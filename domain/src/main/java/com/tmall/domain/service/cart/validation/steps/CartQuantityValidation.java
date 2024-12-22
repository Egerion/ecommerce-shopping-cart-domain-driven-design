package com.tmall.domain.service.cart.validation.steps;

import com.tmall.domain.entity.cart.Cart;
import com.tmall.domain.entity.product.Product;
import com.tmall.domain.exception.cart.CartValidationException;
import com.tmall.domain.model.ValidationResult;
import com.tmall.domain.model.cart.CartValidateModel;
import com.tmall.domain.service.configuration.ConfigurationService;
import com.tmall.domain.util.ValidationStep;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CartQuantityValidation extends ValidationStep<CartValidateModel> {
    private final ConfigurationService configurationService;

    @Override
    public ValidationResult validate(CartValidateModel model) {
        Cart cart = model.getCart();
        Product product = model.getProduct();
        int addDigitalItemTotal = product.isDigitalCategory() ? model.getItemCount() : 0;
        int addUniqueItemTotal = cart.getItemByProduct(product).isPresent() ? model.getItemCount() : 0;

        int maxUniqueItemCount = configurationService.getAsInteger("cart.max.unique.item.count");
        if (cart.getUniqueItemCount() + addUniqueItemTotal > maxUniqueItemCount) {
            return ValidationResult.invalid("UniqueItemCount exceeded!", CartValidationException.class);
        }

        int maxItemCount = configurationService.getAsInteger("cart.max.item.count");
        if (cart.getItemCount() + model.getItemCount() > maxItemCount) {
            return ValidationResult.invalid("ItemCount exceeded!", CartValidationException.class);
        }

        int maxDigitalItemCount = configurationService.getAsInteger("cart.max.digital.item.count");
        if (cart.getDigitalItemCount() + addDigitalItemTotal > maxDigitalItemCount) {
            return ValidationResult.invalid("DigitalItemCount exceeded!", CartValidationException.class);
        }
        return checkNext(model);
    }
}