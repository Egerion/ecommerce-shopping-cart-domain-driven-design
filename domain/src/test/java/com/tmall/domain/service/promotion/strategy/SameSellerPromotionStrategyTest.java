package com.tmall.domain.service.promotion.strategy;

import com.tmall.domain.entity.cart.item.Item;
import com.tmall.domain.entity.product.Product;
import com.tmall.domain.entity.promotion.Promotable;
import com.tmall.domain.entity.promotion.Promotion;
import com.tmall.domain.entity.promotion.SameSellerPromotion;
import com.tmall.domain.entity.seller.Seller;
import com.tmall.domain.repository.promotion.SameSellerPromotionRepository;
import com.tmall.domain.service.promotion.strategy.SameSellerPromotionStrategy;
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
public class SameSellerPromotionStrategyTest {

    @InjectMocks
    private SameSellerPromotionStrategy strategy;

    @Mock
    private SameSellerPromotionRepository repository;

    @Mock
    private Promotable promotable;

    @Test
    public void shouldDecidePromotion() {
        Seller seller = new Seller();
        seller.setId(1L);
        seller.setNickname("ege");

        SameSellerPromotion sameSellerPromotion1 = new SameSellerPromotion();
        sameSellerPromotion1.setId(1L);
        sameSellerPromotion1.setName("promotion1");
        sameSellerPromotion1.setSeller(seller);
        sameSellerPromotion1.setDiscountRate(0.1);

        SameSellerPromotion sameSellerPromotion2 = new SameSellerPromotion();
        sameSellerPromotion2.setId(2L);
        sameSellerPromotion2.setName("promotion2");
        sameSellerPromotion2.setSeller(seller);
        sameSellerPromotion2.setDiscountRate(0.2);

        Product product = new Product();
        product.setId(1L);
        product.setSeller(seller);
        product.setPrice(BigDecimal.valueOf(1000));

        Item item = new Item();
        item.setId(1L);
        item.setProduct(product);
        item.setQuantity(1);

        when(promotable.getItems()).thenReturn(Collections.singletonList(item));
        when(promotable.getTotalPrice()).thenReturn(BigDecimal.valueOf(1000));
        when(repository.getSameSellerPromotions()).thenReturn(Arrays.asList(sameSellerPromotion1, sameSellerPromotion2));

        Promotion promotion = strategy.decidePromotion(promotable);

        assertNotNull(promotion);
        assertEquals(sameSellerPromotion2.getId(), promotion.getId());
        assertEquals(sameSellerPromotion2.getName(), promotion.getName());

        BigDecimal discountAmount = promotion.getDiscountAmount(promotable);

        assertEquals(BigDecimal.valueOf(200.0), discountAmount);
    }

    @Test
    public void shouldReturnNullWhenItemSellersAreNotSame() {
        Seller seller = new Seller();
        seller.setId(1L);
        seller.setNickname("ege");

        SameSellerPromotion sameSellerPromotion = new SameSellerPromotion();
        sameSellerPromotion.setId(1L);
        sameSellerPromotion.setName("promotion1");
        sameSellerPromotion.setSeller(seller);
        sameSellerPromotion.setDiscountRate(0.1);

        Seller productSeller = new Seller();
        productSeller.setId(2L);
        productSeller.setNickname("demirbas");

        Product product = new Product();
        product.setId(1L);
        product.setSeller(productSeller);
        product.setPrice(BigDecimal.valueOf(1000));

        Item item = new Item();
        item.setId(1L);
        item.setProduct(product);
        item.setQuantity(1);

        when(promotable.getItems()).thenReturn(Collections.singletonList(item));
        when(repository.getSameSellerPromotions()).thenReturn(Collections.singletonList(sameSellerPromotion));

        Promotion promotion = strategy.decidePromotion(promotable);

        assertNull(promotion);
    }
}