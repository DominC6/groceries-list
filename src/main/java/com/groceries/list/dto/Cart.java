package com.groceries.list.dto;

import com.groceries.list.data.CartEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Cart {
    private Long id;
    private BigDecimal receipt;
    private Boolean active;

    public static Cart from(CartEntity cartEntity) {
        Cart cart = new Cart();
        cart.setId(cartEntity.getId());
        cart.setReceipt(cartEntity.getReceipt());
        cart.setActive(cartEntity.getActive());
        return cart;
    }
}
