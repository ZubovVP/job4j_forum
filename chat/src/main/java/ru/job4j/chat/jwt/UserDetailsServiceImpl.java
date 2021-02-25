package ru.job4j.chat.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.chat.domain.Person;
import ru.job4j.chat.repository.PersonRepository;

import java.util.Iterator;

import static java.util.Collections.emptyList;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 24.02.2021.
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final PersonRepository persons;

    public UserDetailsServiceImpl(PersonRepository persons) {
        this.persons = persons;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Person result = null;
        Iterator<Person> persons = this.persons.findAll().iterator();
        while (persons.hasNext()) {
            Person p = persons.next();
            if (p.getLogin().equals(s)) {
                result = p;
                break;
            }
        }

        if (result == null) {
            throw new UsernameNotFoundException("Login - " + s);
        }
        log.info("IN loadUserByUsername - user with name : {} successfully loaded", s);
        return new User(result.getLogin(), result.getPassword(), emptyList());
    }
}
