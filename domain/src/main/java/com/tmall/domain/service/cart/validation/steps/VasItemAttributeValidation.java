package com.tmall.domain.service.cart.validation.steps;

import com.tmall.domain.entity.product.Product;
import com.tmall.domain.exception.cart.CartValidationException;
import com.tmall.domain.model.ValidationResult;
import com.tmall.domain.model.cart.CartValidateModel;
import com.tmall.domain.util.ValidationStep;

public class VasItemAttributeValidation extends ValidationStep<CartValidateModel> {
    @Override
    public ValidationResult validate(CartValidateModel model) {
        Product product = model.getProduct();
        if (product.isDigitalCategory()) {
            return ValidationResult.invalid("Digital Item cannot be added as vasItem!", CartValidationException.class);
        }
        return checkNext(model);
    }
}