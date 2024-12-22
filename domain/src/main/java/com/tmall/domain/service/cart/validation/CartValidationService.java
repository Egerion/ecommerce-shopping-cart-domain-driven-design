package com.tmall.domain.service.cart.validation;

import com.tmall.domain.model.cart.CartValidateModel;
import com.tmall.domain.service.cart.validation.steps.CartItemAttributeValidation;
import com.tmall.domain.service.cart.validation.steps.CartMaxPriceValidation;
import com.tmall.domain.service.cart.validation.steps.CartQuantityValidation;
import com.tmall.domain.service.cart.validation.steps.RemoveItemValidation;
import com.tmall.domain.service.cart.validation.steps.VasItemAttributeValidation;
import com.tmall.domain.service.cart.validation.steps.VasItemQuantityValidation;
import com.tmall.domain.service.configuration.ConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartValidationService {

    private final ConfigurationService configurationService;

    public void validateForAddItem(CartValidateModel model) {
        new CartItemAttributeValidation()
                .addConstraint(new CartMaxPriceValidation(configurationService))
                .addConstraint(new CartQuantityValidation(configurationService))
                .validate(model);
    }

    public void validateForAddVasItem(CartValidateModel model) {
        new VasItemAttributeValidation()
                .addConstraint(new CartMaxPriceValidation(configurationService))
                .addConstraint(new VasItemQuantityValidation(configurationService))
                .validate(model);
    }

    public void validateForRemoveItem(CartValidateModel model) {
        new RemoveItemValidation()
                .validate(model);
    }
}