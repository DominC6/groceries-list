package com.groceries.list.repositories;

import com.groceries.list.data.CartEntity;
import com.groceries.list.data.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    List<ItemEntity> findAllByCart(CartEntity cart);
}
