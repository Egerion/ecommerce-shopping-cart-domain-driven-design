package com.tmall.infrastructure.repository.cart;

import com.tmall.domain.entity.cart.Cart;
import com.tmall.domain.repository.cart.CartRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCartRepository extends CartRepository, JpaRepository<Cart, Long> {

    Cart save(Cart cart);
}