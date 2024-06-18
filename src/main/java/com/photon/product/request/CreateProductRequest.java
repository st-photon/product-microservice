package com.photon.product.request;

import lombok.Data;

@Data
public class CreateProductRequest extends AbstractProductRequest {

    private String description;

    private String brand;
}
