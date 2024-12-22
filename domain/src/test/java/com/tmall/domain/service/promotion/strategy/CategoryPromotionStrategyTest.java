package com.tmall.domain.service.promotion.strategy;

import com.tmall.domain.entity.cart.item.Item;
import com.tmall.domain.entity.category.Category;
import com.tmall.domain.entity.product.Product;
import com.tmall.domain.entity.promotion.CategoryPromotion;
import com.tmall.domain.entity.promotion.Promotable;
import com.tmall.domain.entity.promotion.Promotion;
import com.tmall.domain.enums.CategoryType;
import com.tmall.domain.repository.promotion.CategoryPromotionRepository;
import com.tmall.domain.service.promotion.strategy.CategoryPromotionStrategy;
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
public class CategoryPromotionStrategyTest {

    @InjectMocks
    private CategoryPromotionStrategy strategy;

    @Mock
    private CategoryPromotionRepository repository;

    @Mock
    private Promotable promotable;

    @Test
    public void shouldDecidePromotion() {
        Category category = new Category();
        category.setId(1L);
        category.setCategoryType(CategoryType.DEFAULT);

        CategoryPromotion categoryPromotion1 = new CategoryPromotion();
        categoryPromotion1.setId(1L);
        categoryPromotion1.setName("promotion1");
        categoryPromotion1.setDiscountRate(0.1);
        categoryPromotion1.setCategory(category);

        CategoryPromotion categoryPromotion2 = new CategoryPromotion();
        categoryPromotion2.setId(2L);
        categoryPromotion2.setName("promotion2");
        categoryPromotion2.setDiscountRate(0.2);
        categoryPromotion2.setCategory(category);

        CategoryPromotion bestPromotion = new CategoryPromotion();
        bestPromotion.setId(3L);
        bestPromotion.setName("promotion3");
        bestPromotion.setDiscountRate(0.5);
        bestPromotion.setCategory(category);

        Product product1 = new Product();
        product1.setId(1L);
        product1.setCategory(category);
        product1.setPrice(BigDecimal.valueOf(1000));

        Product product2 = new Product();
        product2.setId(2L);
        product2.setCategory(category);
        product2.setPrice(BigDecimal.valueOf(1500));

        Item item1 = new Item();
        item1.setId(1L);
        item1.setProduct(product1);
        item1.setQuantity(1);

        Item item2 = new Item();
        item2.setId(2L);
        item2.setProduct(product2);
        item2.setQuantity(1);

        when(promotable.getItems()).thenReturn(Arrays.asList(item1, item2));
        when(repository.getCategoryPromotions()).thenReturn(Arrays.asList(categoryPromotion1, categoryPromotion2, bestPromotion));

        Promotion promotion = strategy.decidePromotion(promotable);

        assertNotNull(promotion);
        assertEquals(bestPromotion.getId(), promotion.getId());
        assertEquals(bestPromotion.getName(), promotion.getName());

        BigDecimal discountAmount = promotion.getDiscountAmount(promotable);

        assertEquals(BigDecimal.valueOf(1250.0), discountAmount);
    }

    @Test
    public void shouldReturnNullWhenItemCategoriesAreNotSame() {
        Category category = new Category();
        category.setId(1L);
        category.setCategoryType(CategoryType.DEFAULT);

        CategoryPromotion categoryPromotion = new CategoryPromotion();
        categoryPromotion.setId(1L);
        categoryPromotion.setName("promotion1");
        categoryPromotion.setDiscountRate(0.1);
        categoryPromotion.setCategory(category);

        Category productCategory = new Category();
        productCategory.setId(2L);
        productCategory.setCategoryType(CategoryType.DIGITAL);

        Product product = new Product();
        product.setId(1L);
        product.setCategory(productCategory);
        product.setPrice(BigDecimal.valueOf(1500));

        Item item = new Item();
        item.setId(1L);
        item.setProduct(product);
        item.setQuantity(1);

        when(promotable.getItems()).thenReturn(Collections.singletonList(item));
        when(repository.getCategoryPromotions()).thenReturn(Collections.singletonList(categoryPromotion));

        Promotion promotion = strategy.decidePromotion(promotable);

        assertNull(promotion);
    }
}