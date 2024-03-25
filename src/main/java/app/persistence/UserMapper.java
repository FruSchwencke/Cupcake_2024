package app.persistence;

import app.entities.User;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static List<User> getAllUsers(ConnectionPool connectionPool) throws DatabaseException
    {
        List<User> userList = new ArrayList<>();
        String sql = "select * from users";
        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ResultSet rs = ps.executeQuery();

                while (rs.next())
                {
                    int id = rs.getInt("user_id");
                    String userName = rs.getString("username");
                    String password = rs.getString("password");
                    String role = rs.getString("role");
                    User user = new User(id, userName, password, role);
                    userList.add(user);
                }
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage() + "Could not get users from database");
        }
        return userList;
    }


}
