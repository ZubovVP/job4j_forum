package ru.job4j.forum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import ru.job4j.forum.model.User;
import ru.job4j.forum.repository.AuthorityRepository;

import java.util.Optional;

import static joptsimple.internal.Strings.isNullOrEmpty;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 12.12.2020.
 */
@Service
public class UserServiceForRepository implements CrudRepository<User, Integer> {
    @Autowired
    @Qualifier("userRepository")
    private CrudRepository<User, Integer> ur;
    @Autowired
    private AuthorityRepository ar;


    @Override
    public <S extends User> S save(S s) {
        if (checkNameAndPassword(s)) {
            if (s.getAuthority().getId() == 0) {
                s.setAuthority(this.ar.findByName(s.getAuthority().getAuthority()));
            }
        }
        return this.ur.save(s);
    }

    @Override
    public <S extends User> Iterable<S> saveAll(Iterable<S> iterable) {
        for (User user : iterable) {
            checkNameAndPassword(user);
            if (user.getAuthority().getId() == 0) {
                user.setAuthority(this.ar.findByName(user.getAuthority().getAuthority()));
            }
        }
        return this.ur.saveAll(iterable);
    }

    @Override
    public Optional<User> findById(Integer integer) {
        checkId(integer);
        return this.ur.findById(integer);
    }

    @Override
    public boolean existsById(Integer integer) {
        checkId(integer);
        return this.ur.existsById(integer);
    }

    @Override
    public Iterable<User> findAll() {
        return this.ur.findAll();
    }

    @Override
    public Iterable<User> findAllById(Iterable<Integer> iterable) {
        for (Integer id : iterable) {
            checkId(id);
        }
        return this.ur.findAllById(iterable);
    }

    @Override
    public long count() {
        return this.ur.count();
    }

    @Override
    public void deleteById(Integer integer) {
        checkId(integer);
        this.ur.deleteById(integer);
    }

    @Override
    public void delete(User user) {
        checkId(user.getId());
        this.ur.delete(user);
    }

    @Override
    public void deleteAll(Iterable<? extends User> iterable) {
        for (User user : iterable) {
            checkId(user.getId());
        }
        this.deleteAll(iterable);
    }

    @Override
    public void deleteAll() {
        this.deleteAll();
    }

    private boolean checkNameAndPassword(User element) {
        if (isNullOrEmpty(element.getName()) || isNullOrEmpty(element.getPassword())) {
            throw new NullPointerException(element.toString() + " Name or password is null");
        }
        return true;
    }

    private void checkId(Integer id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Wring id");
        }
    }
}
