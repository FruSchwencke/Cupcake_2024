package app.persistence;

import app.entities.Bottom;
import app.entities.Top;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CupcakeMapper {

    public static List<Top> getAllTops(ConnectionPool connectionPool) throws DatabaseException {
        List<Top> tops = new ArrayList<>();
        String sql = "select * from topping";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int topId = rs.getInt("topping_id");
                    String flavour = rs.getString("flavour");
                    int price = rs.getInt("price");
                    tops.add(new Top(topId, flavour, price));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage() + "could not get toppings from DB");
        }
        return tops;

    }

    public static List<Bottom>getAllBottom(ConnectionPool connectionPool) throws DatabaseException {
        List<Bottom>bottoms=new ArrayList<>();
        String sql = "select * from bottom";

        try (Connection connection = connectionPool.getConnection()){
            try (PreparedStatement ps = connection.prepareStatement(sql)){
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    int bottomId = rs.getInt("bottom_id");
                    String flavour = rs.getString("flavour");
                    int price = rs.getInt("price");
                    bottoms.add(new Bottom(bottomId, flavour, price));
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage() + "could not get bottom from DB");
        }
        return bottoms;
    }

}
