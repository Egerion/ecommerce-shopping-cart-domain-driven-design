package com.tmall.domain.service.cart.validation.steps;

import com.tmall.domain.entity.cart.Cart;
import com.tmall.domain.exception.cart.CartValidationException;
import com.tmall.domain.model.ValidationResult;
import com.tmall.domain.model.cart.CartValidateModel;
import com.tmall.domain.service.cart.validation.steps.CartMaxPriceValidation;
import com.tmall.domain.service.configuration.ConfigurationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartMaxPriceValidationTest {

    @InjectMocks
    private CartMaxPriceValidation cartMaxPriceValidation;

    @Mock
    private ConfigurationService configurationService;

    @Test
    public void shouldValidateAndMoveToNextStep() {
        int maxPrice = 500;
        BigDecimal finalTotalPrice = BigDecimal.valueOf(300);
        BigDecimal priceToAdd = BigDecimal.valueOf(150);

        Cart cart = mock(Cart.class);

        CartValidateModel model = new CartValidateModel();
        model.setCart(cart);
        model.setPrice(priceToAdd);

        when(configurationService.getAsInteger("cart.max.price")).thenReturn(maxPrice);
        when(cart.getFinalTotalPrice()).thenReturn(finalTotalPrice);

        ValidationResult result = cartMaxPriceValidation.validate(model);

        assertNotNull(result);
        assertTrue(result.isValid());
    }

    @Test
    public void shouldThrowValidationExceptionWhenCartPriceExceeded() {
        int maxPrice = 500;
        BigDecimal finalTotalPrice = BigDecimal.valueOf(400);
        BigDecimal priceToAdd = BigDecimal.valueOf(200);

        Cart cart = mock(Cart.class);

        CartValidateModel model = new CartValidateModel();
        model.setCart(cart);
        model.setPrice(priceToAdd);

        when(cart.getFinalTotalPrice()).thenReturn(finalTotalPrice);
        when(configurationService.getAsInteger("cart.max.price")).thenReturn(maxPrice);

        CartValidationException exception = assertThrows(CartValidationException.class, () -> cartMaxPriceValidation.validate(model));

        assertEquals("Cart's max allowed amount is exceeded!", exception.getMessage());
    }
}
