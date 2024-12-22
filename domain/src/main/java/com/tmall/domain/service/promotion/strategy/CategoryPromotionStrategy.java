package com.tmall.domain.service.promotion.strategy;

import com.tmall.domain.entity.promotion.Promotable;
import com.tmall.domain.entity.promotion.Promotion;
import com.tmall.domain.repository.promotion.CategoryPromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.Comparator;

@Component
@RequiredArgsConstructor
public class CategoryPromotionStrategy implements PromotionStrategy {

    private final CategoryPromotionRepository repository;

    @Override
    public Promotion decidePromotion(Promotable promotable) {
        return repository.getCategoryPromotions().stream()
                .map(promotion -> new AbstractMap.SimpleEntry<>(promotion, promotion.getDiscountAmount(promotable)))
                .filter(entry -> entry.getValue().doubleValue() > 0)
                .max(Comparator.comparingDouble(entry -> entry.getValue().doubleValue()))
                .map(AbstractMap.SimpleEntry::getKey)
                .orElse(null);
    }
}