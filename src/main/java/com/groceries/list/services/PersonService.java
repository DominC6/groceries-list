package com.groceries.list.services;

import com.groceries.list.data.PersonEntity;
import com.groceries.list.dto.Cart;
import com.groceries.list.dto.Person;
import com.groceries.list.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonService {
    private static final String NAME_BLANK = "Name must be filled";
    private static final String NOT_FOUND = "Person not found";
    private final PersonRepository personRepository;

    public Person registerUser(Person person) {
        if (person.getName() == null || person.getName().isBlank()) {
            throw new IllegalArgumentException(NAME_BLANK);
        }
        PersonEntity personEntity = PersonEntity.from(person);
        PersonEntity persistedPerson = personRepository.save(personEntity);
        return Person.from(persistedPerson);
    }

    public Person getData(Long personId) {
        PersonEntity personEntity = personRepository.findByIdFetchCarts(personId);
        if (personEntity == null) {
            throw new EntityNotFoundException(NOT_FOUND);
        }

        Person person =  Person.from(personEntity);
        List<Cart> carts = personEntity.getCarts().stream()
                .map(Cart::from)
                .toList();
        person.setCarts(carts);
        return person;
    }
}
