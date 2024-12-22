package com.tmall.domain.service.cart.validation.steps;

import com.tmall.domain.entity.cart.Cart;
import com.tmall.domain.entity.cart.item.Item;
import com.tmall.domain.exception.cart.CartValidationException;
import com.tmall.domain.model.ValidationResult;
import com.tmall.domain.model.cart.CartValidateModel;
import com.tmall.domain.service.cart.validation.steps.RemoveItemValidation;
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
public class RemoveItemValidationTest {

    @InjectMocks
    private RemoveItemValidation removeItemValidation;

    @Mock
    private CartValidateModel model;

    @Mock
    private Cart cart;

    @Mock
    private Item item;

    @Test
    public void shouldValidateAndMoveToNextStep() {
        when(model.getCart()).thenReturn(cart);
        when(model.getItem()).thenReturn(item);
        when(cart.hasItem(item)).thenReturn(true);

        ValidationResult result = removeItemValidation.validate(model);

        assertTrue(result.isValid());
    }

    @Test
    public void shouldThrowValidationExceptionWhenItemNotFoundInCart() {
        when(model.getCart()).thenReturn(cart);
        when(model.getItem()).thenReturn(item);
        when(cart.hasItem(item)).thenReturn(false);

        CartValidationException exception = assertThrows(CartValidationException.class, () -> removeItemValidation.validate(model));

        assertEquals("Item not found in the cart!", exception.getMessage());
    }
}