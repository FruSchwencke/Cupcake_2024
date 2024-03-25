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
    }

    private static void login(Context ctx, ConnectionPool connectionPool) {
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");


        try {
            User user = UserMapper.login(username, password, connectionPool );
            ctx.sessionAttribute("currentUser", user);
            ctx.render("login.html");
        } catch (DatabaseException e) {
            ctx.attribute(e.getMessage());
            ctx.render("index.html");
        }

    }
}
