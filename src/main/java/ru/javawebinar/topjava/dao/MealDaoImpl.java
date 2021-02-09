package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MealDaoImpl implements MealDao {
    private static final AtomicInteger counter = new AtomicInteger(0);

    private static CopyOnWriteArrayList<Meal> meals = new CopyOnWriteArrayList<>(Arrays.asList(
            new Meal(count(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(count(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(count(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(count(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничном значении", 100),
            new Meal(count(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(count(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(count(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    ));


    private static int count() {
        return counter.incrementAndGet();
    }

    @Override
    public void addMeal(Meal meal) {
        meals.add(new Meal(count(), meal.getDateTime(), meal.getDescription(), meal.getCalories()));
    }

    @Override
    public void deleteMeal(int mealId) {
        //todo: а если нет с таким id?

        meals.stream().filter(m -> m.getId() == mealId).map(m -> meals.remove(m)).count();
    }

    @Override
    public void updateMeal(Meal meal) {
        //todo: а если нет с таким id?
        deleteMeal(meal.getId());
        meals.add(new Meal(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories()));
    }

    @Override
    public List<Meal> getAllMeals() {
        return new ArrayList<>(meals);
    }

    @Override
    public Meal getMealById(int mealId) {
        Meal meal = meals.stream().filter(m -> m.getId() == mealId).collect(Collectors.toList()).get(0);
        return new Meal(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories());
    }
}
