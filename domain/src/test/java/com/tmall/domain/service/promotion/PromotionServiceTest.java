package com.tmall.domain.service.promotion;

import com.tmall.domain.entity.promotion.Promotable;
import com.tmall.domain.entity.promotion.Promotion;
import com.tmall.domain.service.promotion.PromotionService;
import com.tmall.domain.service.promotion.strategy.CategoryPromotionStrategy;
import com.tmall.domain.service.promotion.strategy.SameSellerPromotionStrategy;
import com.tmall.domain.service.promotion.strategy.TotalPricePromotionStrategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PromotionServiceTest {

    @InjectMocks
    private PromotionService promotionService;

    @Mock
    private CategoryPromotionStrategy categoryPromotionStrategy;

    @Mock
    private SameSellerPromotionStrategy sameSellerPromotionStrategy;

    @Mock
    private TotalPricePromotionStrategy totalPricePromotionStrategy;

    @Mock
    private Promotable promotable;

    @Mock
    private Promotion promotion1;

    @Mock
    private Promotion promotion2;

    @Mock
    private Promotion promotion3;

    @Test
    public void shouldSelectBestPromotion() {
        when(promotion1.getDiscountAmount(promotable)).thenReturn(BigDecimal.valueOf(50));
        when(promotion2.getDiscountAmount(promotable)).thenReturn(BigDecimal.valueOf(100));
        when(promotion3.getDiscountAmount(promotable)).thenReturn(BigDecimal.valueOf(110));

        when(categoryPromotionStrategy.decidePromotion(promotable)).thenReturn(promotion1);
        when(sameSellerPromotionStrategy.decidePromotion(promotable)).thenReturn(promotion2);
        when(totalPricePromotionStrategy.decidePromotion(promotable)).thenReturn(promotion3);

        promotionService = new PromotionService(Arrays.asList(
                categoryPromotionStrategy, sameSellerPromotionStrategy, totalPricePromotionStrategy
        ));

        Promotion bestPromotion = promotionService.selectBestPromotion(promotable).get();

        assertEquals(promotion3, bestPromotion);
    }

    @Test
    public void shouldReturnNullWhenNoPromotionsAvailable() {
        when(categoryPromotionStrategy.decidePromotion(promotable)).thenReturn(null);
        when(sameSellerPromotionStrategy.decidePromotion(promotable)).thenReturn(null);
        when(totalPricePromotionStrategy.decidePromotion(promotable)).thenReturn(null);

        promotionService = new PromotionService(Arrays.asList(
                categoryPromotionStrategy, sameSellerPromotionStrategy, totalPricePromotionStrategy
        ));

        Optional<Promotion> bestPromotion = promotionService.selectBestPromotion(promotable);

        assertEquals(Optional.empty(), bestPromotion);
    }
}