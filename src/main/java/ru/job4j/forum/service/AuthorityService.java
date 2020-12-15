package ru.job4j.forum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.forum.model.Authority;
import ru.job4j.forum.repository.AutohorityRepository;

import java.util.List;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 15.12.2020.
 */
@Service
public class AuthorityService {
    @Autowired
    private AutohorityRepository ar;

    public Authority save(Authority authority) {
        if (!authority.getAuthority().equals("")) {
            return this.ar.save(authority);
        }
        throw new NullPointerException(authority.toString() + "authority is null");
    }

    public Authority findByName(String name) {
        if (!name.equals("")) {
            return this.ar.findByName(name);
        }
        throw new NullPointerException(name + "authority is null");
    }

    public void delete(int id) {
        if (id >= 0) {
            this.ar.delete(id);
        } else {
            throw new NullPointerException("Wrong id");
        }
    }

    private List<Authority> findAll() {
        return this.ar.findAll();
    }
}
