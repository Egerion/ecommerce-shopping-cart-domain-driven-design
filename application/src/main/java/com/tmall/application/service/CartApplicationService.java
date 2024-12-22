package com.tmall.application.service;

import com.tmall.application.dto.request.AddItemRequestDto;
import com.tmall.application.dto.request.AddVasItemRequestDto;
import com.tmall.application.dto.response.CartResponseDto;
import com.tmall.application.dto.response.CartResultResponseDto;
import com.tmall.application.mapper.CartMapper;
import com.tmall.domain.entity.cart.Cart;
import com.tmall.domain.entity.cart.item.Item;
import com.tmall.domain.entity.product.Product;
import com.tmall.domain.model.cart.CartValidateModel;
import com.tmall.domain.service.cart.CartService;
import com.tmall.domain.service.cart.validation.CartValidationService;
import com.tmall.domain.service.configuration.ConfigurationService;
import com.tmall.domain.service.item.ItemService;
import com.tmall.domain.service.product.ProductService;
import com.tmall.domain.service.promotion.PromotionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartApplicationService {

    private final CartService cartService;
    private final ItemService itemService;
    private final CartValidationService cartValidationService;
    private final PromotionService promotionService;
    private final CartMapper cartMapper;
    private final ConfigurationService configurationService;
    private final ProductService productService;

    public CartResponseDto displayCart(Long cartId) {
        return CartResponseDto.builder()
                .result(true)
                .message(configurationService.getAsString("cart.display.success"))
                .cart(cartMapper.toCartDto(cartService.findById(cartId)))
                .build();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CartResultResponseDto addItemToCart(Long cartId, AddItemRequestDto request) {
        Cart cart = cartService.findById(cartId);
        Product product = productService.findById(request.getItemId());

        CartValidateModel validateModel = CartValidateModel.builder()
                .cart(cart)
                .product(product)
                .itemCategoryId(request.getCategoryId())
                .itemSellerId(request.getSellerId())
                .itemCount(request.getQuantity())
                .price(request.getPrice())
                .build();

        cartValidationService.validateForAddItem(validateModel);

        return performCartAction(cartId, () -> {
            cart.getItemByProduct(product)
                    .ifPresentOrElse(
                            existingItem -> itemService.updateItemQuantity(existingItem, request.getQuantity()),
                            () -> {
                                Item item = itemService.createItem(cart, product, request.getQuantity());
                                cart.addItem(item);
                            }
                    );
            applyBestPromotionAndSaveCart(cart);
        }, "cart.add.item.success", "cart.add.item.failed");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CartResultResponseDto addVasItemToCart(Long cartId, AddVasItemRequestDto request) {
        Cart cart = cartService.findById(cartId);
        Item item = itemService.findById(request.getItemId());
        Product product = productService.findById(request.getVasItemId());

        CartValidateModel validateModel = CartValidateModel.builder()
                .cart(cart)
                .item(item)
                .product(product)
                .price(request.getPrice())
                .vasItemSellerId(request.getVasSellerId())
                .vasCategoryId(request.getVasCategoryId())
                .vasItemCount(request.getQuantity())
                .build();

        cartValidationService.validateForAddVasItem(validateModel);

        return performCartAction(cartId, () -> {
            item.getVasItemByProduct(product).ifPresentOrElse(
                    existingVasItem -> itemService.updateVasItemQuantity(existingVasItem, request.getQuantity()),
                    () -> itemService.createVasItem(item, product, request.getQuantity())
            );
            applyBestPromotionAndSaveCart(cart);
        }, "cart.add.vasItem.success", "cart.add.vasItem.failed");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CartResultResponseDto removeItemFromCart(Long cartId, Long itemId) {
        Cart cart = cartService.findById(cartId);
        Item item = itemService.findById(itemId);

        CartValidateModel validateModel = CartValidateModel.builder()
                .cart(cart)
                .item(item)
                .build();

        cartValidationService.validateForRemoveItem(validateModel);

        return performCartAction(cartId, () -> {
            cart.getItems().remove(item);
            itemService.deleteItem(item);
            applyBestPromotionAndSaveCart(cart);
        }, "cart.remove.item.success", "cart.remove.item.failed");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CartResultResponseDto resetCart(Long cartId) {
        return performCartAction(cartId, () -> cartService.resetCart(cartId),
                "cart.reset.success", "cart.reset.failed");
    }

    private void applyBestPromotionAndSaveCart(Cart cart) {
        promotionService.selectBestPromotion(cart).ifPresent(cart::setPromotion);
        cartService.saveCart(cart);
    }

    private CartResultResponseDto performCartAction(Long cartId, Runnable action, String successMessageKey,
                                                    String errorMessageKey) {
        try {
            action.run();
            return CartResultResponseDto.builder()
                    .result(true)
                    .message(configurationService.getAsString(successMessageKey))
                    .build();
        } catch (Exception e) {
            log.error("Error occurred while processing action on cart, cartId:{}", cartId, e);
            return CartResultResponseDto.builder()
                    .message(configurationService.getAsString(errorMessageKey))
                    .build();
        }
    }
}