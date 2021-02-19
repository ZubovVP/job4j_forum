package ru.job4j.chat.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 14.02.2021.
 */
@Data
@EqualsAndHashCode
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;

    public static Role of(int id, String name) {
        Role role = Role.of(name);
        role.setId(id);
        return role;
    }

    public static Role of(String name) {
        Role role = new Role();
        role.setName(name);
        return role;
    }

}
