package com.tmall.domain.repository.promotion;

import com.tmall.domain.entity.promotion.CategoryPromotion;

import java.util.List;

public interface CategoryPromotionRepository {
    List<CategoryPromotion> getCategoryPromotions();
}