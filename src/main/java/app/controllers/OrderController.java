package app.controllers;

import app.entities.*;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import io.javalin.Javalin;
import jakarta.servlet.http.HttpSession;

import io.javalin.http.Context;

import java.sql.Connection;
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
        app.get("allOrders", ctx -> getAllOrders(ctx, connectionPool));
        app.get("/adminback", ctx -> ctx.render("admin_page.html"));
        app.post("/deleteOrder", ctx -> deleteOrder(ctx, connectionPool));


    }



    private static void getAllOrders(Context ctx, ConnectionPool connectionPool) {
        try {
            List<Order> allOrdersList = OrderMapper.getAllOrders(connectionPool);
            double totalSum = allOrdersList.stream().mapToDouble(Order::getTotalPrice).sum();

            ctx.attribute("allOrdersList", allOrdersList);
            ctx.attribute("totalSum", totalSum);

            ctx.render("all_orders.html");
        } catch (SQLException e) {
            throw new RuntimeException(e);
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



    public static void deleteOrder(Context ctx, ConnectionPool connectionPool) {
        int orderId = Integer.parseInt(ctx.formParam("orderId"));
        try {
            OrderMapper.deleteOrder(orderId, connectionPool);
            ctx.attribute("messagedelete", "Ordre med ID " + orderId + " er slettet.");
        } catch (DatabaseException | SQLException e) {
            ctx.attribute("messagedelete", e.getMessage());
        }
        ctx.render("admin_page.html");
    }




}

