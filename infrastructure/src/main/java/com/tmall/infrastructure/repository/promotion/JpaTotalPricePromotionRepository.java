package com.tmall.infrastructure.repository.promotion;

import com.tmall.domain.entity.promotion.TotalPricePromotion;
import com.tmall.domain.repository.promotion.TotalPricePromotionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaTotalPricePromotionRepository extends TotalPricePromotionRepository, JpaRepository<TotalPricePromotion, Long> {

    @Query("select promotion from TotalPricePromotion promotion where promotion.deleted = false")
    List<TotalPricePromotion> getTotalPricePromotion();
}