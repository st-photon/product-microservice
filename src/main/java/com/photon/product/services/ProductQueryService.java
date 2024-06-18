package com.photon.product.services;


import com.photon.product.dto.ProductDTO;

import java.util.List;
import java.util.UUID;

public interface ProductQueryService {

    List<ProductDTO> fetchAll();

    ProductDTO fetchById(UUID productId);
}
