package ru.job4j.chat.controllers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.chat.domain.Person;
import ru.job4j.chat.domain.Role;
import ru.job4j.chat.repository.PersonRepository;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 24.02.2021.
 */
@RestController
@RequestMapping("/persons")
public class PersonAuthController {
    private PersonRepository persons;
    private BCryptPasswordEncoder encoder;


    public PersonAuthController(PersonRepository persons,
                                BCryptPasswordEncoder encoder) {
        this.persons = persons;
        this.encoder = encoder;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        person.setCreated(System.currentTimeMillis());
        person.setRole(Role.of(1, "user"));
        person.setRooms(new HashSet<>());
        person.setMessages(new HashSet<>());
        persons.save(person);
    }

    @GetMapping("/all")
    public List<Person> findAll() {
        return StreamSupport.stream(
                this.persons.findAll().spliterator(), false
        ).peek(person -> person.setPassword("****")).collect(Collectors.toList());
    }
}
