package com.tmall.domain.repository.item;

import com.tmall.domain.entity.cart.item.Item;

import java.util.Optional;

public interface ItemRepository {
    Optional<Item> findItemByIdAndDeletedFalse(Long id);

    Item save(Item item);

    void delete(Long itemId);
}
