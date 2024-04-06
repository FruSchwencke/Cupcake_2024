package app.persistence;


import app.entities.Order;
import app.exceptions.DatabaseException;
import app.entities.Top;
import app.entities.Bottom;
import app.entities.Cupcake;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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



