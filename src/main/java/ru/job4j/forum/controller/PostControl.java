package ru.job4j.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.service.PostServiceForRepository;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 15.12.2020.
 */
@Controller
public class PostControl {
    private final PostServiceForRepository posts;

    public PostControl(PostServiceForRepository posts) {
        this.posts = posts;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") Integer id, Model model) {
        Post post = this.posts.findById(id).get();
        model.addAttribute("currentPost", post);
        return "edit";
    }

    @PostMapping("/updatePost")
    public String update(@ModelAttribute Post post) {
        this.posts.save(post);
        return "redirect:/";
    }

    @RequestMapping(value = "/post/{id}", method = RequestMethod.GET)
    public String post(@PathVariable("id") Integer id, Model model) {
        Post post = this.posts.findById(id).get();
        model.addAttribute("currentPost", post);
        return "post";
    }

}
