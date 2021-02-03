package ru.job4j.auth.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 02.02.2021.
 */
@Data
@EqualsAndHashCode
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;

    public static Person of(int id, String login, String password) {
        Person person = new Person();
        person.id = id;
        person.login = login;
        person.password = password;
        return person;
    }
}
