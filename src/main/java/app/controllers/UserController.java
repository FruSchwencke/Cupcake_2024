package app.controllers;

import app.entities.Order;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import app.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.sql.SQLException;
import java.util.List;

public class UserController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.post("login", ctx -> login(ctx, connectionPool));
        app.get("login", ctx -> ctx.render("login"));
        app.get("createuser", ctx ->ctx.render("createuser"));
        app.post("createuser", ctx -> createUser(ctx, connectionPool));

    }

    private static void createUser(Context ctx, ConnectionPool connectionPool) {
        String email = ctx.formParam("email");
        String password1 = ctx.formParam("password1");
        String password2 = ctx.formParam("password2");
        String userName = ctx.formParam("user_name");
        String phoneNumber = ctx.formParam("phonenumber");

        if (!EmailValidate(email)){
            ctx.attribute("message", "Email address must contain '@' symbol");
            ctx.render("login.html");
            return;
        }

        if(password1.equals(password2)){
            try {
                UserMapper.createuser(email, password1, userName, phoneNumber, connectionPool);
                ctx.attribute("message", "du er hermed oprettet med " + email + ". Nu skal du logge på.");
                ctx.render("login.html");

            } catch (DatabaseException e) {
                ctx.attribute("message", "dit brugernavn findes allerede, prøv igen, eller log ind");
                ctx.render("createuser.html");
            }


        }else{
            ctx.attribute("message", "dine to passwords matcher ikke! prøv igen");
            ctx.render("createuser.html");
        }

    }

    private static boolean EmailValidate(String email) {
        return email.contains("@");
    }

    private static void login(Context ctx, ConnectionPool connectionPool) {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");


        try {
            User user = UserMapper.login(email, password, connectionPool );
            if (user.getRole() ==1){
                ctx.sessionAttribute("currentUser", user);
                ctx.render("admin_page.html");
            }else {
                ctx.sessionAttribute("currentUser", user);
                ctx.render("customer_page.html");
            }
        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("login.html");
        }

    }

    private static void getAllOrders(Context ctx, ConnectionPool connectionPool)
    {
        try {
            List<Order> allOrdersList = OrderMapper.getAllOrders(connectionPool);
            ctx.attribute("allOrdersList",allOrdersList);
            ctx.render("admin_page.html");

        } catch (SQLException e) {
            throw new RuntimeException(e); //HUSK!
        }

    }



    private static void logout(Context ctx)
    {
        ctx.req().getSession().invalidate();
        ctx.redirect("/");
    }


}
