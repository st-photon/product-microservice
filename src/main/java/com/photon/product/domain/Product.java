package com.photon.product.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

import java.io.Serializable;
import java.util.UUID;

@Entity(name = "Product")
@Table(name = "product")
@Getter
@Setter
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id", updatable = false, nullable = false)
    @JdbcType(VarcharJdbcType.class)
    private UUID id;

    @Column(name = "name", nullable = false)
    @Basic(optional = false)
    private String productName;

    @Column(name = "price", nullable = false)
    @Basic(optional = false)
    private String productPrice;

    @Column(name = "image_type", nullable = false)
    @Basic(optional = false)
    private String imageType;

    @Column(name = "image_name", nullable = false)
    @Basic(optional = false)
    private String imageName;

    @Column(name = "image_path", nullable = false)
    @Basic(optional = false)
    private String imagePath;

    @Column(name = "description")
    private String description;

    @Column(name = "brand")
    private String brand;

    @Version
    private Long version;
}
