package app.persistence;


import app.entities.Order;
import app.exceptions.DatabaseException;
import app.entities.Top;
import app.entities.Bottom;
import app.entities.Cupcake;


import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

