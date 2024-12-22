package com.tmall.domain.entity.promotion;


import com.tmall.domain.entity.seller.Seller;
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
@Table(name = "SAME_SELLER_PROMOTION")
@Getter
@Setter
public class SameSellerPromotion extends Promotion {

    @Serial
    private static final long serialVersionUID = 8961916906699644684L;

    @ManyToOne
    private Seller seller;

    @Column(name = "DISCOUNT_RATE")
    private Double discountRate;

    @Override
    public BigDecimal getDiscountAmount(Promotable promotable) {
        return promotable.getItems().stream()
                .allMatch(item -> Objects.equals(item.getSeller().getId(), seller.getId()))
                ? promotable.getTotalPrice().multiply(BigDecimal.valueOf(discountRate))
                : BigDecimal.ZERO;
    }
}