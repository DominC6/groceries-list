package com.groceries.list.repositories;


import com.groceries.list.data.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    @Query("SELECT c FROM PersonEntity c JOIN FETCH c.carts where c.id = (:personId)")
    PersonEntity findByIdFetchCarts(Long personId);
}
