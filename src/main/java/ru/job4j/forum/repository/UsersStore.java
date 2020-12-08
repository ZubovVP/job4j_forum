package ru.job4j.forum.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.forum.model.User;

import java.util.*;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 05.12.2020.
 */
@Repository
public class UsersStore implements MainActions<User> {
    private static Random rd = new Random();
    private Map<Integer, User> users = new HashMap<>();

    @Override
    public User add(User element) {
        while (element.getId() == 0 || this.users.containsKey(element.getId())) {
            element.setId(rd.nextInt(1000000));
        }
        return this.users.put(element.getId(), element);
    }

    @Override
    public void update(User element) {
        this.users.replace(element.getId(), element);
    }

    @Override
    public void delete(User element) {
        this.users.remove(element.getId());
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(this.users.values());
    }

    @Override
    public User findById(int id) {
        return this.users.get(id);
    }
}
