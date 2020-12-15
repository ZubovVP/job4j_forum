package ru.job4j.forum.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.forum.model.Authority;

import java.util.List;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 15.12.2020.
 */
@Repository
public class AutohorityRepository {
    private final JdbcTemplate jdbc;

    public AutohorityRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Authority save(Authority authority) {
        jdbc.update("insert into authorities (name) values (?)",
                authority.getAuthority());
        return authority;
    }

    public List<Authority> findAll() {
        return jdbc.query("select id, name from authorities",
                (rs, row) -> {
                    Authority accident = Authority.of(rs.getInt("id"), rs.getString("name"));
                    return accident;
                });
    }

    public boolean delete(int id) {
        jdbc.update("DELETE FROM authorities WHERE id = ?", id);
        return true;
    }

    public Authority findByName(String name) {
        List<Authority> listOfAuthorities = jdbc.query("SELECT id, authority FROM authorities WHERE authority = '" + name + "'",
                (rs, row) -> Authority.of(rs.getInt("id"), rs.getString("authority")));
        return listOfAuthorities.get(0);
    }
}
