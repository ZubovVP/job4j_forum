package ru.job4j.forum.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Calendar;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 03.12.2020.
 */
@Data
@EqualsAndHashCode
public class Post {
    private int id;
    private String name;
    private String desc;
    private Calendar created;

    public static Post of(int id, String name) {
        Post post = new Post();
        post.id = id;
        post.name = name;
        return post;
    }

    public static Post of(int id, String name, String desc, Calendar created) {
        Post post = of(id, name);
        post.desc = desc;
        post.created = created;
        return post;
    }

}
