package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class InMemoryMealRepository implements MealRepository {
    private static final Logger LOG = getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
//        MealsUtil.meals.forEach(this::save);
        MealsUtil.meals.forEach(m -> save(m, SecurityUtil.authUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        LOG.info("save {}", meal);
        Map<Integer, Meal> mealRepository = repository.get(userId);
        if (mealRepository == null) {
            mealRepository = new ConcurrentHashMap<>();
            repository.put(userId, mealRepository);
        }

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            mealRepository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return mealRepository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        LOG.info("delete {}", id);
        return repository.get(userId).remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        LOG.info("get {}", id);
        return repository.get(userId).get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        LOG.info("getAll");

        //todo sort datetime 3.3: список еды возвращать отсортированный в обратном порядке по датам
        return repository.get(userId).values();
    }
}

