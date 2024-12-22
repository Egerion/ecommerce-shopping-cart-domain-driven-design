package com.tmall.domain.entity.promotion;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TOTAL_PRICE_PROMOTION")
@Getter
@Setter
public class TotalPricePromotion extends Promotion {

    @Serial
    private static final long serialVersionUID = -712734942296292588L;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "promotion_id")
    private List<TotalPromotionDiscountTier> discountTiers = new ArrayList<>();

    @Override
    public BigDecimal getDiscountAmount(Promotable promotable) {
        BigDecimal totalPrice = promotable.getTotalPrice();
        return getDiscountTiers().stream()
                .filter(tier -> totalPrice.compareTo(BigDecimal.valueOf(tier.getMinPrice())) >= 0 && totalPrice.compareTo(BigDecimal.valueOf(tier.getMaxPrice())) < 0)
                .map(TotalPromotionDiscountTier::getDiscountAmount)
                .findFirst()
                .orElse(BigDecimal.ZERO);
    }
}