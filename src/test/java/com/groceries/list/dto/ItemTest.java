package com.groceries.list.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ItemTest {

    @Test
    public void shouldValidateInputItem() {
        //given
        Item item = new Item();
        item.setName("test");
        item.setAmount(1);

        //when + then
        assertDoesNotThrow(item::validateInputItem);
    }

    @Test
    public void shouldThrowWhenNameIsNull() {
        //given
        Item item = new Item();
        item.setAmount(1);

        //when + then
        assertThrows(IllegalArgumentException.class, item::validateInputItem);
    }
}