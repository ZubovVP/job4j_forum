package ru.job4j.forum.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.forum.model.Authority;

import java.sql.PreparedStatement;
import java.util.List;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 15.12.2020.
 */
@Repository
public class AuthorityRepository {
    private final JdbcTemplate jdbc;

    public AuthorityRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Authority save(Authority authority) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement("insert into authorities (authority) values (?) ", new String[]{"id"});
            ps.setString(1, authority.getAuthority());
            return ps;
        }, holder);
        authority.setId(holder.getKey().intValue());
        return authority;
    }

    public List<Authority> findAll() {
        return this.jdbc.query("select id, authority from authorities",
                (rs, row) -> {
                    Authority accident = Authority.of(rs.getInt("id"), rs.getString("authority"));
                    return accident;
                });
    }

    public boolean delete(int id) {
        this.jdbc.update("DELETE FROM authorities WHERE id = ?", id);
        return true;
    }

    public Authority findByName(String name) {
        List<Authority> listOfAuthorities = this.jdbc.query("SELECT id, authority FROM authorities WHERE authority = '" + name + "'",
                (rs, row) -> Authority.of(rs.getInt("id"), rs.getString("authority")));
        return listOfAuthorities.isEmpty() ? null : listOfAuthorities.get(0);
    }
}
