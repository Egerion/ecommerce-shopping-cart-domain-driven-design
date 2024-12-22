package com.tmall.domain.model.cart;

import com.tmall.domain.entity.cart.Cart;
import com.tmall.domain.entity.cart.item.Item;
import com.tmall.domain.entity.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartValidateModel {
    private Cart cart;
    private Product product;
    private Item item;
    private Long itemSellerId;
    private Long itemCategoryId;
    private int itemCount;
    private BigDecimal price;
    private Long vasItemSellerId;
    private Long vasCategoryId;
    private int vasItemCount;

    public Long getVasCategoryId() {
        return vasCategoryId;
    }

    public void setVasCategoryId(Long vasCategoryId) {
        this.vasCategoryId = vasCategoryId;
    }

    public int getVasItemCount() {
        return vasItemCount;
    }

    public void setVasItemCount(int vasItemCount) {
        this.vasItemCount = vasItemCount;
    }

    public Long getVasItemSellerId() {
        return vasItemSellerId;
    }

    public void setVasItemSellerId(Long vasItemSellerId) {
        this.vasItemSellerId = vasItemSellerId;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getItemCategoryId() {
        return itemCategoryId;
    }

    public void setItemCategoryId(Long itemCategoryId) {
        this.itemCategoryId = itemCategoryId;
    }

    public Long getItemSellerId() {
        return itemSellerId;
    }

    public void setItemSellerId(Long itemSellerId) {
        this.itemSellerId = itemSellerId;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}