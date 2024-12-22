package com.tmall.domain.repository.promotion;

import com.tmall.domain.entity.promotion.SameSellerPromotion;

import java.util.List;

public interface SameSellerPromotionRepository {
    List<SameSellerPromotion> getSameSellerPromotions();
}