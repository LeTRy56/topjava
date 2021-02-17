package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private final Map<Integer, User> repository = new ConcurrentHashMap<>();
//    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(SecurityUtil.authUserId());
            repository.put(user.getId(), user);
            return user;
        }
        user.setId(SecurityUtil.authUserId());
        return repository.computeIfPresent(SecurityUtil.authUserId(), (id, oldUser) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        List<User> list = new ArrayList<>(repository.values());
        list.sort((u1, u2) ->
                u1.getName().compareTo(u2.getName()) == 0 ?
                        u1.getEmail().compareTo(u2.getEmail()) :
                        u1.getName().compareTo(u2.getName()));
//        list.sort((u1, u2) -> u1.getName().compareTo(u2.getName()) + u1.getEmail().compareTo(u2.getEmail()));
        return list;
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return getAll().stream().filter(u -> u.getEmail().equals(email)).collect(Collectors.toList()).get(0);
    }
}
