package ru.job4j.forum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.forum.model.User;
import ru.job4j.forum.repository.MainActions;

import java.util.List;

import static joptsimple.internal.Strings.isNullOrEmpty;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 07.12.2020.
 */
@Service
public class UserService implements MainActions<User> {
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    @Qualifier("usersStore")
    private MainActions<User> us;


    @Override
    public User add(User element) {
        User result = null;
        if (checkNameAndData(element)) {
            element.setPassword(passwordEncoder.encode(element.getPassword()));
            result = this.us.add(element);
        }
        return result;
    }

    @Override
    public void update(User element) {
        if (checkNameAndData(element)) {
            element.setPassword(passwordEncoder.encode(element.getPassword()));
            this.us.add(element);
        }
    }

    @Override
    public void delete(User element) {
        if (element.getId() != 0) {
            this.us.delete(element);
        }
    }

    @Override
    public List<User> getAll() {
        return this.us.getAll();
    }

    @Override
    public User findById(int id) {
        return this.us.findById(id);
    }

    private boolean checkNameAndData(User element) {
        return !isNullOrEmpty(element.getName()) && !isNullOrEmpty(element.getPassword());

    }
}
