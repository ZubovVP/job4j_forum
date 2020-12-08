package ru.job4j.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.service.PostService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 03.12.2020.
 */
@Controller
public class IndexControl {
    private final PostService posts;
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd-MMM-yyyy");

    public IndexControl(PostService posts) {
        this.posts = posts;
    }

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        model.addAttribute("posts", posts.getAll());
        return "index";
    }

    @GetMapping("/update")
    public String update(@RequestParam("id") int id, Model model) {
        Post post = this.posts.findById(id);
        model.addAttribute("stringData", SIMPLE_DATE_FORMAT.format(post.getCreated().getTime()));
        model.addAttribute("post", post);
        return "edit";
    }

    @PostMapping("/update")
    public String update(@RequestParam("data") String data, @ModelAttribute Post post) throws ParseException {
        Date parsed = SIMPLE_DATE_FORMAT.parse(data);
        GregorianCalendar newCalendar = new GregorianCalendar();
        newCalendar.setTime(parsed);
        post.setCreated(newCalendar);
        this.posts.update(post);
        return "redirect:/";
    }

    @GetMapping("/post")
    public String post(@RequestParam("id") int id, Model model) {
        Post post = this.posts.findById(id);
        model.addAttribute("stringData", SIMPLE_DATE_FORMAT.format(post.getCreated().getTime()));
        model.addAttribute("post", post);
        return "post";
    }
}
