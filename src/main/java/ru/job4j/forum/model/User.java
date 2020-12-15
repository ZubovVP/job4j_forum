package ru.job4j.forum.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "username")
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "enabled")
    private boolean enabled;
    @ManyToOne
    @JoinColumn(name = "authority_id")
    private Authority authority;


    public static User of(int id, String name, String password, Authority authority) {
        User user = of(name, password, authority);
        user.id = id;
        return user;
    }

    public static User of(String name, String password, Authority authority) {
        User user = new User();
        user.name = name;
        user.password = password;
        user.authority = authority;
        return user;
    }
}
