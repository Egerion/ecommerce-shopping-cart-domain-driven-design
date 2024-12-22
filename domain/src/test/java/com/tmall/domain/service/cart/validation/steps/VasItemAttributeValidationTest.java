package com.tmall.domain.service.cart.validation.steps;

import com.tmall.domain.entity.product.Product;
import com.tmall.domain.exception.cart.CartValidationException;
import com.tmall.domain.model.ValidationResult;
import com.tmall.domain.model.cart.CartValidateModel;
import com.tmall.domain.service.cart.validation.steps.VasItemAttributeValidation;
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
public class VasItemAttributeValidationTest {

    @InjectMocks
    private VasItemAttributeValidation vasItemAttributeValidation;

    @Mock
    private CartValidateModel model;

    @Mock
    private Product product;

    @Test
    public void shouldValidateAndMoveToNextStep() {
        when(model.getProduct()).thenReturn(product);
        when(product.isDigitalCategory()).thenReturn(false);

        ValidationResult result = vasItemAttributeValidation.validate(model);

        assertTrue(result.isValid());
    }

    @Test
    public void shouldThrowValidationExceptionWhenProductTypeIsDigital() {
        when(model.getProduct()).thenReturn(product);
        when(product.isDigitalCategory()).thenReturn(true);

        CartValidationException exception = assertThrows(CartValidationException.class, () -> vasItemAttributeValidation.validate(model));

        assertEquals("Digital Item cannot be added as vasItem!", exception.getMessage());
    }
}
