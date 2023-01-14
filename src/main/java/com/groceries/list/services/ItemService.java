package com.groceries.list.services;

import com.groceries.list.data.ItemEntity;
import com.groceries.list.dto.Item;
import com.groceries.list.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final static String ITEM_NOT_FOUND = "Item not found.";

    private final ItemRepository itemRepository;

    @Transactional
    public ItemEntity createItemEntity(Item item) {
        item.validateInputItem();
        ItemEntity itemEntity = ItemEntity.from(item);
        return itemRepository.save(itemEntity);
    }

    public Item checkItemAsPut(Long itemId) {
        Optional<ItemEntity> itemEntityOptional = itemRepository.findById(itemId);
        if (itemEntityOptional.isEmpty()) {
            throw new EntityNotFoundException(ITEM_NOT_FOUND);
        }
        ItemEntity itemEntity = itemEntityOptional.get();
        itemEntity.setItemInCart(true);
        return Item.from(itemRepository.save(itemEntity));
    }
}
