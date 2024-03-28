package app.controllers;

import app.persistence.ConnectionPool;
import io.javalin.Javalin;

public class OrderController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("basket", ctx -> ctx.render("basket"));
    }
}

