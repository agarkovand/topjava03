package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.LoggerWrapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: Agarkov Andrey
 * Date: 26-Jul-15.
 */
public class MealServlet extends HttpServlet {
    private static final LoggerWrapper LOG = LoggerWrapper.get(UserServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("redirect to mealList");

        response.sendRedirect("mealList.jsp");
    }
}
