package ru.job4j.auth.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 08.02.2021.
 */
@Data
@EqualsAndHashCode
public class Employee {
    private int id;
    private String name;
    private String surname;
    private int inn;
    private Timestamp dateOfEmployment;
    private Person person;

    public static Employee of(int id, String name, String surname, int inn, Timestamp dateOfEmployment, Person person) {
        Employee emp = new Employee();
        emp.id = id;
        emp.name = name;
        emp.surname = surname;
        emp.inn = inn;
        emp.dateOfEmployment = dateOfEmployment;
        emp.person = person;
        return emp;
    }


}
