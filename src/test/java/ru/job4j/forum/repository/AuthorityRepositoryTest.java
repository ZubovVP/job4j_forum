package ru.job4j.forum.repository;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.forum.Main;
import ru.job4j.forum.model.Authority;

import java.util.List;

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
class AuthorityRepositoryTest {
    private Authority authority;
    @Autowired
    private AuthorityRepository authorityRepository;

    @BeforeEach
    void start() {
        this.authority = Authority.of(0, "Authority_Test");
    }

    @AfterEach
    void finish() {
        this.authorityRepository.delete(this.authority.getId());
    }

    @Test
    void testSaveElementIdDB() {
        this.authority = this.authorityRepository.save(this.authority);
        Assert.assertThat(this.authority.getId(), is(greaterThan(2)));
    }

    @Test
    void testFindAllElements() {
        this.authority = this.authorityRepository.save(this.authority);
        List<Authority> listOfAuthorities = this.authorityRepository.findAll();
        Assert.assertTrue(listOfAuthorities.contains(this.authority));
    }

    @Test
    void testDeleteElementFromDB() {
        this.authority = this.authorityRepository.save(this.authority);
        Assert.assertThat(this.authorityRepository.findAll().size(), is(3));
        this.authorityRepository.delete(this.authority.getId());
        Assert.assertThat(this.authorityRepository.findAll().size(), is(2));
    }

    @Test
    void testFindByNameFromDB() {
        Assert.assertNull(this.authorityRepository.findByName("Authority_Test"));
        this.authority = this.authorityRepository.save(this.authority);
        Assert.assertNotNull(this.authorityRepository.findByName("Authority_Test"));
    }
}