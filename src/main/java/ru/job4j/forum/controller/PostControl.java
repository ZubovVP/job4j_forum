package ru.job4j.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.service.PostServiceForRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;


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
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd-MMM-yyyy");

    public PostControl(PostServiceForRepository posts) {
        this.posts = posts;
    }

    @GetMapping("/update")
    public String update(@RequestParam("id") int id, Model model) {
        Post post = this.posts.findById(id).get();
        model.addAttribute("stringData", SIMPLE_DATE_FORMAT.format(post.getCreated().getTime()));
        model.addAttribute("post", post);
        return "edit";
    }

    @PostMapping("/updatePost")
    public String update(@RequestParam("data") String data, @ModelAttribute Post post) throws ParseException {
        Date parsed = SIMPLE_DATE_FORMAT.parse(data);
        GregorianCalendar newCalendar = new GregorianCalendar();
        newCalendar.setTime(parsed);
        post.setCreated(newCalendar);
        Post result = this.posts.findById(post.getId()).get();
        result.setName(post.getName());
        result.setDesc(post.getDesc());
        this.posts.save(result);
        return "redirect:/";
    }

    @GetMapping("/post")
    public String post(@RequestParam("id") int id, Model model) {
        Post post = this.posts.findById(id).get();
        model.addAttribute("stringData", SIMPLE_DATE_FORMAT.format(post.getCreated().getTime()));
        model.addAttribute("post", post);
        return "post";
    }

}
