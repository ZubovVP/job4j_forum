package ru.job4j.forum.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.service.CommentService;

import java.util.Calendar;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 15.12.2020.
 */
@Controller
public class CommentControl {
    private final CommentService cs;

    public CommentControl(CommentService cs) {
        this.cs = cs;
    }

    @PostMapping("/addComment")
    public String addComment(@RequestParam("text") String comment, @ModelAttribute("post") Post post) {
        this.cs.save(Comment.of(0, comment, Calendar.getInstance(), post, null));
        return "redirect:/post/" + post.getId();
    }
}
