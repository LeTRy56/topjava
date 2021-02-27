package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.web.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(USER_MEAL_ID_1, UserTestData.USER_ID);
        assertMatch(meal, USER_MEAL_1);
    }

    @Test
    public void delete() {
        service.delete(USER_MEAL_ID_1, UserTestData.USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_MEAL_ID_1, UserTestData.USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, UserTestData.USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        //todo
    }

    @Test
    public void duplicateDateTimeCreate() {
        Meal duplicated = new Meal(USER_MEAL_1);
        duplicated.setId(null);
        assertThrows(DataAccessException.class, () ->
                service.create(duplicated, UserTestData.USER_ID));
    }

    @Test
    public void getAll() {
        List<Meal> userMeals = service.getAll(UserTestData.USER_ID);
        List<Meal> adminMeals = service.getAll(UserTestData.ADMIN_ID);
        assertMatch(userMeals, USER_MEAL_2, USER_MEAL_1);
        assertMatch(adminMeals, ADMIN_MEAL_2, ADMIN_MEAL_1);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, UserTestData.USER_ID);
        assertMatch(service.get(USER_MEAL_ID_1, UserTestData.USER_ID), getUpdated());
    }

    @Test
    public void create() {
        Meal created = service.create(getNew(), UserTestData.USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, UserTestData.USER_ID), newMeal);
    }

    @Test
    public void deleteStranger() {
        assertThrows(NotFoundException.class, () -> service.delete(USER_MEAL_ID_1, UserTestData.ADMIN_ID));
    }

    @Test
    public void getStranger() {
        assertThrows(NotFoundException.class, () -> service.get(ADMIN_MEAL_ID_1, UserTestData.USER_ID));
    }

    @Test
    public void updateStranger() {
        //todo
//        assertThrows(NotFoundException.class, () -> service.update(ADMIN_MEAL_1, UserTestData.USER_ID));
    }

}