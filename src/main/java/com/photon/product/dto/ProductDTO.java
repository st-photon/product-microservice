package com.photon.product.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

@Builder
@Getter
public class ProductDTO implements Serializable {

    private UUID id;

    private String name;

    private String price;

    private String imageURL;

    private String description;

    private String brand;

}
