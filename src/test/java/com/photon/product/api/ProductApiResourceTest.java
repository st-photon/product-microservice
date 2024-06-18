package com.photon.product.api;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photon.core.BaseUnitTest;
import com.photon.infrastructure.Response;
import com.photon.product.dto.ProductDTO;
import com.photon.product.services.ProductCommandService;
import com.photon.product.services.ProductQueryService;
import jakarta.servlet.http.Part;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockPart;
import org.springframework.test.util.AssertionErrors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@WebMvcTest(ProductApiResource.class)
public class ProductApiResourceTest extends BaseUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductCommandService productCommandService;

    @MockBean
    private ProductQueryService productQueryService;


    @Test
    @DisplayName("should return hello world")
    public void shouldReturnHelloWorld() throws Exception {
        //arrange
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products/helloWorld")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE);

        //act
        MockHttpServletResponse mockResponse = this.mockMvc.perform(requestBuilder)
                .andReturn().getResponse();

        //assert
        assertThat(mockResponse.getStatus()).isEqualTo(200);

    }

    @Test
    @DisplayName("Save product and return 200 successfully")
    public void shouldSaveProduct() throws Exception {

        //arrange
        Response response = Response.of(UUID.fromString("6cc63550-f135-43fd-8b2b-cdf301417ff1"));
        Mockito.when(productCommandService.saveProduct(Mockito.any(), Mockito.any()))
                .thenReturn(response);

        //act
        String body = "{\"name\":\"pencil\",\"price\":\"200\"}";
        Part bodyPart = new MockPart("data", body.getBytes());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .multipart(HttpMethod.POST, "/products")
                .file("file", new byte[0])
                .part(bodyPart)
                .contentType(MediaType.MULTIPART_FORM_DATA);
        MockHttpServletResponse mockResponse = this.mockMvc.perform(requestBuilder)
                .andReturn().getResponse();

        //assert
        assertThat(mockResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("should return product list with 200")
    public void shouldReturnProductList() throws Exception{

        //arrange
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products")
                .contentType(MediaType.APPLICATION_JSON_VALUE);
        ProductDTO productDTO = ProductDTO
                .builder()
                .name("product1")
                .build();
        Mockito.when(productQueryService.fetchAll()).thenReturn(List.of(productDTO));

        //act
        MockHttpServletResponse mockHttpServletResponse= this.mockMvc.perform(requestBuilder).andReturn().getResponse();
        String responseBody = mockHttpServletResponse.getContentAsString();
        List<ProductDTO> responseDtoList = new Gson().fromJson(responseBody, new TypeToken<List<ProductDTO>>(){}.getType());

        //assert
        assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(responseDtoList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("should return empty product list with 200")
    public void shouldReturnEmptyProductList() throws Exception{

        //arrange
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products")
                .contentType(MediaType.APPLICATION_JSON_VALUE);
        Mockito.when(productQueryService.fetchAll()).thenReturn(List.of());

        //act
        MockHttpServletResponse mockHttpServletResponse= this.mockMvc.perform(requestBuilder).andReturn().getResponse();
        String responseBody = mockHttpServletResponse.getContentAsString();
        List<ProductDTO> responseDtoList = new Gson().fromJson(responseBody, new TypeToken<List<ProductDTO>>(){}.getType());

        //assert
        assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(responseDtoList.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("should return product by their id and 200 status code")
    public void shouldReturnProductByTheirId() throws Exception {
        //arrange
        UUID uuid = UUID.randomUUID();
        RequestBuilder mockMvcRequestBuilders = MockMvcRequestBuilders
                .get("/products/"+uuid);
        ProductDTO returnProductDTO = ProductDTO.builder()
                .name("product1")
                .id(uuid)
                .build();
        Mockito.when(productQueryService.fetchById(uuid)).thenReturn(returnProductDTO);

        //act
        MockHttpServletResponse response = this.mockMvc.perform(mockMvcRequestBuilders).andReturn().getResponse();
        ProductDTO productDTO = new Gson().fromJson(response.getContentAsString(), ProductDTO.class);

        //assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(productDTO.getName()).isEqualTo("product1");
        assertThat(productDTO.getId()).isEqualTo(uuid);
    }

    @Test
    @DisplayName("")
    public void shouldThrowJsonProcessingException() {
        try {
            //act
            String body = "\"name\":\"pencil\",\"price\":\"200\"}";
            Part bodyPart = new MockPart("data", body.getBytes());
            RequestBuilder requestBuilder = MockMvcRequestBuilders
                    .multipart(HttpMethod.POST, "/products")
                    .file("file", new byte[0])
                    .part(bodyPart)
                    .contentType(MediaType.MULTIPART_FORM_DATA);
            this.mockMvc.perform(requestBuilder)
                    .andReturn().getResponse();
        } catch (RuntimeException e) {
            //assert
            assertThat(e.getCause().getMessage()).isEqualTo("Request body is not in the json format");
        } catch(Exception e) {
            AssertionErrors.assertNotEquals("Error thrown which is not handled", "", e.getMessage());
        }
    }
}
