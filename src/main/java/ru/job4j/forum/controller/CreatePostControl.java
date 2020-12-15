package ru.job4j.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.service.PostServiceForRepository;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 08.12.2020.
 */
@Controller
public class CreatePostControl {
    private PostServiceForRepository pr;

    public CreatePostControl(PostServiceForRepository ps) {
        this.pr = ps;
    }

    @GetMapping("/createPost")
    public String createPost() {
        return "createPost";
    }

    @PostMapping("/savePost")
    public String savePost(@ModelAttribute Post post) {
        this.pr.save(post);
        return "redirect:/";
    }
}
