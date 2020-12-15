package ru.job4j.forum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.model.User;

import java.util.Calendar;
import java.util.Optional;

import static joptsimple.internal.Strings.isNullOrEmpty;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 12.12.2020.
 */
@Service
public class PostServiceForRepository implements CrudRepository<Post, Integer> {
    @Autowired
    @Qualifier("postRepository")
    private CrudRepository<Post, Integer> ps;
    @Autowired
    @Qualifier("userRepository")
    private CrudRepository<User, Integer> ur;


    @Override
    public <S extends Post> S save(S s) {
        s = checkOwner(s);
        checkCreated(s);
        checkNameAndDescr(s);
        return this.ps.save(s);
    }

    @Override
    public <S extends Post> Iterable<S> saveAll(Iterable<S> iterable) {
        for (Post post : iterable) {
            post = checkOwner(post);
            checkCreated(post);
            checkNameAndDescr(post);
        }
        return this.ps.saveAll(iterable);
    }

    @Override
    public Optional<Post> findById(Integer integer) {
        if (integer <= 0) {
            throw new IllegalArgumentException("Wring id");
        }
        return this.ps.findById(integer);
    }

    @Override
    public boolean existsById(Integer integer) {
        return findById(integer).isPresent();
    }

    @Override
    public Iterable<Post> findAll() {
        return this.ps.findAll();
    }

    @Override
    public Iterable<Post> findAllById(Iterable<Integer> iterable) {
        for (Integer id : iterable) {
            if (id <= 0) {
                throw new IllegalArgumentException("Wring id");
            }
        }
        return this.ps.findAllById(iterable);
    }

    @Override
    public long count() {
        return this.ps.count();
    }

    @Override
    public void deleteById(Integer integer) {
        if (integer <= 0) {
            throw new IllegalArgumentException("Wring id");
        }
        this.ps.deleteById(integer);
    }

    @Override
    public void delete(Post post) {
        if (post.getId() <= 0) {
            throw new IllegalArgumentException("Wring id");
        }
        this.ps.delete(post);
    }

    @Override
    public void deleteAll(Iterable<? extends Post> iterable) {
        for (Post post : iterable) {
            if (post.getId() <= 0) {
                throw new IllegalArgumentException("Wring id");
            }
        }
        this.ps.deleteAll(iterable);
    }

    @Override
    public void deleteAll() {
        this.ps.deleteAll();
    }

    private boolean checkNameAndDescr(Post element) {
        if (!isNullOrEmpty(element.getName()) && !isNullOrEmpty(element.getDesc())) {
            return true;
        }
        throw new NullPointerException(element.toString() + " Name or desc is null");
    }

    private <S extends Post> S fillOwner(S post) {
        String nameOfOwner = SecurityContextHolder.getContext().getAuthentication().getName();
        for (User user : this.ur.findAll()) {
            if (user.getName().equals(nameOfOwner)) {
                post.setOwner(user);
            }
        }
        if (post.getOwner() == null || post.getOwner().getId() == 0) {
            throw new NullPointerException("Don't have user with this name - " + nameOfOwner);
        }
        return post;
    }

    private <S extends Post> S checkOwner(S post) {
        if (post.getOwner() == null || post.getOwner().getId() == 0) {
            post = fillOwner(post);
        }
        return post;
    }

    private <S extends Post> S checkCreated(S post) {
        if (post.getCreated() == null) {
            post.setCreated(Calendar.getInstance());
        }
        return post;
    }
}
