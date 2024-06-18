package com.photon.product.helpers;



import com.photon.product.domain.Product;
import com.photon.product.dto.ProductDTO;
import com.photon.product.request.CreateProductRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ProductMapperHelper {

    public Product toEntity(CreateProductRequest createProductRequest) {
        final Product product = new Product();
        product.setProductName(createProductRequest.getName());
        product.setProductPrice(createProductRequest.getPrice());
        product.setDescription(createProductRequest.getDescription());
        product.setBrand(createProductRequest.getBrand());
        return product;
    }

    public List<ProductDTO> map(final List<Product> productList) {
        return productList.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public ProductDTO map(final Product product) {
        return ProductDTO
                .builder()
                .id(product.getId())
                .name(product.getProductName())
                .price(product.getProductPrice())
                .description(product.getDescription())
                .brand(product.getBrand())
                .build();
    }
}
