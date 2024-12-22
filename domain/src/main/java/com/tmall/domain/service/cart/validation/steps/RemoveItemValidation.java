package com.tmall.domain.service.cart.validation.steps;

import com.tmall.domain.exception.cart.CartValidationException;
import com.tmall.domain.model.ValidationResult;
import com.tmall.domain.model.cart.CartValidateModel;
import com.tmall.domain.util.ValidationStep;

public class RemoveItemValidation extends ValidationStep<CartValidateModel> {
    @Override
    public ValidationResult validate(CartValidateModel model) {
        if (!model.getCart().hasItem(model.getItem())) {
            return ValidationResult.invalid("Item not found in the cart!", CartValidationException.class);
        }
        return checkNext(model);
    }
}