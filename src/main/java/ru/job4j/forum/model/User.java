package ru.job4j.forum.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 03.12.2020.
 */
@Data
@EqualsAndHashCode
public class User {
    private int id;
    private String name;
    private String password;
    private Set<Post> posts;

    public static User of(int id, String name, String password, Set<Post> posts) {
        User user = of(name, password, posts);
        user.id = id;
        return user;
    }

    public static User of(String name, String password, Set<Post> posts) {
        User user = new User();
        user.name = name;
        user.password = password;
        user.posts = posts;
        return user;
    }
}
