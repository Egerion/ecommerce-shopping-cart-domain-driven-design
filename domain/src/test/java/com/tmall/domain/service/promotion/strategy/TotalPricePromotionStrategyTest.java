package com.tmall.domain.service.promotion.strategy;

import com.tmall.domain.entity.promotion.Promotable;
import com.tmall.domain.entity.promotion.Promotion;
import com.tmall.domain.entity.promotion.TotalPricePromotion;
import com.tmall.domain.entity.promotion.TotalPromotionDiscountTier;
import com.tmall.domain.repository.promotion.TotalPricePromotionRepository;
import com.tmall.domain.service.promotion.strategy.TotalPricePromotionStrategy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TotalPricePromotionStrategyTest {

    @InjectMocks
    private TotalPricePromotionStrategy strategy;

    @Mock
    private TotalPricePromotionRepository repository;

    @Mock
    private Promotable promotable;

    @Before
    public void setUp() {
        TotalPricePromotion totalPricePromotion = new TotalPricePromotion();
        totalPricePromotion.setId(1232L);

        TotalPromotionDiscountTier tier1 = new TotalPromotionDiscountTier();
        tier1.setMinPrice(500.0);
        tier1.setMaxPrice(5000.0);
        tier1.setDiscountAmount(BigDecimal.valueOf(250));

        TotalPromotionDiscountTier tier2 = new TotalPromotionDiscountTier();
        tier2.setMinPrice(5000.0);
        tier2.setMaxPrice(10000.0);
        tier2.setDiscountAmount(BigDecimal.valueOf(500));

        TotalPromotionDiscountTier tier3 = new TotalPromotionDiscountTier();
        tier3.setMinPrice(10000.0);
        tier3.setMaxPrice(50000.0);
        tier3.setDiscountAmount(BigDecimal.valueOf(1000));

        TotalPromotionDiscountTier tier4 = new TotalPromotionDiscountTier();
        tier4.setMinPrice(50000.0);
        tier4.setMaxPrice(Double.MAX_VALUE);
        tier4.setDiscountAmount(BigDecimal.valueOf(2000));

        totalPricePromotion.setDiscountTiers(Arrays.asList(tier1, tier2, tier3, tier4));

        when(repository.getTotalPricePromotion()).thenReturn(Collections.singletonList(totalPricePromotion));
    }

    @Test
    public void shouldApplyDiscountForPriceInTier1() {
        when(promotable.getTotalPrice()).thenReturn(BigDecimal.valueOf(500));

        Promotion promotion = strategy.decidePromotion(promotable);

        assertNotNull(promotion);
        assertEquals(BigDecimal.valueOf(250), promotion.getDiscountAmount(promotable));
    }

    @Test
    public void shouldApplyDiscountForPriceInTier2() {
        when(promotable.getTotalPrice()).thenReturn(BigDecimal.valueOf(6000));

        Promotion promotion = strategy.decidePromotion(promotable);

        assertEquals(BigDecimal.valueOf(500), promotion.getDiscountAmount(promotable));
    }

    @Test
    public void shouldApplyDiscountForPriceInTier3() {
        when(promotable.getTotalPrice()).thenReturn(BigDecimal.valueOf(20000));

        Promotion promotion = strategy.decidePromotion(promotable);

        assertEquals(BigDecimal.valueOf(1000), promotion.getDiscountAmount(promotable));
    }

    @Test
    public void shouldApplyDiscountForPriceInTier4() {
        when(promotable.getTotalPrice()).thenReturn(BigDecimal.valueOf(50000));

        Promotion promotion = strategy.decidePromotion(promotable);

        assertEquals(BigDecimal.valueOf(2000), promotion.getDiscountAmount(promotable));
    }

    @Test
    public void shouldReturnNullWhenPriceDoesNotMatchAnyTier() {
        when(promotable.getTotalPrice()).thenReturn(BigDecimal.valueOf(499.99));

        Promotion promotion = strategy.decidePromotion(promotable);

        assertNull(promotion);
    }
}