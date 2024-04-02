package app;

import app.config.ThymeleafConfig;
import app.controllers.OrderController;
import app.controllers.UserController;
import app.entities.Bottom;
import app.entities.Top;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.CupcakeMapper;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=public";
    private static final String DB = "cupcake";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);
    public static void main(String[] args)
    {
        // Initializing Javalin and Jetty webserver

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
        }).start(7070);

        // Routing

        app.get("/", ctx -> {
            //get top og bund fra mapper
            List<Top> topList = new ArrayList<>();
            try {
                topList = CupcakeMapper.getAllTops(connectionPool);
            } catch (DatabaseException e) {
                System.out.println("Fejl" + e.getMessage());
            }

            List<Bottom> bottomList = new ArrayList<>();
            try {
                bottomList = CupcakeMapper.getAllBottom(connectionPool);
            } catch (DatabaseException e) {
                System.out.println("Fejl" + e.getMessage());
            }
            ctx.attribute("bottomList", bottomList);
            ctx.attribute("topList", topList);
            ctx.render("index.html");
        });

        UserController.addRoutes(app, connectionPool);
        OrderController.addRoutes(app, connectionPool);

    }
}
