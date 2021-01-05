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

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 05.01.2021.
 */
@SpringBootTest(classes = Main.class)
class AuthorityServiceTest {
    private Authority authority;
    @Autowired
    private AuthorityService authorityService;

    @BeforeEach
    void start() {
        this.authority = Authority.of(0, "Authority_Test");
    }

    @AfterEach
    void finish() {
        if (this.authority.getId() != 0) {
            this.authorityService.delete(this.authority.getId());
        }
    }


    @Test
    void testSaveElementShouldOk() {
        this.authority = this.authorityService.save(this.authority);
        Assert.assertThat(this.authority.getId(), is(greaterThan(2)));
    }

    @Test
    void testSaveElementWithIsEmptyNameShouldNullPointerException() {
        this.authority.setAuthority("");
        Assertions.assertThrows(NullPointerException.class, () -> {
            this.authority = this.authorityService.save(this.authority);
        });
    }

    @Test
    void testFindByNameElementWithIsEmptyNameShouldNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            this.authorityService.findByName("");
        });
    }

    @Test
    void testFindByNameElementShouldElement() {
        this.authority = this.authorityService.save(this.authority);
        Assert.assertThat(this.authorityService.findByName(this.authority.getAuthority()), is(this.authority));
    }

    @Test
    void testDeleteElementShouldOk() {
        this.authority = this.authorityService.save(this.authority);
        Assert.assertThat(this.authorityService.findByName(this.authority.getAuthority()), is(this.authority));
        this.authorityService.delete(this.authority.getId());
        Assert.assertNull(this.authorityService.findByName(this.authority.getAuthority()));
    }

    @Test
    void testDeleteElementWithIsIdZeroShouldNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            this.authorityService.delete(this.authority.getId());
        });
    }

    @Test
    void findAllTest() {
        Assert.assertThat(this.authorityService.findAll().size(), is(greaterThan(1)));
    }
}