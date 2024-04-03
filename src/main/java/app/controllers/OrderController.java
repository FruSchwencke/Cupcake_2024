package app.controllers;

import app.entities.Bottom;
import app.entities.Cupcake;
import app.entities.Top;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class OrderController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("basket", ctx -> {

            //initializing a current basket-list
            List<Cupcake> basketList = ctx.sessionAttribute("basketList");

            ctx.sessionAttribute("basketList", basketList);
            ctx.attribute("basketList", basketList);
            ctx.render("basket.html");

        });



    }


}

