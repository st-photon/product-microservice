package com.photon.product.services;


import com.photon.product.domain.Product;
import com.photon.product.domain.ProductRepository;
import com.photon.product.domain.ProductRepositoryWrapper;
import com.photon.product.dto.ProductDTO;
import com.photon.product.helpers.ProductMapperHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductQueryServiceImpl implements ProductQueryService {

    private final ProductRepository productRepository;

    private final ProductMapperHelper productMapperHelper;

    private final ProductRepositoryWrapper productRepositoryWrapper;

    @Override
    public List<ProductDTO> fetchAll() {
        List<Product> products = this.productRepository.findAll();
        return this.productMapperHelper.map(products);
    }

    @Override
    public ProductDTO fetchById(UUID productId) {
        final Product product = this.productRepositoryWrapper.findById(productId);
        return this.productMapperHelper.map(product);
    }
}
