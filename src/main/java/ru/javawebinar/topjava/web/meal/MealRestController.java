package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

@Controller
public class MealRestController {
    private static final Logger LOG = getLogger(MealRestController.class);

    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Collection<Meal> getAll() {
        LOG.info("getAll");
        return service.getAll(SecurityUtil.authUserId());
    }

    public Meal get(int id) {
        LOG.info("get {}", id);
        //todo convert to DTO:
        return service.get(id, SecurityUtil.authUserId());
    }

    public Meal create(Meal meal) {
        LOG.info("create {}", meal);
        return service.create(meal, SecurityUtil.authUserId());
    }

    public void delete(int id) {
        LOG.info("delete {}", id);
        service.delete(id, SecurityUtil.authUserId());
    }

    public void update(Meal meal, int id) {
        LOG.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal, id);
    }
}