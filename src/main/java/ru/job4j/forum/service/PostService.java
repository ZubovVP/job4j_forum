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
        Post result = null;
        if (element.getCreated() == null) {
            element.setCreated(Calendar.getInstance());
        }
        if (!isNullOrEmpty(element.getName()) && !isNullOrEmpty(element.getDesc())) {
            result = this.ps.add(element);
        }
        return result;

    }


    @Override
    public void update(Post element) {
        Post post = this.ps.findById(element.getId());
        post.setDesc(element.getDesc());
        post.setName(element.getName());
        this.ps.update(post);
    }

    @Override
    public void delete(Post element) {
        this.ps.delete(element);
    }

    public List<Post> getAll() {
        return this.ps.getAll();
    }

    @Override
    public Post findById(int id) {
        return this.ps.findById(id);
    }
}
