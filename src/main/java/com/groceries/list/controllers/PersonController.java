package com.groceries.list.controllers;

import com.groceries.list.dto.Cart;
import com.groceries.list.dto.Person;
import com.groceries.list.services.CartService;
import com.groceries.list.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/person-api")
@RequiredArgsConstructor
public class PersonController {

    private final static String NAME_BLANK = "Person name must be filled";

    private final PersonService personService;
    private final CartService cartService;

    @PostMapping("/persons")
    public ResponseEntity<Person> createUser(@RequestBody Person person) {
        if (person.getName() == null || person.getName().isBlank()) {
            throw new IllegalArgumentException(NAME_BLANK);
        }
        Person persistedPerson = personService.registerUser(person);
        return new ResponseEntity<>(persistedPerson, HttpStatus.CREATED);
    }

    @GetMapping("/persons")
    public ResponseEntity<Person> getUserData(@RequestParam Long personId) {
        Person person = personService.getData(personId);
        return ResponseEntity.ok(person);
    }

    @GetMapping("/persons/{personId}/carts")
    public ResponseEntity<List<Cart>> getActiveCartsForPerson(@PathVariable("personId") Long personId) {
        List<Cart> carts = cartService.getActiveCartsForPerson(personId);
        return ResponseEntity.ok(carts);
    }
}
