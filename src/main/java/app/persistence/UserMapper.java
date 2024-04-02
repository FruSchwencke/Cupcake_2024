package app.persistence;

import app.entities.User;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static app.Main.connectionPool;

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
                    String userName = rs.getString("user_name");
                    String password = rs.getString("user_password");
                   int role = rs.getInt("role_id");
                    String email =rs.getString("email");
                    String phonenumber =rs.getString("phonenumber");
                    User user = new User(id, userName, password, role, email, phonenumber);
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
    public static User login(String email, String password, ConnectionPool connectionPool) throws DatabaseException
    {
        String sql = "select * from users where email=? and user_password=?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        )
        {
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                int userid = rs.getInt("user_id");
                String username = rs.getString("user_name");
                int role =rs.getInt("role_id");
                String phonenumber = rs.getString("phonenumber");
                return new User(userid, username, password, role, email, phonenumber);
            } else
            {
                throw new DatabaseException("fejl ved login. prøv igen");
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("DB fejl", e.getMessage());
        }


    }
    public static void createuser(String email, String password, String userName, String phonenumber, ConnectionPool connectionPool) throws DatabaseException
    {
        String sql = "insert into users (email, user_password, user_name, phonenumber) values (?,?,?,?)";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        )
        {
            ps.setString(1, email);
            ps.setString(2, password);
            ps.setString(3, userName);
            ps.setString(4, phonenumber);

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

    public void addBalance (int userId, double balance, ConnectionPool connectionPool)
    {
        String sql = "UPDATE users SET balance = balance + ? WHERE user_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        )
        {

            ps.setDouble(1, balance);
            ps.setInt(2, userId);
            int rowsAffected =  ps.executeUpdate();
            if (rowsAffected > 0) {

            } else {

            }




        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
