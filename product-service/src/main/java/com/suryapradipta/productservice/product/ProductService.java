package com.suryapradipta.productservice.product;

import com.suryapradipta.productservice.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductResponse create(ProductRequest productRequest) {
        Product product = productMapper.toEntity(productRequest);
        Product savedProduct = productRepository.save(product);
        log.info("Product created: {}", savedProduct);
        return productMapper.toProductResponse(savedProduct);
    }

    public List<ProductResponse> findAll() {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> responses = products.stream()
                .map(productMapper::toProductResponse)
                .toList();
        log.info("Retrieved products: {}", responses);
        return responses;
    }

    @Transactional
    public void delete(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
        log.info("Product deleted: {}", id);
    }
}
