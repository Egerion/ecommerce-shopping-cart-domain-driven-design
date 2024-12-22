package com.tmall.domain.entity.cart.item;

import com.tmall.domain.entity.category.Category;
import com.tmall.domain.entity.common.BaseEntity;
import com.tmall.domain.entity.product.Product;
import com.tmall.domain.entity.seller.Seller;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.math.BigDecimal;

@Entity
@Table(name = "VAS_ITEM")
@Getter
@Setter
public class VasItem extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -800189017711487350L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne
    private Product product;

    @Column(name = "QUANTITY")
    private int quantity;

    public Seller getSeller() {
        return product.getSeller();
    }

    public Category getCategory() {
        return product.getCategory();
    }

    public BigDecimal getPrice() {
        return product.getPrice();
    }
}