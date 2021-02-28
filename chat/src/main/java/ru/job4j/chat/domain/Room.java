package ru.job4j.chat.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

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
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "rooms", fetch = FetchType.LAZY)
    private List<Person> persons;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "room")
    private List<Message> messages;


    public static Room of(String name, String description, List<Person> persons, List<Message> messages) {
        Room room = new Room();
        room.setName(name);
        room.setDescription(description);
        room.setMessages(messages);
        room.setPersons(persons);
        return room;
    }

    public static Room of(int id, String name, String description, List<Person> persons, List<Message> messages) {
        Room room = of(name, description, persons, messages);
        room.setId(id);
        return room;
    }
}
