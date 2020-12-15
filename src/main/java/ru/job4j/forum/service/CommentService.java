package ru.job4j.forum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.job4j.forum.model.Comment;
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
 * Date: 14.12.2020.
 */
@Service
public class CommentService implements CrudRepository<Comment, Integer> {
    @Autowired
    @Qualifier("commentRepository")
    private CrudRepository<Comment, Integer> cr;
    @Autowired
    @Qualifier("userRepository")
    private CrudRepository<User, Integer> ur;
    @Autowired
    @Qualifier("postRepository")
    private CrudRepository<Post, Integer> pr;

    @Override
    public <S extends Comment> S save(S s) {
        checkUser(s);
        checkCreated(s);
        checkComment(s);
        if (s.getPost() != null && s.getPost().getId() != 0) {
            s = this.cr.save(s);
            Post post = this.pr.findById(s.getPost().getId()).get();
            post.getComments().add(s);
            this.pr.save(post);
        } else {
            throw new NullPointerException(s.toString() + "Id of post or is null");
        }
        return s;
    }

    @Override
    public <S extends Comment> Iterable<S> saveAll(Iterable<S> iterable) {
        for (Comment comment : iterable) {
            checkUser(comment);
            checkCreated(comment);
            checkComment(comment);
            if (comment.getPost() == null || comment.getPost().getId() == 0) {
                throw new NullPointerException(comment.toString() + "Id of post or is null");
            }
        }
        return this.cr.saveAll(iterable);
    }

    @Override
    public Optional<Comment> findById(Integer integer) {
        checkId(integer);
        return this.cr.findById(integer);
    }

    @Override
    public boolean existsById(Integer integer) {
        return this.cr.existsById(integer);
    }

    @Override
    public Iterable<Comment> findAll() {
        return this.cr.findAll();
    }

    @Override
    public Iterable<Comment> findAllById(Iterable<Integer> iterable) {
        for (Integer integer : iterable) {
            checkId(integer);
        }
        return this.cr.findAllById(iterable);
    }

    @Override
    public long count() {
        return this.cr.count();
    }

    @Override
    public void deleteById(Integer integer) {
        if (checkId(integer)) {
            this.cr.deleteById(integer);
        }
    }

    @Override
    public void delete(Comment comment) {
        checkId(comment.getId());
        this.cr.delete(comment);
    }

    @Override
    public void deleteAll(Iterable<? extends Comment> iterable) {
        for (Comment comment : iterable) {
            checkId(comment.getId());
        }
        this.cr.deleteAll(iterable);
    }

    @Override
    public void deleteAll() {
        this.deleteAll();
    }

    private boolean checkComment(Comment element) {
        return !isNullOrEmpty(element.getComment());
    }

    private <S extends Comment> S fillUser(S comment) {
        String nameOfOwner = SecurityContextHolder.getContext().getAuthentication().getName();
        for (User user : this.ur.findAll()) {
            if (user.getName().equals(nameOfOwner)) {
                comment.setUser(user);
                return comment;
            }
        }
        if (comment.getUser() == null || comment.getUser().getId() == 0) {
            throw new NullPointerException("Don't have user with this name - " + nameOfOwner);
        }
        return comment;
    }

    private <S extends Comment> S checkUser(S comment) {
        if (comment.getUser() == null || comment.getUser().getId() == 0) {
            comment = fillUser(comment);
        }
        return comment;
    }

    private <S extends Comment> S checkCreated(S comment) {
        if (comment.getCreated() == null) {
            comment.setCreated(Calendar.getInstance());
        }
        return comment;
    }

    private boolean checkId(int id) {
        if (id <= 0) {
            throw new NullPointerException(id + "Wrong id");
        }
        return true;
    }
}
