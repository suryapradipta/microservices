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

    @Transactional
    public ProductResponse create(ProductRequest productRequest) {
        Product product = productMapper.toEntity(productRequest);
        Product savedProduct = productRepository.save(product);
        log.info("Product created with id: {}", savedProduct.getId());
        return productMapper.toProductResponse(savedProduct);
       }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        List<ProductResponse> products = productRepository.findAll()
                .stream()
                .map(productMapper::toProductResponse)
                .toList();
        log.info("Retrieved {} products", products.size());
        return products;
    }

    @Transactional
    public void delete(String id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
        log.info("Product deleted with id: {}", id);

    }
}
