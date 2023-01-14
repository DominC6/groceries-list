package com.groceries.list.services;

import com.groceries.list.data.CartEntity;
import com.groceries.list.data.ItemEntity;
import com.groceries.list.dto.Item;
import com.groceries.list.repositories.CartRepository;
import com.groceries.list.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShoppingService {

    private final static String CART_NOT_FOUND = "Cart not found";

    private final ItemService itemService;
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;

    public void addItemToCart(Long cartId, Item item) {
        ItemEntity itemEntity = itemService.createItemEntity(item);
        Optional<CartEntity> cartEntityOptional = cartRepository.findById(cartId);
        if (cartEntityOptional.isEmpty()) {
            throw new EntityNotFoundException(CART_NOT_FOUND);
        }

        CartEntity cartEntity = cartEntityOptional.get();
        itemEntity.setCart(cartEntity);
        itemRepository.save(itemEntity);
    }

    public List<Item> getAllItemsForCart(Long cartId) {
        Optional<CartEntity> cartEntityOptional = cartRepository.findById(cartId);
        if (cartEntityOptional.isEmpty()) {
            throw new EntityNotFoundException(CART_NOT_FOUND);
        }

        List<ItemEntity> itemEntities = itemRepository.findAllByCart(cartEntityOptional.get());

        return itemEntities.stream()
                .map(Item::from)
                .toList();
    }
}
