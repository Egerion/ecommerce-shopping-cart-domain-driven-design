package com.tmall.domain.service.promotion;

import com.tmall.domain.entity.promotion.Promotable;
import com.tmall.domain.entity.promotion.Promotion;
import com.tmall.domain.service.promotion.strategy.PromotionStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PromotionService {

    private final List<PromotionStrategy> promotionStrategies;

    public Optional<Promotion> selectBestPromotion(Promotable promotable) {
        return promotionStrategies.stream()
                .map(strategy -> strategy.decidePromotion(promotable))
                .filter(Objects::nonNull)
                .max(Comparator.comparing(promotion -> promotion.getDiscountAmount(promotable)));
    }
}