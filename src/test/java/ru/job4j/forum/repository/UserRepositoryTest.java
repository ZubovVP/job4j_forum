package ru.job4j.forum.repository;

import org.junit.Assert;
import org.junit.jupiter.api.*;
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
class UserRepositoryTest {
    @Autowired
    private UserRepository usr;
    private User user;

    @BeforeEach
    void start() {
        this.user = User.of("name", "password", Authority.of(1, ""));
    }

    @AfterEach
    void finish() {
        this.usr.delete(this.user);
    }

    @Test
    void testAddUserInDB() {
        this.usr.save(this.user);
        User result = this.usr.findById(this.user.getId()).get();
        Assert.assertThat(result.getId(), is(this.user.getId()));
    }

    @Test
    void testUpdateUserInDB() {
        this.user = this.usr.save(this.user);
        this.user.setName("name2");
        this.user = this.usr.save(this.user);
        User result = this.usr.findById(this.user.getId()).get();
        Assert.assertThat(result.getName(), is(this.user.getName()));
    }

    @Test
    void testDeleteUserInDB() {
        this.user = this.usr.save(this.user);
        this.usr.delete(this.user);
        Assert.assertFalse(this.usr.findById(this.user.getId()).isPresent());
    }

    @Test
    void testFindByIdUserInDB() {
        this.user = this.usr.save(this.user);
        User result = this.usr.findById(this.user.getId()).get();
        Assert.assertThat(result.getName(), is(this.user.getName()));
    }


}