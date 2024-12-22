package com.tmall.domain.service.cart.validation.steps;

import com.tmall.domain.entity.product.Product;
import com.tmall.domain.exception.cart.CartValidationException;
import com.tmall.domain.model.ValidationResult;
import com.tmall.domain.model.cart.CartValidateModel;
import com.tmall.domain.util.ValidationStep;

public class CartItemAttributeValidation extends ValidationStep<CartValidateModel> {
    @Override
    public ValidationResult validate(CartValidateModel model) {
        Product product = model.getProduct();
        if (!product.getSeller().getId().equals(model.getItemSellerId())) {
            return ValidationResult.invalid("Product seller Id mismatched!", CartValidationException.class);
        }
        if (!product.getCategory().getId().equals(model.getItemCategoryId())) {
            return ValidationResult.invalid("Product category Id mismatched!", CartValidationException.class);
        }
        if (product.getStock() < model.getItemCount()) {
            return ValidationResult.invalid("Insufficient amount of product stock", CartValidationException.class);
        }
        if (!product.getPrice().equals(model.getPrice())) {
            return ValidationResult.invalid("Invalid item price", CartValidationException.class);
        }
        return checkNext(model);
    }
}