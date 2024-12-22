package com.tmall.application.service;

import com.tmall.application.dto.CartDto;
import com.tmall.application.dto.request.AddItemRequestDto;
import com.tmall.application.dto.request.AddVasItemRequestDto;
import com.tmall.application.dto.response.CartResponseDto;
import com.tmall.application.dto.response.CartResultResponseDto;
import com.tmall.application.mapper.CartMapper;
import com.tmall.domain.entity.cart.Cart;
import com.tmall.domain.entity.cart.item.Item;
import com.tmall.domain.entity.product.Product;
import com.tmall.domain.model.cart.CartValidateModel;
import com.tmall.domain.service.cart.CartService;
import com.tmall.domain.service.cart.validation.CartValidationService;
import com.tmall.domain.service.configuration.ConfigurationService;
import com.tmall.domain.service.item.ItemService;
import com.tmall.domain.service.product.ProductService;
import com.tmall.domain.service.promotion.PromotionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartApplicationServiceTest {

    @InjectMocks
    private CartApplicationService cartApplicationService;

    @Mock
    private CartService cartService;

    @Mock
    private ItemService itemService;

    @Mock
    private CartValidationService cartValidationService;

    @Mock
    private PromotionService promotionService;

    @Mock
    private CartMapper cartMapper;

    @Mock
    private ConfigurationService configurationService;

    @Mock
    private ProductService productService;

    @Test
    public void shouldDisplayCart() {
        Long cartId = 1L;
        Cart cart = new Cart();

        when(cartService.findById(cartId)).thenReturn(cart);
        when(cartMapper.toCartDto(cart)).thenReturn(CartDto.builder().build());
        when(configurationService.getAsString("cart.display.success")).thenReturn("Cart displayed successfully");

        CartResponseDto response = cartApplicationService.displayCart(cartId);

        assertTrue(response.isResult());
        assertEquals("Cart displayed successfully", response.getMessage());

        verify(cartService).findById(cartId);
        verify(cartMapper).toCartDto(cart);
    }

    @Test
    public void shouldAddItemToCart() {
        Long cartId = 1L;
        AddItemRequestDto request = new AddItemRequestDto();
        Cart cart = new Cart();
        Product product = new Product();

        when(cartService.findById(cartId)).thenReturn(cart);
        when(productService.findById(request.getItemId())).thenReturn(product);
        when(configurationService.getAsString("cart.add.item.success")).thenReturn("Item added successfully");

        CartResultResponseDto response = cartApplicationService.addItemToCart(cartId, request);

        assertTrue(response.isResult());
        assertEquals(1, cart.getItems().size());
        assertEquals("Item added successfully", response.getMessage());

        verify(cartValidationService).validateForAddItem(any(CartValidateModel.class));
        verify(itemService).createItem(any(Cart.class), any(Product.class), anyInt());
        verify(promotionService).selectBestPromotion(cart);
        verify(cartService).saveCart(cart);
    }

    @Test
    public void shouldAddVasItemToCart() {
        Long cartId = 1L;
        AddVasItemRequestDto request = new AddVasItemRequestDto();
        Cart cart = new Cart();
        Item item = new Item();
        Product product = new Product();

        when(cartService.findById(cartId)).thenReturn(cart);
        when(itemService.findById(request.getItemId())).thenReturn(item);
        when(productService.findById(request.getVasItemId())).thenReturn(product);
        when(configurationService.getAsString("cart.add.vasItem.success")).thenReturn("VAS item added successfully");

        CartResultResponseDto response = cartApplicationService.addVasItemToCart(cartId, request);

        assertTrue(response.isResult());
        assertEquals("VAS item added successfully", response.getMessage());

        verify(cartValidationService).validateForAddVasItem(any(CartValidateModel.class));
        verify(itemService).createVasItem(any(Item.class), any(Product.class), anyInt());
        verify(promotionService).selectBestPromotion(cart);
        verify(cartService).saveCart(cart);
    }

    @Test
    public void shouldRemoveItemFromCart() {
        Long cartId = 1L;
        Long itemId = 2L;
        Cart cart = new Cart();
        Item item = new Item();

        when(cartService.findById(cartId)).thenReturn(cart);
        when(itemService.findById(itemId)).thenReturn(item);
        when(configurationService.getAsString("cart.remove.item.success")).thenReturn("Item removed successfully");

        CartResultResponseDto response = cartApplicationService.removeItemFromCart(cartId, itemId);

        assertTrue(cart.getItems().isEmpty());
        assertTrue(response.isResult());
        assertEquals("Item removed successfully", response.getMessage());

        verify(cartValidationService).validateForRemoveItem(any(CartValidateModel.class));
        verify(itemService).deleteItem(item);
        verify(promotionService).selectBestPromotion(cart);
        verify(cartService).saveCart(cart);
    }

    @Test
    public void shouldThrowExceptionWhileResetingCart() {
        Long cartId = 1L;

        when(configurationService.getAsString("cart.reset.failed")).thenReturn("Failed to reset cart");

        doThrow(new RuntimeException("Reset failed")).when(cartService).resetCart(cartId);

        CartResultResponseDto response = cartApplicationService.resetCart(cartId);

        assertEquals("Failed to reset cart", response.getMessage());

        verify(cartService, times(1)).resetCart(cartId);
    }


    @Test
    public void shouldResetCart() {
        Long cartId = 1L;
        when(configurationService.getAsString("cart.reset.success")).thenReturn("Cart reset successfully");

        CartResultResponseDto response = cartApplicationService.resetCart(cartId);

        assertTrue(response.isResult());
        assertEquals("Cart reset successfully", response.getMessage());
    }
}