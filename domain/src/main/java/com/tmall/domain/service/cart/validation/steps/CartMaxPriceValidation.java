package com.tmall.domain.service.cart.validation.steps;

import com.tmall.domain.exception.cart.CartValidationException;
import com.tmall.domain.model.ValidationResult;
import com.tmall.domain.model.cart.CartValidateModel;
import com.tmall.domain.service.configuration.ConfigurationService;
import com.tmall.domain.util.ValidationStep;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class CartMaxPriceValidation extends ValidationStep<CartValidateModel> {
    private final ConfigurationService configurationService;

    @Override
    public ValidationResult validate(CartValidateModel model) {
        int maxPrice = configurationService.getAsInteger("cart.max.price");
        if (model.getCart().getFinalTotalPrice().add(model.getPrice()).compareTo(BigDecimal.valueOf(maxPrice)) > 0) {
            return ValidationResult.invalid("Cart's max allowed amount is exceeded!", CartValidationException.class);
        }
        return checkNext(model);
    }
}