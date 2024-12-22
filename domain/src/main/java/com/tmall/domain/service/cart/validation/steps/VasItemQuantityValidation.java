package com.tmall.domain.service.cart.validation.steps;

import com.tmall.domain.exception.cart.CartValidationException;
import com.tmall.domain.model.ValidationResult;
import com.tmall.domain.model.cart.CartValidateModel;
import com.tmall.domain.service.configuration.ConfigurationService;
import com.tmall.domain.util.ValidationStep;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VasItemQuantityValidation extends ValidationStep<CartValidateModel> {
    private final ConfigurationService configurationService;

    @Override
    public ValidationResult validate(CartValidateModel model) {
        int maxVasItemCount = configurationService.getAsInteger("cart.vasItem.max.count.perItem");
        if (model.getItem().getTotalVasItemCount() + model.getVasItemCount() > maxVasItemCount) {
            return ValidationResult.invalid("Maximum vasItem limit exceeded for item", CartValidationException.class);
        }

        int maxItemCount = configurationService.getAsInteger("cart.max.item.count");
        if (model.getCart().getItemCount() + model.getVasItemCount() > maxItemCount) {
            return ValidationResult.invalid("ItemCount exceeded!", CartValidationException.class);
        }
        return checkNext(model);
    }
}