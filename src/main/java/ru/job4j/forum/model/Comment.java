package ru.job4j.forum.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Calendar;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 12.12.2020.
 */
@Data
@EqualsAndHashCode
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "comment")
    private String comment;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created")
    private Calendar created;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_post", nullable = false)
    private Post post;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    public static Comment of(int id, String comment, Calendar created, Post post, User user) {
        Comment com = new Comment();
        com.id = id;
        com.comment = comment;
        com.created = created;
        com.post = post;
        com.user = user;
        return com;
    }

    @Override
    public String toString() {
        return "Comment{" + "id=" + id + ", comment='" + comment + '\'' + ", created=" + created + '}';
    }
}
