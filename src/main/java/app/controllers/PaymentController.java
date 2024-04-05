package app.controllers;

import app.entities.Cupcake;
import app.entities.User;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;

import java.util.List;

public class PaymentController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("pay", ctx -> {

            //initializing a current basket-list
            List<Cupcake> basketList = ctx.sessionAttribute("basketList");

            // getting the sum of all orderlines by using totalPrice
            double sum = basketList.stream().mapToDouble(Cupcake::getPrice).sum();

            ctx.sessionAttribute("basketList", basketList);
            ctx.attribute("basketList", basketList);
            ctx.sessionAttribute("sum", sum );
            ctx.attribute("sum", sum );


            User user = ctx.sessionAttribute("currentUser");
            System.out.println(user);
            //if the user is logged in, the order is succesfull, if the user is not logged in, the user is redirected to login.html
            if(user != null){

                ctx.render("order_processed.html");
            }else ctx.render("login.html");


        });
    }
}
