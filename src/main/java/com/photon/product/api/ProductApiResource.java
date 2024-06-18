package com.photon.product.api;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.photon.infrastructure.Response;
import com.photon.product.dto.ProductDTO;
import com.photon.product.request.CreateProductRequest;
import com.photon.product.services.ProductCommandService;
import com.photon.product.services.ProductQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@Tag(name = "ProductApiResource")
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductApiResource {

    private final ProductCommandService productCommandService;

    private final ProductQueryService productQueryService;

    @Operation(summary = "return 200 if product saves successfully", description = "save product and upload file")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response saveProduct(@RequestPart("data") String reqBody, @RequestPart("file") MultipartFile file) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            CreateProductRequest createProductRequest = mapper.readValue(reqBody, CreateProductRequest.class);
            log.debug("saveProduct request body {}", createProductRequest);
            return productCommandService.saveProduct(createProductRequest, file);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Request body is not in the json format");
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDTO getProduct(@PathVariable(name = "id") UUID productId) {
        return productQueryService.fetchById(productId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductDTO> getAllProduct() {
        return productQueryService.fetchAll();
    }

    @GetMapping("/helloWorld")
    public String helloWorld() {
        return "hello world";
    }
}
