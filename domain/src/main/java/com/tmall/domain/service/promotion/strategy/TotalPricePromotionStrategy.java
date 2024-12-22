package com.tmall.domain.service.promotion.strategy;

import com.tmall.domain.entity.promotion.Promotable;
import com.tmall.domain.entity.promotion.Promotion;
import com.tmall.domain.repository.promotion.TotalPricePromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.Comparator;

@Component
@RequiredArgsConstructor
public class TotalPricePromotionStrategy implements PromotionStrategy {

    private final TotalPricePromotionRepository repository;

    @Override
    public Promotion decidePromotion(Promotable promotable) {
        return repository.getTotalPricePromotion().stream()
                .map(promotion -> new AbstractMap.SimpleEntry<>(promotion, promotion.getDiscountAmount(promotable)))
                .filter(entry -> entry.getValue().doubleValue() > 0)
                .max(Comparator.comparingDouble(entry -> entry.getValue().doubleValue()))
                .map(AbstractMap.SimpleEntry::getKey)
                .orElse(null);
    }
}