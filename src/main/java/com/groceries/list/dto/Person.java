package com.groceries.list.dto;

import com.groceries.list.data.PersonEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Person {
    private Long id;
    private String name;
    private BigDecimal spent;
    private List<Cart> carts;

    public static Person from(PersonEntity personEntity) {
        Person person = new Person();
        person.setName(personEntity.getName());
        person.setId(personEntity.getId());
        person.setSpent(personEntity.getSpent());
        return person;
    }
}
