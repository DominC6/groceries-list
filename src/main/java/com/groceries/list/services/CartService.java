package com.groceries.list.services;

import com.groceries.list.data.CartEntity;
import com.groceries.list.data.PersonEntity;
import com.groceries.list.dto.Cart;
import com.groceries.list.repositories.CartRepository;
import com.groceries.list.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final static String CART_NOT_FOUND = "Cart not found";
    private final static String PERSON_NOT_FOUND = "Person not found";
    private final static String NO_RECEIPT = "To deactivate cart, receipt must be given";

    private final CartRepository cartRepository;
    private final PersonRepository personRepository;

    @Transactional
    public Cart addNewCart(Long personId) {
        Optional<PersonEntity> personEntityOptional = personRepository.findById(personId);
        if (personEntityOptional.isEmpty()) {
            throw new EntityNotFoundException(PERSON_NOT_FOUND);
        }
        PersonEntity personEntity = personEntityOptional.get();

        CartEntity cartEntity = CartEntity.createEmptyCart();
        cartEntity.setPerson(personEntity);
        CartEntity persistedCart = cartRepository.save(cartEntity);
        return Cart.from(persistedCart);
    }

    @Transactional
    public void deactivateCart(Long cartId, BigDecimal receipt) {
        if (receipt.compareTo(BigDecimal.ZERO) < 1) {
            throw new IllegalArgumentException(NO_RECEIPT);
        }
        CartEntity cartEntity = cartRepository.findByIdAndFetchPerson(cartId);
        if (cartEntity == null) {
            throw new EntityNotFoundException(CART_NOT_FOUND);
        }
        cartEntity.setActive(false);
        cartRepository.save(cartEntity);
        PersonEntity person = cartEntity.getPerson();
        person.setSpent(person.getSpent().add(receipt));
        personRepository.save(person);
    }

    public List<Cart> getActiveCartsForPerson(Long personId) {
        Optional<PersonEntity> person = personRepository.findById(personId);
        if (person.isEmpty()) {
            throw new EntityNotFoundException(PERSON_NOT_FOUND);
        }
        List<CartEntity> cartEntities = cartRepository.findAllByPersonAndActiveIsTrue(person.get());

        return cartEntities.stream()
                .map(Cart::from)
                .toList();
    }
}
