package ru.job4j.forum.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.forum.model.User;
import ru.job4j.forum.repository.UsersStore;
import ru.job4j.forum.service.UserService;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 03.12.2020.
 */
@Controller
public class RegControl {
    private UserService users = new UserService();
    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping("/reg")
    public String save(@ModelAttribute User user) {
        user.setPassword(this.encoder.encode(user.getPassword()));
        users.add(user);
        return "redirect:/login";
    }

    @GetMapping("/reg")
    public String reg() {
        return "reg";
    }
}
