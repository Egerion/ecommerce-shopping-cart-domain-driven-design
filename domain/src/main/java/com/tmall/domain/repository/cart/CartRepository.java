package com.tmall.domain.repository.cart;

import com.tmall.domain.entity.cart.Cart;

import java.util.Optional;

public interface CartRepository {
    Optional<Cart> findCartByIdAndDeletedFalse(Long id);

    Cart save(Cart cart);
}
