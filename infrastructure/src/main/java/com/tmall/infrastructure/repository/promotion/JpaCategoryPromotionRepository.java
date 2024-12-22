package com.tmall.infrastructure.repository.promotion;

import com.tmall.domain.entity.promotion.CategoryPromotion;
import com.tmall.domain.repository.promotion.CategoryPromotionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaCategoryPromotionRepository extends CategoryPromotionRepository, JpaRepository<CategoryPromotion, Long> {

    @Query("select promotion from CategoryPromotion promotion where promotion.deleted = false")
    List<CategoryPromotion> getCategoryPromotions();
}