package app.persistence;


import app.entities.*;
import app.exceptions.DatabaseException;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static app.Main.connectionPool;
import static java.time.ZoneOffset.UTC;

public class OrderMapper {

    public static List<Order> getAllOrders(ConnectionPool connectionPool) throws SQLException {
        List<Order> allOrdersList = new ArrayList<>();
        String sql = "SELECT order_id, price_total FROM orders";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                double totalPrice = rs.getDouble("price_total");

                Order order = new Order(orderId, totalPrice);
                allOrdersList.add(order);

            }
            return allOrdersList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static List<Cupcake> getOrderlinePerOrder(int orderId, ConnectionPool connectionPool) {

        List<Cupcake> orderlineList = new ArrayList<>();
        String sql = "SELECT ol.topping_id, ol.bottom_id, ol.quantity, t.flavour AS top_flavour, b.flavour AS bottom_flavour " +
                "FROM orderline AS ol " +
                "JOIN topping AS t ON ol.topping_id = t.topping_id " +
                "JOIN bottom AS b ON ol.bottom_id = b.bottom_id " +
                "WHERE ol.order_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Top top = new Top(rs.getInt("topping_id"), rs.getString("top_flavour"));
                Bottom bottom = new Bottom(rs.getInt("bottom_id"), rs.getString("bottom_flavour"));
                int quantity = rs.getInt("quantity");

                Cupcake cupcake = new Cupcake(bottom, top, quantity);
                orderlineList.add(cupcake);
            }

            return orderlineList;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }


    }


    public static List<Cupcake> getAllOrderlines(ConnectionPool connectionPool) {

        List<Cupcake> orderlineListAll = new ArrayList<>();
        String sql = "SELECT ol.topping_id, ol.bottom_id, ol.quantity, t.flavour AS top_flavour, b.flavour AS bottom_flavour " +
                "FROM orderline AS ol " +
                "JOIN topping AS t ON ol.topping_id = t.topping_id " +
                "JOIN bottom AS b ON ol.bottom_id = b.bottom_id";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Top top = new Top(rs.getInt("topping_id"), rs.getString("top_flavour"));
                Bottom bottom = new Bottom(rs.getInt("bottom_id"), rs.getString("bottom_flavour"));
                int quantity = rs.getInt("quantity");

                Cupcake cupcake = new Cupcake(bottom, top, quantity);
                orderlineListAll.add(cupcake);
            }

            return orderlineListAll;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }



//
//    public static List<Order> getOrderByUserID(int user_id, ConnectionPool connectionPool) throws DatabaseException {
//        String sql = "SELECT * from users WHERE users_id=?";
//        List<Order> orderList = new ArrayList<>();
//        try (
//                Connection connection = connectionPool.getConnection();
//                PreparedStatement ps = connection.prepareStatement(sql)
//
//        ) {
//            ps.setInt(1, user_id);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//
//                int orderId = rs.getInt("orderId");
//                LocalDate pickuptime = rs.getDate("pickuptime").toLocalDate();
//                double totalPrice = rs.getDouble("totalPrice");
//
//
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//
//        return orderList;
//        }




    public static void createOrder(User user, List<Cupcake> cupcakes, double totalPrice, ConnectionPool connectionPool) throws DatabaseException {

        String sql = "insert into orders (user_id, price_total, pickup_time) values (?,?,?)";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, user.getUserId());
            ps.setDouble(2, totalPrice);
            //TODO: fix timestamp
            ps.setTimestamp(3, new Timestamp(new Date().getTime()));


            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 1)
            {
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                int orderId = rs.getInt("order_id");

                //For each customized cupcake and wanted quantity, the orderLines are created and connected to an order on a user_id in DB
                cupcakes.forEach(cupcake -> {
                    try {
                        createOrderLine(cupcake.getBottom().getBottomId(), cupcake.getTop().getTopId(), cupcake.getQuantity(), orderId);
                    } catch (DatabaseException e) {
                        throw new RuntimeException(e.getMessage());
                    }
                });


            } else {
                throw new DatabaseException("Fejl. Prøv igen");
            }

        } catch (SQLException e) {
            String msg = "Der er sket en fejl. Prøv igen";
            if (e.getMessage().startsWith("ERROR: duplicate key value ")) {
                msg = "Brugernavnet findes allerede. Vælg et andet";
            }
            throw new DatabaseException(msg, e.getMessage());
        }


    }

    public static void createOrderLine(int bottomId, int toppingId, int quantity, int orderId) throws DatabaseException {

        //OrderLines are created in DB by this method, which gets called in createOrder
        String sql = "insert into orderline (order_id, bottom_id, topping_id, quantity) values (?,?,?,?)";


        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)


        ) {

            ps.setInt(1, orderId);
            ps.setInt(2, bottomId);
            ps.setInt(3, toppingId);
            ps.setInt(4, quantity);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new DatabaseException("Fejl ved oprettelse af orderlinje");
            }

        } catch (SQLException e) {
            String msg = "Der er sket en fejl ved bestilling. Prøv igen";

            throw new DatabaseException(msg , e.getMessage());
        }

    }

    public static void deleteOrder(int orderId, ConnectionPool connectionPool) throws DatabaseException, SQLException {
        String orderlineSql = "DELETE FROM orderline WHERE order_id = ?";
        String orderSql = "DELETE FROM orders WHERE order_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement psOrderline = connection.prepareStatement(orderlineSql);
        ) {
            psOrderline.setInt(1, orderId);
            int orderlineRowsAffected = psOrderline.executeUpdate();
            if (orderlineRowsAffected == 0) {
                throw new DatabaseException("Ordre ID findes ikke");
            }

            try (
                    PreparedStatement psOrder = connection.prepareStatement(orderSql);
            ) {
                psOrder.setInt(1, orderId);
                int orderRowsAffected = psOrder.executeUpdate();
                if (orderRowsAffected == 0) {
                    throw new DatabaseException("Ordre ID findes ikke");
                }
            }
        } catch (SQLException e) {
            String msg = "Der er sket en fejl ved sletning af ordre, prøv igen";
            throw new DatabaseException(msg, e.getMessage());
        }
    }

}
