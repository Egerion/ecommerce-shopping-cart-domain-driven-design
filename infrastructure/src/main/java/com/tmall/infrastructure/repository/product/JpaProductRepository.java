package com.tmall.infrastructure.repository.product;

import com.tmall.domain.entity.product.Product;
import com.tmall.domain.repository.product.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, Long> {
}