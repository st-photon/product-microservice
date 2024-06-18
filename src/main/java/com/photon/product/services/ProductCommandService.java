package com.photon.product.services;


import com.photon.infrastructure.Response;
import com.photon.product.request.CreateProductRequest;
import com.photon.product.request.EditProductRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ProductCommandService {

    String PRODUCT_IMAGE_ROOT_DIRECTORY = "products";

    Response saveProduct(CreateProductRequest createProductRequest, MultipartFile productImage);

    Response updateProduct(UUID productId, EditProductRequest editProductRequest);
}
