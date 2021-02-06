package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class MealServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<MealTo> mealTos = MealsUtil.filteredByStreams(
                MealsUtil.getMeals(),
                LocalTime.MIN,
                LocalTime.MAX,
                MealsUtil.getCaloriesPerDay()
        );

        req.setAttribute("meals", mealTos);

        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
//        resp.sendRedirect("meals.jsp");
    }
}
