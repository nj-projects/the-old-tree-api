package me.nolanjames.theoldtreeapi.product.service;

import me.nolanjames.theoldtreeapi.category.Category;
import me.nolanjames.theoldtreeapi.category.dto.CategoryRequest;
import me.nolanjames.theoldtreeapi.category.dto.CategoryResponse;
import me.nolanjames.theoldtreeapi.category.service.CategoryService;
import me.nolanjames.theoldtreeapi.product.Product;
import me.nolanjames.theoldtreeapi.product.ProductRepository;
import me.nolanjames.theoldtreeapi.product.dto.ImageRequest;
import me.nolanjames.theoldtreeapi.product.dto.ImageResponse;
import me.nolanjames.theoldtreeapi.product.dto.ProductRequest;
import me.nolanjames.theoldtreeapi.product.dto.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    private ProductRequest request;
    private ProductResponse response;
    private Product product;
    String publicId;
    private List<CategoryRequest> categoryRequests;
    private List<CategoryResponse> categoryResponses;
    private Set<Category> categorySet;

    @Mock
    ProductRepository productRepository;

    ProductServiceImpl productService;

    @Mock
    ProductMapper productMapper;

    @Mock
    CategoryService categoryService;

    @BeforeEach
    void setup() {
        Category category = Category.builder()
                .name("Toys")
                .description("Description")
                .publicId("123456")
                .build();
        productService = new ProductServiceImpl(productRepository, productMapper, categoryService);
        String publicId = "123456";
        categoryRequests = List.of(
                new CategoryRequest("Toys", "Description"));
        categoryResponses = List.of(
                new CategoryResponse("Toys", "Description", "123456"));
        categorySet.add(category);
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
    void createProduct() {
        when(productMapper.toProduct(request)).thenReturn(product);
        when(productService.createProduct(request)).thenReturn(response);
        when(productRepository.findByName(request.name())).thenReturn(Optional.empty());
        when(categoryService.existingCategories(request.categories())).thenReturn(categorySet);

        ProductResponse productResponse = productService.createProduct(request);

        assertEquals(productResponse, response);
    }

    @Test
    void listProducts() {
    }
}