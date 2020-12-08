package ru.job4j.forum.repository;

import java.util.List;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 05.12.2020.
 */
public interface MainActions<E> {
    E add(E element);

    void update(E element);

    void delete(E element);

    List<E> getAll();

    E findById(int id);
}
