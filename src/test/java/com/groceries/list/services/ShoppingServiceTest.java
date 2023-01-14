package com.groceries.list.services;

import com.groceries.list.data.CartEntity;
import com.groceries.list.data.ItemEntity;
import com.groceries.list.dto.Item;
import com.groceries.list.repositories.CartRepository;
import com.groceries.list.repositories.ItemRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ShoppingServiceTest {

    @InjectMocks
    private ShoppingService shoppingService;
    @Mock
    private ItemService itemService;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private ItemRepository itemRepository;

    @Test
    public void shouldAddItemToCart() {
        //given
        Long cartId = 1L;
        ItemEntity itemEntity = prepareMockedItemEntity("szampon");
        CartEntity cartEntity = prepareMockedCartEntity(cartId);

        //and mocked
        when(itemService.createItemEntity(any())).thenReturn(itemEntity);
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cartEntity));

        //when
        assertDoesNotThrow(() -> shoppingService.addItemToCart(cartId, new Item()));

        //then
        ArgumentCaptor<ItemEntity> itemEntityArgumentCaptor = ArgumentCaptor.forClass(ItemEntity.class);
        verify(itemRepository, times(1)).save(itemEntityArgumentCaptor.capture());
        ItemEntity capturedEntity = itemEntityArgumentCaptor.getValue();
        assertEquals(cartEntity, capturedEntity.getCart());
    }

    @Test
    public void shouldThrowWhenNoCartFoundForGivenId() {
        //given
        Long cartId = 1L;
        ItemEntity itemEntity = prepareMockedItemEntity("szampon");

        //and mocked
        when(itemService.createItemEntity(any())).thenReturn(itemEntity);
        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        //when + then
        assertThrows(EntityNotFoundException.class, () -> shoppingService.addItemToCart(cartId, new Item()));
    }

    private ItemEntity prepareMockedItemEntity(String name) {
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setName(name);
        return itemEntity;
    }

    private CartEntity prepareMockedCartEntity(Long id) {
        CartEntity cartEntity = new CartEntity();
        cartEntity.setId(id);
        return cartEntity;
    }
}