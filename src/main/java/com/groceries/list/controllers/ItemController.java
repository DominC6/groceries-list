package com.groceries.list.controllers;

import com.groceries.list.dto.Item;
import com.groceries.list.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item-api")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PatchMapping("/items/{id}")
    public ResponseEntity<Item> checkItemAsPut(@PathVariable("id") Long id ) {
        Item item = itemService.checkItemAsPut(id);

        return ResponseEntity.ok(item);
    }
}
