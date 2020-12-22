package ru.job4j.forum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.repository.MainActions;

import java.util.*;

import static joptsimple.internal.Strings.isNullOrEmpty;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 03.12.2020.
 */
@Service
public class PostService implements MainActions<Post> {
    @Autowired
    @Qualifier("postsStore")
    private MainActions<Post> ps;


    @Override
    public Post add(Post element) {
        if (element.getCreated() == null) {
            element.setCreated(Calendar.getInstance());
        }
        if (isNullOrEmpty(element.getName()) || isNullOrEmpty(element.getDesc()) || element.getOwner() == null) {
            throw new NullPointerException(element.toString() + " Name or description or owner is null");
        }
        return this.ps.add(element);
    }


    @Override
    public void update(Post element) {
        if (isNullOrEmpty(element.getName()) || isNullOrEmpty(element.getDesc()) || element.getId() <= 0 || element.getOwner() == null) {
            throw new NullPointerException(element.toString() + " Name or description or owner is null or id wrong");
        }
        this.ps.update(element);
    }

    @Override
    public void delete(Post element) {
        if (element.getId() <= 0) {
            throw new NullPointerException(element.toString() + " - Wrong id");
        }
        this.ps.delete(element);
    }

    public List<Post> getAll() {
        return this.ps.getAll();
    }

    @Override
    public Post findById(int id) {
        if (id <= 0) {
            throw new NullPointerException(id + " - Wrong id");
        }
        return this.ps.findById(id);
    }
}
