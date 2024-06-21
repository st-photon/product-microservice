package com.photon.product.services;


import com.photon.infrastructure.Response;
import com.photon.product.domain.Product;
import com.photon.product.domain.ProductRepository;
import com.photon.product.domain.ProductRepositoryWrapper;
import com.photon.product.helpers.ProductMapperHelper;
import com.photon.product.request.CreateProductRequest;
import com.photon.product.request.EditProductRequest;
import com.photon.storage.FileStorageFactoryService;
import com.photon.storage.FileStorageResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductCommandServiceImpl implements ProductCommandService {

    private final ProductMapperHelper productMapperHelper;

    private final ProductRepository productRepository;

    private final FileStorageFactoryService fileStorageFactoryService;

    private final ProductRepositoryWrapper productRepositoryWrapper;

    @Override
    @Transactional
    public Response saveProduct(CreateProductRequest createProductRequest, MultipartFile productImage) {
        try {
            Product product = productMapperHelper.toEntity(createProductRequest);
            final FileStorageResult fileStorageResult = fileStorageFactoryService.saveFile(PRODUCT_IMAGE_ROOT_DIRECTORY, productImage);
            product.setImageName(fileStorageResult.getNewFileName());
            product.setImagePath(fileStorageResult.getAbsolutePath());
            product.setImageType(fileStorageResult.getMimeType());
            Product dbProduct = productRepository.saveAndFlush(product);
            log.debug("New product Id={}", dbProduct.getId());
            return Response.of(dbProduct.getId());
        } catch(Exception e) {
            log.error("Error while saving product {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Response updateProduct(UUID productId, EditProductRequest editProductRequest) {
        return null;
    }

    @Override
    @Transactional
    public void reduceStock(UUID productId, int qty) {
        log.debug("Started reduce stock productId={}, qty={}", productId, qty);
        final Product product = this.productRepositoryWrapper.findById(productId);
        log.debug("Before reducing stock for product={}, totalStock={}", productId, product.getStock());
        product.setStock((product.getStock() - qty));
        productRepository.saveAndFlush(product);
        log.debug("After stock reduced for product={}, totalStock={}", productId, product.getStock());
    }

    @Override
    @Transactional
    public void rollbackStock(UUID productId, int qty) {
        log.debug("Started rollback stock productId={}, qty={}", productId, qty);
        final Product product = this.productRepositoryWrapper.findById(productId);
        log.debug("Before rollback stock for product={}, totalStock={}", productId, product.getStock());
        product.setStock((product.getStock() + qty));
        productRepository.saveAndFlush(product);
        log.debug("After stock roll backed for product={}, totalStock={}", productId, product.getStock());
    }
}
