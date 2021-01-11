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
import ru.job4j.forum.model.User;

import static org.hamcrest.core.Is.is;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 05.01.2021.
 */
@SpringBootTest(classes = Main.class)
class UserServiceForRepositoryTest {
    private User user;
    @Autowired
    private UserServiceForRepository userServiceForRepository;

    @BeforeEach
    void start() {
        this.userServiceForRepository.deleteAll();
        this.user = User.of("name", "password", Authority.of(1, ""));
    }

    @AfterEach
    void finish() {
        if (this.user.getId() != 0) {
            this.userServiceForRepository.delete(this.user);
        }
    }

    @Test
    void testAddElementShouldOk() {
        this.user = this.userServiceForRepository.save(this.user);
        Assert.assertThat(this.userServiceForRepository.findAll().iterator().next().getId(), is(this.user.getId()));
    }

    @Test
    void testAddElementNameIsEmptyShouldException() {
        this.user.setName("");
        Assertions.assertThrows(NullPointerException.class, () -> {
            this.user = this.userServiceForRepository.save(this.user);
        });
    }

    @Test
    void testAddElementPasswordIsEmptyShouldException() {
        this.user.setPassword("");
        Assertions.assertThrows(NullPointerException.class, () -> {
            this.user = this.userServiceForRepository.save(this.user);
        });
    }

    @Test
    void testFindById() {
        this.user = this.userServiceForRepository.save(this.user);
        Assert.assertTrue(this.userServiceForRepository.findById(this.user.getId()).isPresent());
    }

    @Test
    void testFindByIdWhereIdIsZeroShouldIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            this.userServiceForRepository.findById(this.user.getId());
        });
    }

    @Test
    void testExistById() {
        Assert.assertFalse(this.userServiceForRepository.existsById(15));
        this.user = this.userServiceForRepository.save(this.user);
        Assert.assertTrue(this.userServiceForRepository.existsById(this.user.getId()));
    }

    @Test
    void testExistByIdWhereIdIsZeroShouldIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            this.userServiceForRepository.existsById(this.user.getId());
        });
    }

    @Test
    void testFindAll() {
        Assert.assertFalse(this.userServiceForRepository.findAll().iterator().hasNext());
        this.user = this.userServiceForRepository.save(this.user);
        Assert.assertTrue(this.userServiceForRepository.findAll().iterator().hasNext());
    }

    @Test
    void testDeleteByIdElementShouldOk() {
        this.user = this.userServiceForRepository.save(this.user);
        Assert.assertTrue(this.userServiceForRepository.findAll().iterator().hasNext());
        this.userServiceForRepository.deleteById(this.user.getId());
        Assert.assertFalse(this.userServiceForRepository.findAll().iterator().hasNext());
    }

    @Test
    void testDeleteByIdElementIdIsZeroShouldException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            this.userServiceForRepository.deleteById(this.user.getId());
        });
    }

    @Test
    void testDeleteElementShouldOk() {
        this.user = this.userServiceForRepository.save(this.user);
        Assert.assertTrue(this.userServiceForRepository.findAll().iterator().hasNext());
        this.userServiceForRepository.delete(this.user);
        Assert.assertFalse(this.userServiceForRepository.findAll().iterator().hasNext());
    }

    @Test
    void testDeleteElementIdIsZeroShouldException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            this.userServiceForRepository.delete(this.user);
        });
    }

    @Test
    void testDeleteAllElementShouldOk() {
        this.user = this.userServiceForRepository.save(this.user);
        Assert.assertTrue(this.userServiceForRepository.findAll().iterator().hasNext());
        this.userServiceForRepository.deleteAll();
        Assert.assertFalse(this.userServiceForRepository.findAll().iterator().hasNext());
    }
}