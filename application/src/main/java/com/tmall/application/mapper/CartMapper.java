package com.tmall.application.mapper;

import com.tmall.application.dto.CartDto;
import com.tmall.application.dto.ItemDto;
import com.tmall.application.dto.VasItemDto;
import com.tmall.domain.entity.cart.Cart;
import com.tmall.domain.entity.cart.item.Item;
import com.tmall.domain.entity.cart.item.VasItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(target = "totalAmount", expression = "java(cart.getTotalPrice())")
    @Mapping(target = "totalDiscount", expression = "java(cart.getTotalDiscount())")
    @Mapping(target = "appliedPromotionId", source = "promotion.id")
    @Mapping(target = "items", source = "items")
    CartDto toCartDto(Cart cart);

    List<ItemDto> toItemDtoList(List<Item> items);

    @Mapping(target = "itemId", source = "id")
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "sellerId", source = "seller.id")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "quantity", source = "quantity")
    ItemDto toItemDto(Item item);

    List<VasItemDto> toVasItemDtoList(List<VasItem> vasItems);

    @Mapping(target = "vasItemId", source = "id")
    @Mapping(target = "vasSellerId", source = "seller.id")
    @Mapping(target = "vasCategoryId", source = "category.id")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "quantity", source = "quantity")
    VasItemDto toVasItemDto(VasItem vasItem);
}