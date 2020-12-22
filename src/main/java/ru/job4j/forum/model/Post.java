package ru.job4j.forum.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 03.12.2020.
 */
@Data
@EqualsAndHashCode
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String desc;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created")
    private Calendar created;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User owner;
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "post")
    private List<Comment> comments;

    public static Post of(int id, String name, User owner) {
        Post post = new Post();
        post.id = id;
        post.name = name;
        post.owner = owner;
        return post;
    }

    public static Post of(int id, String name, String desc, Calendar created, User owner) {
        Post post = of(id, name, owner);
        post.desc = desc;
        post.created = created;
        return post;
    }

    public static Post of(int id, String name, String desc, Calendar created, User owner, List<Comment> commetns) {
        Post post = of(id, name, desc, created, owner);
        post.comments = commetns;
        return post;
    }

}
