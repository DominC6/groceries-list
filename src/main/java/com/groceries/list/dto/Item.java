package com.groceries.list.dto;

import com.groceries.list.data.Category;
import com.groceries.list.data.ItemEntity;
import lombok.Data;

@Data
public class Item {
    private static final String VALIDATION_EXCEPTION = "Item data is not correct";

    private Long id;
    private String name;
    private Category category;
    private boolean itemInCart;
    private Integer amount;

    public static Item from(ItemEntity itemEntity) {
        Item item = new Item();
        item.setId(itemEntity.getId());
        item.setName(itemEntity.getName());
        item.setAmount(itemEntity.getAmount());
        item.setCategory(itemEntity.getCategory());
        item.setItemInCart(itemEntity.isItemInCart());
        return item;
    }

    public void validateInputItem() {
        if ((this.name == null || this.name.isBlank()) ||
                (amount == null || amount < 1)) {
            throw new IllegalArgumentException(VALIDATION_EXCEPTION);
        }
    }
}
