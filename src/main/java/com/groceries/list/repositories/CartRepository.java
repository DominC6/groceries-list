package com.groceries.list.repositories;

import com.groceries.list.data.CartEntity;
import com.groceries.list.data.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<CartEntity, Long> {
    List<CartEntity> findAllByPersonAndActiveIsTrue(PersonEntity person);

    @Query("SELECT c FROM CartEntity c JOIN FETCH c.person where c.id = (:cartId)")
    CartEntity findByIdAndFetchPerson(Long cartId);
}
