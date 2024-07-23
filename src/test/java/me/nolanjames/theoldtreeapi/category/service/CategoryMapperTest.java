package me.nolanjames.theoldtreeapi.category.service;

import me.nolanjames.theoldtreeapi.category.Category;
import me.nolanjames.theoldtreeapi.category.dto.CategoryRequest;
import me.nolanjames.theoldtreeapi.category.dto.CategoryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryMapperTest {
    private CategoryRequest request;
    private Category category;
    String publicId;

    private CategoryMapper categoryMapper;

    @BeforeEach
    void setup() {
        categoryMapper = new CategoryMapper();
        publicId = "123456";
        category = Category.builder()
                .name("Service Test Name")
                .description("Service Test Description")
                .publicId(publicId)
                .build();

        request = new CategoryRequest(
                "Service Test Name",
                "Service Test Description"
        );
    }

    @Test
    void toCategory() {
        Category category1 = categoryMapper.toCategory(request);

        assertEquals(request.name(), category1.getName());
    }

    @Test
    void toCategoryResponse() {
        CategoryResponse categoryResponse = categoryMapper.toCategoryResponse(category);

        assertEquals(categoryResponse.name(), category.getName());
    }
}