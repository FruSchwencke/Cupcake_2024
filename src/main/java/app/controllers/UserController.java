package app.controllers;

import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class UserController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.post("login", ctx -> login(ctx, connectionPool));
        app.get("login", ctx -> ctx.render("login"));


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
            ctx.attribute(e.getMessage());
            ctx.render("index.html");
        }

    }
}
