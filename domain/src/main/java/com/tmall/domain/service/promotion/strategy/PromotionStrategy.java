package com.tmall.domain.service.promotion.strategy;

import com.tmall.domain.entity.promotion.Promotable;
import com.tmall.domain.entity.promotion.Promotion;

public interface PromotionStrategy {
    Promotion decidePromotion(Promotable promotable);
}
