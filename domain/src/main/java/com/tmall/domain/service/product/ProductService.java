package com.tmall.domain.service.product;

import com.tmall.domain.entity.product.Product;
import com.tmall.domain.exception.product.ProductNotFoundException;
import com.tmall.domain.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product findById(Long id) {
        return productRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new ProductNotFoundException("Product not found with Id:" + id));
    }
}