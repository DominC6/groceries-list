package com.groceries.list.controllers;

import com.groceries.list.dto.Cart;
import com.groceries.list.dto.Item;
import com.groceries.list.services.CartService;
import com.groceries.list.services.ShoppingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart-api")
@RequiredArgsConstructor
public class CartController {

    private final static String ID_NOT_MATCH = "Cart ids don't match";

    private final CartService cartService;
    private final ShoppingService shoppingService;

    @PostMapping("/carts")
    public ResponseEntity<Cart> createEmptyCart(@RequestParam("personId") Long personId) {
        Cart createdCart = cartService.addNewCart(personId);
        return ResponseEntity.ok(createdCart);
    }

    @PatchMapping("/carts/{id}")
    public ResponseEntity<Void> deactivateCart(@PathVariable("id") Long id, @RequestBody Cart cart) {
        if (!id.equals(cart.getId())) {
            throw new IllegalArgumentException(ID_NOT_MATCH);
        }
        cartService.deactivateCart(id, cart.getReceipt());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/carts/{id}/items")
    public ResponseEntity<List<Item>> getAllItemsForCart(@PathVariable("id") Long id) {
        List<Item> items = shoppingService.getAllItemsForCart(id);

        return ResponseEntity.ok(items);
    }

    @PostMapping("/carts/{id}/items")
    public ResponseEntity<List<Item>> addItemToCart(@PathVariable("id") Long id, @RequestBody Item item) {
        shoppingService.addItemToCart(id, item);
        return new ResponseEntity<>(shoppingService.getAllItemsForCart(id), HttpStatus.CREATED);
    }
}
