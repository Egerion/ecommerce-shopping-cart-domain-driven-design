package com.tmall.application.controller;

import com.tmall.application.dto.request.AddItemRequestDto;
import com.tmall.application.dto.request.AddVasItemRequestDto;
import com.tmall.application.dto.response.CartResponseDto;
import com.tmall.application.dto.response.CartResultResponseDto;
import com.tmall.application.service.CartApplicationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartControllerTest {

    @InjectMocks
    private CartController cartController;

    @Mock
    private CartApplicationService cartApplicationService;

    @Test
    public void shouldDisplayCart() {
        long cartId = 1L;
        CartResponseDto cartResponseDto = new CartResponseDto();

        when(cartApplicationService.displayCart(cartId)).thenReturn(cartResponseDto);

        ResponseEntity<CartResponseDto> response = cartController.displayCart(cartId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cartResponseDto, response.getBody());
    }

    @Test
    public void shouldAddItem() {
        long cartId = 1L;
        AddItemRequestDto requestDto = new AddItemRequestDto();
        CartResultResponseDto cartResultResponseDto = new CartResultResponseDto();

        when(cartApplicationService.addItemToCart(cartId, requestDto)).thenReturn(cartResultResponseDto);

        ResponseEntity<CartResultResponseDto> response = cartController.addItem(cartId, requestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cartResultResponseDto, response.getBody());
    }

    @Test
    public void shouldAddVasItem() {
        long cartId = 1L;
        AddVasItemRequestDto requestDto = new AddVasItemRequestDto();
        CartResultResponseDto cartResultResponseDto = new CartResultResponseDto();

        when(cartApplicationService.addVasItemToCart(cartId, requestDto)).thenReturn(cartResultResponseDto);

        ResponseEntity<CartResultResponseDto> response = cartController.addVasItem(cartId, requestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cartResultResponseDto, response.getBody());
    }

    @Test
    public void shouldDeleteItem() {
        long cartId = 1L;
        long itemId = 2L;
        CartResultResponseDto cartResultResponseDto = new CartResultResponseDto();

        when(cartApplicationService.removeItemFromCart(cartId, itemId)).thenReturn(cartResultResponseDto);

        ResponseEntity<CartResultResponseDto> response = cartController.deleteItem(cartId, itemId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cartResultResponseDto, response.getBody());
    }

    @Test
    public void shouldResetCart() {
        long cartId = 1L;
        CartResultResponseDto cartResultResponseDto = new CartResultResponseDto();

        when(cartApplicationService.resetCart(cartId)).thenReturn(cartResultResponseDto);

        ResponseEntity<CartResultResponseDto> response = cartController.reset(cartId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cartResultResponseDto, response.getBody());
    }
}