package com.photon.product.request;

import lombok.Data;

@Data
public abstract class AbstractProductRequest {

    private String name;

    private String price;

    @Override
    public String toString() {
        return "AbstractProductRequest{" +
                "productName='" + name + '\'' +
                ", productPrice='" + price + '\'' +
                '}';
    }
}
