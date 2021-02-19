package ru.job4j.chat.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Set;

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
@Table(name = "persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "login")
    private String login;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "password")
    private String password;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "created")
    private Long created;
    @Column(name = "enabled")
    private boolean enabled;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "person_room",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id"))
    private Set<Room> rooms;
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "person")
    private Set<Message> messages;

    // CHECKSTYLE:OFF
    public static Person of(int id, String login, String name, String surname, String password, boolean enabled, Role role, Set<Message> messages) {
        Person person = of(login, name, surname, password, enabled, role, messages);
        person.setId(id);
        return person;
    }

    public static Person of(String login, String name, String surname, String password, boolean enabled, Role role, Set<Message> messages) {
        Person person = new Person();
        person.setLogin(login);
        person.setName(name);
        person.setSurname(surname);
        person.setPassword(password);
        person.setCreated(System.currentTimeMillis());
        person.setEnabled(enabled);
        person.setRole(role);
        person.setMessages(messages);
        return person;
    }


    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", password='" + password + '\'' +
                ", created=" + created +
                ", enabled=" + enabled +
                ", role=" + role +
                ", messages=" + messages +
                '}';
    }
    // CHECKSTYLE:ON

}
