package com.photon.product.services;


import com.photon.core.BaseUnitTest;
import com.photon.infrastructure.Response;
import com.photon.product.domain.Product;
import com.photon.product.domain.ProductRepository;
import com.photon.product.helpers.ProductMapperHelper;
import com.photon.product.request.CreateProductRequest;
import com.photon.storage.FileStorageFactoryService;
import com.photon.storage.FileStorageResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;


public class ProductCommandServiceImplTest extends BaseUnitTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapperHelper productMapperHelper;

    @InjectMocks
    private ProductCommandServiceImpl productCommandService;

    @Mock
    private FileStorageFactoryService fileStorageFactoryService;

    @ParameterizedTest
    @DisplayName("should save product")
    @ArgumentsSource(SaveProductArgumentsProvider.class)
    public void shouldSaveProduct(Product product) {

        //arrange
        Mockito.when(productMapperHelper.toEntity(any())).thenReturn(product);
        Mockito.when(fileStorageFactoryService.saveFile(anyString(), any())).thenReturn(mock(FileStorageResult.class));
        Mockito.when(productRepository.saveAndFlush(any())).thenReturn(product);

        //act
        Response response = productCommandService.saveProduct(mock(CreateProductRequest.class), mock(MultipartFile.class));

        //assert
        Mockito.verify(productRepository, Mockito.times(1)).saveAndFlush(any(Product.class));
        Assertions.assertEquals("6cc63550-f135-43fd-8b2b-cdf301417ff1", response.getId().toString());
    }
}
