package ru.job4j.chat.domain;

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
 * Date: 14.02.2021.
 */
@Data
@EqualsAndHashCode
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "text")
    private String text;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created")
    private Calendar created;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_person")
    private Person person;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    public static Message of(String text, Calendar created, Person person, Room room) {
        Message message = new Message();
        message.setText(text);
        message.setCreated(created);
        message.setPerson(person);
        message.setRoom(room);
        return message;
    }

    public static Message of(int id, String text, Calendar created, Person person, Room room) {
        Message message = Message.of(text, created, person, room);
        message.setId(id);
        return message;
    }
}
