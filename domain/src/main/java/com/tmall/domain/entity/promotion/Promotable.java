package com.tmall.domain.entity.promotion;

import com.tmall.domain.entity.cart.item.Item;

import java.math.BigDecimal;
import java.util.List;

public interface Promotable {
    List<Item> getItems();

    BigDecimal getTotalPrice();
}