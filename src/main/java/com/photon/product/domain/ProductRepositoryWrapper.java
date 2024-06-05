package com.photon.product.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductRepositoryWrapper {

    private final ProductRepository productRepository;

    public Product findById(UUID id) {
        return this.productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product Not found"));
    }
}
