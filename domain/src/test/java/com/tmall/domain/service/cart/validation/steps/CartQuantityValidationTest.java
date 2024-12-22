package com.tmall.domain.service.cart.validation.steps;

import com.tmall.domain.entity.cart.Cart;
import com.tmall.domain.entity.cart.item.Item;
import com.tmall.domain.entity.product.Product;
import com.tmall.domain.exception.cart.CartValidationException;
import com.tmall.domain.model.ValidationResult;
import com.tmall.domain.model.cart.CartValidateModel;
import com.tmall.domain.service.cart.validation.steps.CartQuantityValidation;
import com.tmall.domain.service.configuration.ConfigurationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartQuantityValidationTest {

    @InjectMocks
    private CartQuantityValidation cartQuantityValidation;

    @Mock
    private ConfigurationService configurationService;

    @Mock
    private Cart cart;

    @Mock
    private Product product;

    @Mock
    private Item item;

    @Test
    public void shouldValidateAndMoveToNextStep() {
        int itemCount = 1;
        CartValidateModel model = new CartValidateModel();
        model.setCart(cart);
        model.setProduct(product);
        model.setItemCount(itemCount);

        when(cart.getUniqueItemCount()).thenReturn(2);
        when(cart.getItemCount()).thenReturn(4);

        when(configurationService.getAsInteger("cart.max.unique.item.count")).thenReturn(10);
        when(configurationService.getAsInteger("cart.max.item.count")).thenReturn(30);
        when(configurationService.getAsInteger("cart.max.digital.item.count")).thenReturn(5);

        ValidationResult result = cartQuantityValidation.validate(model);

        assertNotNull(result);
        assertTrue(result.isValid());
    }

    @Test
    public void shouldThrowValidationExceptionWhenMaxUniqueItemCountExceeded() {
        int itemCount = 1;
        CartValidateModel model = new CartValidateModel();
        model.setCart(cart);
        model.setProduct(product);
        model.setItemCount(itemCount);

        when(cart.getUniqueItemCount()).thenReturn(10);
        when(cart.getItemByProduct(product)).thenReturn(Optional.of(item));

        when(configurationService.getAsInteger("cart.max.unique.item.count")).thenReturn(10);

        CartValidationException exception = assertThrows(CartValidationException.class, () -> cartQuantityValidation.validate(model));

        assertEquals("UniqueItemCount exceeded!", exception.getMessage());
    }

    @Test
    public void shouldThrowValidationExceptionWhenMaxItemCountExceeded() {
        int itemCount = 7;
        CartValidateModel model = new CartValidateModel();
        model.setCart(cart);
        model.setProduct(product);
        model.setItemCount(itemCount);

        when(cart.getUniqueItemCount()).thenReturn(10);
        when(cart.getItemCount()).thenReturn(30);

        when(configurationService.getAsInteger("cart.max.unique.item.count")).thenReturn(10);
        when(configurationService.getAsInteger("cart.max.item.count")).thenReturn(30);

        CartValidationException exception = assertThrows(CartValidationException.class, () -> cartQuantityValidation.validate(model));

        assertEquals("ItemCount exceeded!", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenMaxDigitalItemCountExceeded() {
        int itemCount = 5;
        CartValidateModel model = new CartValidateModel();
        model.setCart(cart);
        model.setProduct(product);
        model.setItemCount(itemCount);

        when(cart.getUniqueItemCount()).thenReturn(1);
        when(cart.getItemCount()).thenReturn(5);
        when(cart.getDigitalItemCount()).thenReturn(5);
        when(product.isDigitalCategory()).thenReturn(true);

        when(configurationService.getAsInteger("cart.max.unique.item.count")).thenReturn(10);
        when(configurationService.getAsInteger("cart.max.item.count")).thenReturn(30);
        when(configurationService.getAsInteger("cart.max.digital.item.count")).thenReturn(5);

        CartValidationException exception = assertThrows(CartValidationException.class, () -> cartQuantityValidation.validate(model));

        assertEquals("DigitalItemCount exceeded!", exception.getMessage());
    }
}