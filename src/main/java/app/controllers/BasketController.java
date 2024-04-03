package app.controllers;

import app.entities.Bottom;
import app.entities.Cupcake;
import app.entities.Top;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;

import java.util.ArrayList;
import java.util.List;

public class BasketController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("addToBasket", ctx -> {

            // these parameters represents the user's choices for bottom, top and quantity.
            String bottomParam = ctx.queryParam("bottom");
            String topParam = ctx.queryParam("top");
            String quantityParam = ctx.queryParam("quantity");

            // this parses the choosen quantity into a integer
            int quantity = Integer.parseInt(quantityParam);

            //Retrieval of the bottom-list and the top-list provided by DB while rendering the site.
           List<Bottom> bottomList = ctx.sessionAttribute("bottomList");
           List<Top> topList = ctx.sessionAttribute("topList");

           //initializing a current basket-list
           List<Cupcake> basketList = ctx.sessionAttribute("basketList");

            if (basketList == null) {
                basketList = new ArrayList<>();
            }

            // filters bottomList to find the bottom whose flavour are equal to bottomParam.
            Bottom selectedBottom = bottomList.stream()
                    .filter(b -> b.getBottomIdAsString().equals(bottomParam)).findFirst().orElse(null);

            Top selectedTop = topList.stream()
                    .filter(b -> b.getTopIdAsString().equals(topParam)).findFirst().orElse(null);;

            // catches user-error when choosing
            if (selectedBottom == null || selectedTop == null) {
                ctx.status(400).result("Invalid bottom or top selection");
                return;
            }

            // calculating the total price, for the customized cupcake and quantity
            double totalPrice = (selectedBottom.getPrice() + selectedTop.getPrice()) * quantity;

            // creating a cupcake based on user-choices
            Cupcake newCupcake = new Cupcake(selectedBottom, selectedTop, quantity, totalPrice);
            basketList.add(newCupcake);

            // til at se i terminalen at der sker noget...
            basketList.forEach(cupcake -> {
                System.out.println(cupcake.getPrice());
            });

            ctx.sessionAttribute("basketList", basketList);
            ctx.attribute("basketList", basketList);
            ctx.attribute("topList", topList);
            ctx.attribute("bottomList", bottomList);
            ctx.render("index.html");
        });
    }

}
