package app.persistence;

import app.entities.Order;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper {



    public static List<Order> getOrderByUserID(int user_id, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT * from users WHERE users_id=?";
        List<Order> orderList = new ArrayList<>();
        try (
                Connection connection = connectionPool.getConnection();
                 PreparedStatement ps = connection.prepareStatement(sql)

        ) {
            ps.setInt(1, user_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                int order_id = rs.getInt("order_id");
///MANGLER!!

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderList;
    }



}
