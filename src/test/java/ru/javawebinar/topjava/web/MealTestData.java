package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.*;

public class MealTestData {
    public static final int USER_MEAL_ID_1 = START_SEQ + 2;
    public static final int USER_MEAL_ID_2 = START_SEQ + 3;
    public static final int ADMIN_MEAL_ID_1 = START_SEQ + 4;
    public static final int ADMIN_MEAL_ID_2 = START_SEQ + 5;
    public static final int NOT_FOUND = 20;

    public static final Meal USER_MEAL_1 = new Meal(USER_MEAL_ID_1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Юзер завтрак", 100);
    public static final Meal USER_MEAL_2 = new Meal(USER_MEAL_ID_2, LocalDateTime.of(2020, Month.JANUARY, 30, 18, 0), "Юзер ужин", 200);
    public static final Meal ADMIN_MEAL_1 = new Meal(ADMIN_MEAL_ID_1, LocalDateTime.of(2021, Month.JANUARY, 31, 14, 0), "Админ обед", 300);
    public static final Meal ADMIN_MEAL_2 = new Meal(ADMIN_MEAL_ID_2, LocalDateTime.of(2021, Month.JANUARY, 31, 21, 0), "Админ ужин", 400);


    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2000, Month.APRIL, 10, 22, 0), "New meal", 123);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(USER_MEAL_1);
        updated.setDateTime(LocalDateTime.of(1900, Month.DECEMBER, 10, 5, 0));
        updated.setDescription("UpdatedDescription");
        updated.setCalories(999);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
                //uses object.equals, not works correct!!!:
//        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
