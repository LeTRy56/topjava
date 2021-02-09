package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MealServlet extends HttpServlet {
    private static final String INSERT_OR_EDIT = "/editMeal.jsp";
    private static final String MEALS_LIST = "/meals.jsp";
    private MealDao mealDao;

    public MealServlet() {
        this.mealDao = new MealDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward = "";
        String action = req.getParameter("action");

        if (action == null || action.equalsIgnoreCase("mealsList")) {
            forward = MEALS_LIST;
            req.setAttribute("mealsDTOList", MealsUtil.filteredByStreams(
                    mealDao.getAllMeals(),
                    LocalTime.MIN,
                    LocalTime.MAX,
                    MealsUtil.getCaloriesPerDay()
            ));
        }
        else if (action.equalsIgnoreCase("delete")) {
            Integer mealId = Integer.parseInt(req.getParameter("mealId"));
            mealDao.deleteMeal(mealId);
            forward = MEALS_LIST;
            req.setAttribute("mealsDTOList", MealsUtil.filteredByStreams(
                    mealDao.getAllMeals(),
                    LocalTime.MIN,
                    LocalTime.MAX,
                    MealsUtil.getCaloriesPerDay()
            ));
        } else if (action.equalsIgnoreCase("edit")) {
            forward = INSERT_OR_EDIT;
            Integer mealId = Integer.parseInt(req.getParameter("mealId"));
            req.setAttribute("meal", mealDao.getMealById(mealId));
        } else if (action.equalsIgnoreCase("add")) {
            forward = INSERT_OR_EDIT;
        } else forward = MEALS_LIST;

        req.getRequestDispatcher(forward).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String mealId = req.getParameter("mealId");
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"), TimeUtil.ISO_LOCAL_DATE_TIME_FORMATTER);
        String description = req.getParameter("description");
        Integer calories = Integer.parseInt(req.getParameter("calories"));

        if (mealId == null || mealId.isEmpty())
            mealDao.addMeal(new Meal(0, dateTime, description, calories));
        else {
            mealDao.updateMeal(new Meal(Integer.parseInt(mealId), dateTime, description, calories));
        }

        req.setAttribute("mealsDTOList", MealsUtil.filteredByStreams(
                mealDao.getAllMeals(),
                LocalTime.MIN,
                LocalTime.MAX,
                MealsUtil.getCaloriesPerDay()
        ));
        req.getRequestDispatcher(MEALS_LIST).forward(req, resp);
    }
}
