package ru.job4j.forum.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 14.12.2020.
 */
@Data
@EqualsAndHashCode
@Entity
@Table(name = "authorities")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "authority")
    private String authority;

    public static Authority of(int id, String authority) {
        Authority author = new Authority();
        author.id = id;
        author.authority = authority;
        return author;
    }

}
