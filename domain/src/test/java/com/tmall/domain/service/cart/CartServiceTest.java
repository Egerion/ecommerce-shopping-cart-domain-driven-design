package com.tmall.domain.service.cart;

import com.tmall.domain.entity.cart.Cart;
import com.tmall.domain.entity.cart.item.Item;
import com.tmall.domain.entity.promotion.CategoryPromotion;
import com.tmall.domain.exception.cart.CartNotFoundException;
import com.tmall.domain.repository.cart.CartRepository;
import com.tmall.domain.service.cart.CartService;
import com.tmall.domain.service.item.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceTest {

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ItemService itemService;

    @Test
    public void shouldFindById() {
        Long cartId = 1L;
        Cart mockCart = new Cart();

        when(cartRepository.findCartByIdAndDeletedFalse(cartId)).thenReturn(Optional.of(mockCart));

        Cart cart = cartService.findById(cartId);

        assertNotNull(cart);
        assertEquals(mockCart, cart);
        verify(cartRepository, times(1)).findCartByIdAndDeletedFalse(cartId);
    }

    @Test
    public void shouldThrowExceptionWhenCartNotFound() {
        Long cartId = 1L;

        when(cartRepository.findCartByIdAndDeletedFalse(cartId)).thenReturn(Optional.empty());

        assertThrows(CartNotFoundException.class, () -> cartService.findById(cartId));
    }


    @Test
    public void saveCart() {
        Cart cart = new Cart();

        cartService.saveCart(cart);

        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    public void resetCart() {
        Long cartId = 1L;
        Cart cart = new Cart();
        cart.setItems(List.of(new Item(), new Item()));
        cart.setPromotion(new CategoryPromotion());

        when(cartRepository.findCartByIdAndDeletedFalse(cartId)).thenReturn(Optional.of(cart));

        cartService.resetCart(cartId);

        assertNull(cart.getPromotion());
        verify(itemService, times(cart.getItems().size())).deleteItem(any(Item.class));
        verify(cartRepository, times(1)).save(cart);
    }
}
