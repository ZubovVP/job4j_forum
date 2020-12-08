package ru.job4j.forum.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.forum.model.Post;

import java.util.*;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 05.12.2020.
 */
@Repository
public class PostsStore implements MainActions<Post> {
    private static Random rd = new Random();
    private Map<Integer, Post> posts = new HashMap<>();

    @Override
    public Post add(Post element) {
        while (element.getId() == 0 || this.posts.containsKey(element.getId())) {
            element.setId(rd.nextInt(1000000));
        }
        return this.posts.put(element.getId(), element);
    }

    @Override
    public void update(Post element) {
        this.posts.replace(element.getId(), element);
    }

    @Override
    public void delete(Post element) {
        this.posts.remove(element.getId());
    }

    @Override
    public List<Post> getAll() {
        return new ArrayList<>(this.posts.values());
    }

    @Override
    public Post findById(int id) {
        return this.posts.get(id);
    }
}
