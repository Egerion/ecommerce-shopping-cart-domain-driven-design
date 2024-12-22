package com.tmall.domain.service.cart;

import com.tmall.domain.entity.cart.Cart;
import com.tmall.domain.exception.cart.CartNotFoundException;
import com.tmall.domain.repository.cart.CartRepository;
import com.tmall.domain.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ItemService itemService;

    public Cart findById(Long id) {
        return cartRepository.findCartByIdAndDeletedFalse(id).orElseThrow(() -> new CartNotFoundException("cart not found with Id:" + id));
    }

    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }

    public void resetCart(Long cartId) {
        Cart cart = findById(cartId);
        cart.getItems().forEach(itemService::deleteItem);
        cart.setPromotion(null);
        cartRepository.save(cart);
    }
}