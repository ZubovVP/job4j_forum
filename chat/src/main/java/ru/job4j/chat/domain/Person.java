package ru.job4j.chat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 14.02.2021.
 */
@Data
@Entity
@Table(name = "persons")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @CreatedDate
    @Column(name = "created")
    private Long created;

    @Column(name = "enabled")
    private boolean enabled;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "person_room",
            joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"))
    private Set<Room> rooms;
    @JsonIgnore
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


    // CHECKSTYLE:ON


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        return id == person.id && Objects.equals(login, person.login) && Objects.equals(name, person.name) && Objects.equals(surname, person.surname) && Objects.equals(password, person.password) && Objects.equals(created, person.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, name, surname, password, created);
    }
}
