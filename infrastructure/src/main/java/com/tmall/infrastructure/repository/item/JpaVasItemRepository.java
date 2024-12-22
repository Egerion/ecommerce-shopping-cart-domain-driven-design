package com.tmall.infrastructure.repository.item;

import com.tmall.domain.entity.cart.item.VasItem;
import com.tmall.domain.repository.item.VasItemRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaVasItemRepository extends VasItemRepository, JpaRepository<VasItem, Long> {
}