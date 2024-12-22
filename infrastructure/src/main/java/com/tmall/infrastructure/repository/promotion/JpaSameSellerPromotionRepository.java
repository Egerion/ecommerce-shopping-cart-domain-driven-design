package com.tmall.infrastructure.repository.promotion;

import com.tmall.domain.entity.promotion.SameSellerPromotion;
import com.tmall.domain.repository.promotion.SameSellerPromotionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaSameSellerPromotionRepository extends SameSellerPromotionRepository, JpaRepository<SameSellerPromotion, Long> {

    @Query("select promotion from SameSellerPromotion promotion where promotion.deleted = false")
    List<SameSellerPromotion> getSameSellerPromotions();
}