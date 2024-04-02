package app.controllers;

import app.entities.Bottom;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;

import java.util.List;

public class BasketController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("addToBasket", ctx -> {

            ctx.queryParam("bottom");
//            ctx.formParam("top");
//            ctx.formParam("quantity");

           List<Bottom> bottomList = ctx.sessionAttribute("bottomList");
           List<Top> topList = ctx.sessionAttribute("topList");


            ctx.attribute("bottomList",bottomList);
            ctx.attribute("topList",topList);
            ctx.render("index.html");
        });
    }

}
