package com.tmall.domain.service.cart.validation.steps;

import com.tmall.domain.entity.cart.Cart;
import com.tmall.domain.entity.cart.item.Item;
import com.tmall.domain.exception.cart.CartValidationException;
import com.tmall.domain.model.ValidationResult;
import com.tmall.domain.model.cart.CartValidateModel;
import com.tmall.domain.service.cart.validation.steps.VasItemQuantityValidation;
import com.tmall.domain.service.configuration.ConfigurationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VasItemQuantityValidationTest {

    @InjectMocks
    private VasItemQuantityValidation vasItemQuantityValidation;

    @Mock
    private ConfigurationService configurationService;

    @Mock
    private CartValidateModel model;

    @Mock
    private Cart cart;

    @Mock
    private Item item;

    @Test
    public void shouldValidateAndMoveToNextStep() {
        when(model.getItem()).thenReturn(item);
        when(model.getVasItemCount()).thenReturn(1);
        when(model.getCart()).thenReturn(cart);
        when(cart.getItemCount()).thenReturn(2);

        when(configurationService.getAsInteger("cart.vasItem.max.count.perItem")).thenReturn(3);
        when(configurationService.getAsInteger("cart.max.item.count")).thenReturn(10);
        when(item.getTotalVasItemCount()).thenReturn(2);

        ValidationResult result = vasItemQuantityValidation.validate(model);

        assertTrue(result.isValid());
    }

    @Test
    public void shouldThrowValidationExceptionWhenMaxVasItemCountExceeded() {
        when(model.getItem()).thenReturn(item);
        when(model.getVasItemCount()).thenReturn(3);

        when(configurationService.getAsInteger("cart.vasItem.max.count.perItem")).thenReturn(3);
        when(item.getTotalVasItemCount()).thenReturn(3);

        CartValidationException exception = assertThrows(CartValidationException.class, () -> vasItemQuantityValidation.validate(model));

        assertEquals("Maximum vasItem limit exceeded for item", exception.getMessage());
    }

    @Test
    public void shouldThrowValidationExceptionWhenMaxCartItemCountExceeded() {
        when(model.getItem()).thenReturn(item);
        when(model.getVasItemCount()).thenReturn(1);
        when(model.getCart()).thenReturn(cart);
        when(cart.getItemCount()).thenReturn(30);

        when(configurationService.getAsInteger("cart.vasItem.max.count.perItem")).thenReturn(3);
        when(configurationService.getAsInteger("cart.max.item.count")).thenReturn(30);
        when(item.getTotalVasItemCount()).thenReturn(2);

        CartValidationException exception = assertThrows(CartValidationException.class, () -> vasItemQuantityValidation.validate(model));

        assertEquals("ItemCount exceeded!", exception.getMessage());
    }
}