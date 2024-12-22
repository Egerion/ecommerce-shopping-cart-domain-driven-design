package com.tmall.domain.repository.promotion;

import com.tmall.domain.entity.promotion.TotalPricePromotion;

import java.util.List;

public interface TotalPricePromotionRepository {
    List<TotalPricePromotion> getTotalPricePromotion();
}