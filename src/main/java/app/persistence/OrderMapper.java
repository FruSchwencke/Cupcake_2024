package app.persistence;


import app.entities.*;
import app.exceptions.DatabaseException;


import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static app.Main.connectionPool;
import static java.time.ZoneOffset.UTC;

public class OrderMapper {

    public static List<Order> getAllOrders(ConnectionPool connectionPool) throws SQLException {
        List<Order> allOrdersList = new ArrayList<>();
        String sql = "SELECT order_id, order_date, price_total FROM orders";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                LocalDate pickuptime = rs.getDate("order_date").toLocalDate();
                double totalPrice = rs.getDouble("price_total");

                Order order = new Order(orderId, pickuptime, totalPrice);
                allOrdersList.add(order);

            }
            return allOrdersList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //IKKE FÆRDIG:
    public List<Cupcake> getOrderlineList(int order_id, ConnectionPool connectionpool) {

        List<Cupcake> orderlineList = new ArrayList<>();
        String sql = "SELECT bottom_id, top_id, quantity FROM orderline WHERE order_id=?";
        try (
                Connection connection = connectionpool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ResultSet rs = ps.executeQuery();
            ps.setInt(1, order_id);

            while (rs.next()) {
                Top top = new Top(rs.getInt("topping_id"), rs.getString("topping.flavour"), rs.getDouble("topping.price"));
                Bottom bottom = new Bottom(rs.getInt("bottom_id"), rs.getString("bottom.flavour"), rs.getDouble("bottom.price"));
                int quantity = rs.getInt("quantity");

                Cupcake cupcake = new Cupcake(bottom, top, quantity);

                orderlineList.add(cupcake);

            }

            return orderlineList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }









//        public List<Order> getOrderByDate (LocalDate pickuptime, ConnectionPool connectionPool) throws SQLException {
//
//        List<Order> ordersDate = new ArrayList<>();
//        String sql = "SELECT orderId, userID, totalPrice FROM orders WHERE pickuptime = ?";
//        try (
//                Connection connection = connectionPool.getConnection();
//                PreparedStatement ps = connection.prepareStatement(sql)
//        ) {
//            ps.setDate(1, java.sql.Date.valueOf(pickuptime));
//            ResultSet rs = ps.executeQuery();
//            {
//                while (rs.next()) {
//                    int orderId = rs.getInt("orderId");
//                    int userID = rs.getInt("userID");
//                    double totalPrice = rs.getDouble("totalPrice");
//
//                    List<Cupcake> orderListDate =
//
//
//                }
//            }
//        }
//    }
//
//        return orderListDate;
//    }
//
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
/////MANGLER!!
//
//    }
//} catch(SQLException e){
//        throw new RuntimeException(e);
//        }
//        return orderList;
//        }
//        }
//



    public static void createOrder(User user, List<Cupcake> cupcakes, LocalDate pickUpTime, double totalPrice, ConnectionPool connectionPool) throws DatabaseException
    {
        String sql = "insert into orders (user_id, price_total, pickup_time) values (?,?,?)";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        )
        {
            ps.setInt(1, user.getUserId());
            ps.setDouble(2, totalPrice);
            //TODO: fix timestamp
            ps.setTimestamp(3, new Timestamp(new Date().getTime()));


            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new DatabaseException("Fejl ved oprettelse af ny bruger");
            }
        }
        catch (SQLException e)
        {
            String msg = "Der er sket en fejl. Prøv igen";
            if (e.getMessage().startsWith("ERROR: duplicate key value "))
            {
                msg = "Brugernavnet findes allerede. Vælg et andet";
            }
            throw new DatabaseException(msg, e.getMessage());
        }
    }
}
