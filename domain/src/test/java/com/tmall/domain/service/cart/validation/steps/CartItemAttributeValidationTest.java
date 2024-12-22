package com.tmall.domain.service.cart.validation.steps;

import com.tmall.domain.entity.category.Category;
import com.tmall.domain.entity.product.Product;
import com.tmall.domain.entity.seller.Seller;
import com.tmall.domain.exception.cart.CartValidationException;
import com.tmall.domain.model.ValidationResult;
import com.tmall.domain.model.cart.CartValidateModel;
import com.tmall.domain.service.cart.validation.steps.CartItemAttributeValidation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartItemAttributeValidationTest {

    @InjectMocks
    private CartItemAttributeValidation cartItemAttributeValidation;

    @Mock
    private CartValidateModel model;

    @Mock
    private Product product;

    @Mock
    private Seller seller;

    @Mock
    private Category category;

    @Test
    public void shouldValidateAndMoveToNextStep() {
        Long sellerId = 1L;
        Long categoryId = 1L;
        BigDecimal price = BigDecimal.valueOf(100);

        when(seller.getId()).thenReturn(sellerId);
        when(category.getId()).thenReturn(categoryId);
        when(product.getSeller()).thenReturn(seller);
        when(product.getCategory()).thenReturn(category);
        when(product.getStock()).thenReturn(5);
        when(product.getPrice()).thenReturn(price);
        when(model.getProduct()).thenReturn(product);
        when(model.getItemSellerId()).thenReturn(sellerId);
        when(model.getItemCategoryId()).thenReturn(categoryId);
        when(model.getItemCount()).thenReturn(1);
        when(model.getPrice()).thenReturn(price);

        ValidationResult result = cartItemAttributeValidation.validate(model);

        assertTrue(result.isValid());
    }

    @Test
    public void shouldThrowValidationExceptionWhenSellerIdMismatched() {
        when(seller.getId()).thenReturn(1L);
        when(product.getSeller()).thenReturn(seller);
        when(model.getProduct()).thenReturn(product);
        when(model.getItemSellerId()).thenReturn(2L);

        CartValidationException exception = assertThrows(CartValidationException.class, () -> cartItemAttributeValidation.validate(model));

        assertEquals("Product seller Id mismatched!", exception.getMessage());
    }

    @Test
    public void shouldThrowValidationExceptionWhenCategoryIdMismatched() {
        Long sellerId = 1L;
        Long categoryId = 1L;

        when(product.getSeller()).thenReturn(seller);
        when(seller.getId()).thenReturn(sellerId);
        when(category.getId()).thenReturn(categoryId);
        when(model.getItemSellerId()).thenReturn(sellerId);
        when(model.getItemCategoryId()).thenReturn(2L);
        when(model.getProduct()).thenReturn(product);
        when(product.getCategory()).thenReturn(category);

        CartValidationException exception = assertThrows(CartValidationException.class, () -> cartItemAttributeValidation.validate(model));

        assertEquals("Product category Id mismatched!", exception.getMessage());
    }

    @Test
    public void shouldThrowValidationExceptionWhenStockNotEnough() {
        Long sellerId = 1L;
        Long categoryId = 1L;

        when(product.getSeller()).thenReturn(seller);
        when(seller.getId()).thenReturn(sellerId);
        when(category.getId()).thenReturn(categoryId);
        when(product.getCategory()).thenReturn(category);
        when(product.getStock()).thenReturn(1);
        when(model.getItemSellerId()).thenReturn(sellerId);
        when(model.getItemCategoryId()).thenReturn(categoryId);
        when(model.getProduct()).thenReturn(product);
        when(model.getItemCount()).thenReturn(2);

        CartValidationException exception = assertThrows(CartValidationException.class, () -> cartItemAttributeValidation.validate(model));

        assertEquals("Insufficient amount of product stock", exception.getMessage());
    }

    @Test
    public void shouldThrowValidationExceptionWhenPriceMismatched() {
        Long sellerId = 1L;
        Long categoryId = 1L;
        int stock = 1;

        when(product.getSeller()).thenReturn(seller);
        when(seller.getId()).thenReturn(sellerId);
        when(category.getId()).thenReturn(categoryId);
        when(product.getCategory()).thenReturn(category);
        when(product.getStock()).thenReturn(stock);
        when(product.getPrice()).thenReturn(BigDecimal.valueOf(110));
        when(model.getItemSellerId()).thenReturn(sellerId);
        when(model.getItemCategoryId()).thenReturn(categoryId);
        when(model.getProduct()).thenReturn(product);
        when(model.getItemCount()).thenReturn(stock);
        when(model.getPrice()).thenReturn(BigDecimal.valueOf(100));

        CartValidationException exception = assertThrows(CartValidationException.class, () -> cartItemAttributeValidation.validate(model));

        assertEquals("Invalid item price", exception.getMessage());
    }
}