package ru.job4j.forum.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.forum.Main;
import ru.job4j.forum.model.Authority;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.model.User;
import ru.job4j.forum.repository.CommentRepository;
import ru.job4j.forum.repository.PostRepository;
import ru.job4j.forum.repository.UserRepository;

import java.util.Calendar;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 04.01.2021.
 */
@SpringBootTest(classes = Main.class)
public class CommentServiceTest {
    @Autowired
    private PostServiceForRepository postServiceForRepository;
    @Autowired
    private UserServiceForRepository userServiceForRepository;
    @Autowired
    private CommentService commentService;
    private User user;
    private Post post;
    private Comment comment;

    @BeforeEach
    void start() {
        this.user = User.of("name", "password", Authority.of(1, ""));
        this.post = Post.of(0, "name", "decs", Calendar.getInstance(), this.user);
        this.comment = Comment.of(0, "comment", Calendar.getInstance(), this.post, this.user);
        this.userServiceForRepository.save(this.user);
        this.postServiceForRepository.save(this.post);
    }

    @AfterEach
    void finish() {
        if (this.comment.getId() != 0) {
            this.commentService.delete(this.comment);
        }
        this.postServiceForRepository.delete(this.post);
        this.userServiceForRepository.delete(this.user);
    }

    @Test
    void testAddCommentShouldOk() {
        this.comment = this.commentService.save(this.comment);
        Assert.assertThat(this.commentService.findAll().iterator().next().getId(), is(this.comment.getId()));
    }

    @Test
    void testAddCommentCommentIsEmptyShouldException() {
        this.comment.setComment("");
        Assertions.assertThrows(NullPointerException.class, () -> {
            this.comment = this.commentService.save(this.comment);
        });
    }

    @Test
    void testAddCommentPostIsEmptyShouldException() {
        this.comment.setPost(null);
        Assertions.assertThrows(NullPointerException.class, () -> {
            this.comment = this.commentService.save(this.comment);
        });
    }

    @Test
    void testFindById() {
        this.comment = this.commentService.save(this.comment);
        Assert.assertTrue(this.commentService.findById(this.comment.getId()).isPresent());
    }

    @Test
    void testFindByIdWhereIdIsZeroShouldIllegalArgumentException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            this.commentService.findById(this.comment.getId());
        });
    }

    @Test
    void testExistById() {
        Assert.assertFalse(this.commentService.existsById(15));
        this.comment = this.commentService.save(this.comment);
        Assert.assertTrue(this.commentService.existsById(this.comment.getId()));
    }

    @Test
    void testExistByIdWhereIdIsZeroShouldIllegalArgumentException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            this.commentService.existsById(this.comment.getId());
        });
    }

    @Test
    void testFindAll() {
        Assert.assertFalse(this.commentService.findAll().iterator().hasNext());
        this.comment = this.commentService.save(this.comment);
        Assert.assertTrue(this.commentService.findAll().iterator().hasNext());
    }

    @Test
    void testDeleteByIdElementShouldOk() {
        this.comment = this.commentService.save(this.comment);
        Assert.assertTrue(this.commentService.findAll().iterator().hasNext());
        this.commentService.deleteById(this.comment.getId());
        Assert.assertFalse(this.commentService.findAll().iterator().hasNext());
    }

    @Test
    void testDeleteByIdElementIdIsZeroShouldException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            this.commentService.deleteById(this.comment.getId());
        });
    }

    @Test
    void testDeleteElementShouldOk() {
        this.comment = this.commentService.save(this.comment);
        Assert.assertTrue(this.commentService.findAll().iterator().hasNext());
        this.commentService.delete(this.comment);
        Assert.assertFalse(this.commentService.findAll().iterator().hasNext());
    }
    @Test
    void testDeleteElementIdIsZeroShouldException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            this.commentService.delete(this.comment);
        });
    }

    @Test
    void testDeleteAllElementShouldOk() {
        this.comment = this.commentService.save(this.comment);
        Assert.assertTrue(this.commentService.findAll().iterator().hasNext());
        this.commentService.deleteAll();
        Assert.assertFalse(this.commentService.findAll().iterator().hasNext());
    }
}