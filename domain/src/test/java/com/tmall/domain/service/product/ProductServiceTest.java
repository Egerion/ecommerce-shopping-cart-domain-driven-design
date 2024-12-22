package com.tmall.domain.service.product;

import com.tmall.domain.entity.product.Product;
import com.tmall.domain.exception.product.ProductNotFoundException;
import com.tmall.domain.repository.product.ProductRepository;
import com.tmall.domain.service.product.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private Product product;

    @Before
    public void setUp() {
        product = new Product();
        product.setId(1L);
        product.setTitle("Sample Product");
    }

    @Test
    public void shouldFindById() {
        Long productId = 1L;
        when(productRepository.findByIdAndDeletedFalse(productId)).thenReturn(Optional.of(product));

        Product foundProduct = productService.findById(productId);

        assertEquals(product, foundProduct);
    }

    @Test
    public void shouldThrowExceptionWhenProductNotFound() {
        Long productId = 1L;
        when(productRepository.findByIdAndDeletedFalse(productId)).thenReturn(Optional.empty());

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            productService.findById(productId);
        });

        assertEquals("Product not found with Id:1", exception.getMessage());
    }
}