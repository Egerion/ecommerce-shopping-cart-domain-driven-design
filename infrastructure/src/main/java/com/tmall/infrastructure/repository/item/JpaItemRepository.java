package com.tmall.infrastructure.repository.item;

import com.tmall.domain.entity.cart.item.Item;
import com.tmall.domain.repository.item.ItemRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface JpaItemRepository extends ItemRepository, JpaRepository<Item, Long> {

    @Modifying
    @Transactional
    @Query("update Item item set item.deleted=true where item.id=:itemId")
    void delete(@Param("itemId") Long itemId);
}