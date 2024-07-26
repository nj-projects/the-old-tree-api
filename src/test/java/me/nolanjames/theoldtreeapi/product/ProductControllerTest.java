package me.nolanjames.theoldtreeapi.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.nolanjames.theoldtreeapi.category.dto.CategoryRequest;
import me.nolanjames.theoldtreeapi.category.dto.CategoryResponse;
import me.nolanjames.theoldtreeapi.product.dto.ImageRequest;
import me.nolanjames.theoldtreeapi.product.dto.ImageResponse;
import me.nolanjames.theoldtreeapi.product.dto.ProductRequest;
import me.nolanjames.theoldtreeapi.product.dto.ProductResponse;
import me.nolanjames.theoldtreeapi.product.service.ProductService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@AutoConfigureMockMvc()
class ProductControllerTest {
    private ProductRequest request;
    private ProductResponse response;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setup() {
//        String publicId = "123456";
        List<CategoryRequest> categoryRequests = List.of(
                new CategoryRequest("Toys", "Description"));
        List<CategoryResponse> categoryResponses = List.of(
                new CategoryResponse("Toys", "Description", "123456"));
        ImageRequest imageRequest = new ImageRequest("url_link");
        ImageResponse imageResponse = new ImageResponse("url_link");
        List<ImageRequest> images = new ArrayList<>();
        List<ImageResponse> imagesResponse = new ArrayList<>();

        request = new ProductRequest(
                "Product 1",
                "Product 1 desc",
                BigDecimal.valueOf(123.00),
                12,
                true,
                imageRequest,
                images,
                categoryRequests
        );

        response = new ProductResponse(
                "Product 1",
                "Product 1 desc",
                BigDecimal.valueOf(123.00),
                12,
                true,
                imageResponse,
                imagesResponse,
                categoryResponses,
                false,
                null,
                0,
                "123456"
        );
    }

    @Test
    void createProduct() throws Exception {
        Mockito.when(productService.createProduct(ArgumentMatchers.any())).thenReturn(response);
        String json = mapper.writeValueAsString(response);

        mvc.perform(post("/product").with(csrf()).contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", Matchers.equalTo(request.name())))
                .andExpect(jsonPath("$.description", Matchers.equalTo(request.description())));

    }

    @Test
    void listProducts() throws Exception {
        Mockito.when(productService.listProducts()).thenReturn(List.of(response));
        String json = mapper.writeValueAsString(response);

        mvc.perform(get("/product").with(csrf()).contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()").value(1));
    }
}