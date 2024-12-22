package com.tmall.domain.repository.product;

import com.tmall.domain.entity.product.Product;

import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findByIdAndDeletedFalse(Long id);
}
