package com.tmall.domain.entity.promotion;

import com.tmall.domain.entity.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "TOTAL_PROMOTION_DISCOUNT_TIER")
@Getter
@Setter
public class TotalPromotionDiscountTier extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 4746699219719013162L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MIN_PRICE")
    private Double minPrice;

    @Column(name = "MAX_PRICE")
    private Double maxPrice;

    @Column(name = "DISCOUNT_AMOUNT")
    private BigDecimal discountAmount;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private TotalPricePromotion totalPricePromotion;
}