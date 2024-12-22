package com.tmall.domain.entity.promotion;

import com.tmall.domain.entity.category.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "CATEGORY_PROMOTION")
@Getter
@Setter
public class CategoryPromotion extends Promotion {

    @Serial
    private static final long serialVersionUID = -1935524103287812111L;

    @ManyToOne
    private Category category;

    @Column(name = "DISCOUNT_RATE")
    private Double discountRate;

    @Override
    public BigDecimal getDiscountAmount(Promotable promotable) {
        return promotable.getItems().stream()
                .filter(item -> Objects.equals(item.getCategory().getId(), category.getId()))
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(discountRate)).multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}