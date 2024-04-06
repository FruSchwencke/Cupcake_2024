package app.controllers;

import app.entities.*;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import io.javalin.Javalin;
import jakarta.servlet.http.HttpSession;

import io.javalin.http.Context;

import java.sql.SQLException;
import java.util.List;

public class OrderController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("basket", ctx -> {

            //initializing a current basket-list
            List<Cupcake> basketList = ctx.sessionAttribute("basketList");

            // getting the sum of all orderlines by using totalPrice
            double sum = basketList.stream().mapToDouble(Cupcake::getPrice).sum();


            ctx.sessionAttribute("basketList", basketList);
            ctx.attribute("basketList", basketList);
            ctx.sessionAttribute("sum", sum );
            ctx.attribute("sum", sum );
            ctx.render("basket.html");

        });

        app.get("order_details.html", ctx -> showOrderLines(ctx, connectionPool));
        app.get("/allOrderlines", ctx -> showAllOrderlines(ctx, connectionPool));
        app.get("/admin", ctx -> ctx.render("/admin_page.html"));




    }
    private static List<Order> getAllOrders(ConnectionPool connectionPool)
    {

        try {
            List<Order> allOrdersList = OrderMapper.getAllOrders(connectionPool);
            return allOrdersList;


        } catch (SQLException e) {
            throw new RuntimeException(e); //HUSK!
        }

    }


    public static void showOrderLines(Context ctx, ConnectionPool connectionPool) {

        int orderId = Integer.parseInt(ctx.queryParam("orderId"));

        List<Cupcake> orderLines = OrderMapper.getOrderlinePerOrder(orderId, connectionPool);

        ctx.attribute("orderLines", orderLines);

        ctx.render("order_details.html");
    }

    public static void showAllOrderlines(Context ctx, ConnectionPool connectionPool) {

        List<Cupcake> allOrderlines = OrderMapper.getAllOrderlines(connectionPool);

        ctx.attribute("allOrderlines", allOrderlines);

        ctx.render("all_orderlines.html");
    }
}

