package ru.job4j.forum.service;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.forum.Main;
import ru.job4j.forum.model.Authority;
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
class PostServiceForRepositoryTest {
    private User user;
    private Post post;
    @Autowired
    private UserServiceForRepository userServiceForRepository;
    @Autowired
    private PostServiceForRepository postServiceForRepository;

    @BeforeEach
    void start() {
        this.user = User.of("name", "password", Authority.of(1, ""));
        this.post = Post.of(0, "name", "decs", Calendar.getInstance(), this.user);
        this.userServiceForRepository.save(this.user);
    }

    @AfterEach
    void finish() {
        if (post.getId() != 0) {
            this.postServiceForRepository.delete(post);
        }
        this.userServiceForRepository.delete(this.user);
    }

    @Test
    void testAddElementShouldOk() {
        this.post = this.postServiceForRepository.save(this.post);
        Assert.assertThat(this.postServiceForRepository.findAll().iterator().next().getId(), is(this.post.getId()));
    }

    @Test
    void testAddPostNameIsEmptyShouldException() {
        this.post.setName("");
        Assertions.assertThrows(NullPointerException.class, () -> {
            this.post = this.postServiceForRepository.save(this.post);
        });
    }

    @Test
    void testAddPostDescriptionIsEmptyShouldException() {
        this.post.setDesc("");
        Assertions.assertThrows(NullPointerException.class, () -> {
            this.post = this.postServiceForRepository.save(this.post);
        });
    }

    @Test
    void testFindById() {
        this.post = this.postServiceForRepository.save(this.post);
        Assert.assertTrue(this.postServiceForRepository.findById(this.post.getId()).isPresent());
    }

    @Test
    void testFindByIdWhereIdIsZeroShouldIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            this.postServiceForRepository.findById(this.post.getId());
        });
    }

    @Test
    void testExistById() {
        Assert.assertFalse(this.postServiceForRepository.existsById(15));
        this.post = this.postServiceForRepository.save(this.post);
        Assert.assertTrue(this.postServiceForRepository.existsById(this.post.getId()));
    }

    @Test
    void testExistByIdWhereIdIsZeroShouldIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            this.postServiceForRepository.existsById(this.post.getId());
        });
    }

    @Test
    void testFindAll() {
        Assert.assertFalse(this.postServiceForRepository.findAll().iterator().hasNext());
        this.post = this.postServiceForRepository.save(this.post);
        Assert.assertTrue(this.postServiceForRepository.findAll().iterator().hasNext());
    }

    @Test
    void testDeleteByIdElementShouldOk() {
        this.post = this.postServiceForRepository.save(this.post);
        Assert.assertTrue(this.postServiceForRepository.findAll().iterator().hasNext());
        this.postServiceForRepository.deleteById(this.post.getId());
        Assert.assertFalse(this.postServiceForRepository.findAll().iterator().hasNext());
    }

    @Test
    void testDeleteByIdElementIdIsZeroShouldException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            this.postServiceForRepository.deleteById(this.post.getId());
        });
    }

    @Test
    void testDeleteElementShouldOk() {
        this.post = this.postServiceForRepository.save(this.post);
        Assert.assertTrue(this.postServiceForRepository.findAll().iterator().hasNext());
        this.postServiceForRepository.delete(this.post);
        Assert.assertFalse(this.postServiceForRepository.findAll().iterator().hasNext());
    }

    @Test
    void testDeleteElementIdIsZeroShouldException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            this.postServiceForRepository.delete(this.post);
        });
    }

    @Test
    void testDeleteAllElementShouldOk() {
        this.post = this.postServiceForRepository.save(this.post);
        Assert.assertTrue(this.postServiceForRepository.findAll().iterator().hasNext());
        this.postServiceForRepository.deleteAll();
        Assert.assertFalse(this.postServiceForRepository.findAll().iterator().hasNext());
    }

}