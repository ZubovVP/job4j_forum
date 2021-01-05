package ru.job4j.forum.repository;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.forum.Main;
import ru.job4j.forum.model.Authority;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.model.User;

import java.util.Calendar;

import static org.hamcrest.core.Is.is;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 05.01.2021.
 */
@SpringBootTest(classes = Main.class)
class CommentRepositoryTest {
    @Autowired
    private PostRepository pfr;
    @Autowired
    private UserRepository usr;
    @Autowired
    private CommentRepository cr;
    private User user;
    private Post post;
    private Comment comment;

    @BeforeEach
    void start() {
        this.user = User.of("name", "password", Authority.of(1, ""));
        this.post = Post.of(0, "name", "decs", Calendar.getInstance(), this.user);
        this.comment = Comment.of(0, "comment", Calendar.getInstance(), this.post, this.user);
        this.usr.save(this.user);
        this.pfr.save(this.post);
    }

    @AfterEach
    void finish() {
        this.cr.delete(this.comment);
        this.pfr.delete(this.post);
        usr.delete(this.user);
    }

    @Test
    void testAddCommentInDB() {
        this.cr.save(this.comment);
        Comment result = this.cr.findById(this.comment.getId()).get();
        Assert.assertThat(result.getId(), is(this.comment.getId()));
    }

    @Test
    void testUpdatePostInDB() {
        this.cr.save(this.comment);
        this.comment.setComment("comment2");
        this.cr.save(this.comment);
        Comment result = this.cr.findById(this.comment.getId()).get();
        Assert.assertThat(result.getComment(), is(this.comment.getComment()));
    }

    @Test
    void testDeleteUserInDB() {
        this.cr.save(this.comment);
        this.cr.delete(this.comment);
        Assert.assertFalse(this.cr.findById(this.comment.getId()).isPresent());
    }

    @Test
    void testFindByIdUserInDB() {
        this.cr.save(this.comment);
        Comment result = this.cr.findById(this.comment.getId()).get();
        Assert.assertThat(result.getComment(), is(this.comment.getComment()));
    }
}