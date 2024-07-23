package me.nolanjames.theoldtreeapi.category.service;

import me.nolanjames.theoldtreeapi.category.Category;
import me.nolanjames.theoldtreeapi.category.CategoryRepository;
import me.nolanjames.theoldtreeapi.category.dto.CategoryRequest;
import me.nolanjames.theoldtreeapi.category.dto.CategoryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    private CategoryRequest request;
    private CategoryResponse response;
    private Category category;
    String publicId;


    @Mock
    CategoryRepository categoryRepository;

    CategoryServiceImpl categoryService;

    @Mock
    CategoryMapper categoryMapper;


    @BeforeEach
    void setup() {
        categoryService = new CategoryServiceImpl(categoryRepository, categoryMapper);
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

        response = new CategoryResponse(
                "Service Test Name",
                "Service Test Description",
                publicId
        );
    }

    @Test
    void givenValidCategoryRequest_whenCreateCategory_thenReturnCategoryResponse() {
        when(categoryMapper.toCategory(request)).thenReturn(category);
        when(categoryService.createCategory(request)).thenReturn(response);

        CategoryResponse categoryResponse = categoryService.createCategory(request);

        assertEquals(categoryResponse, response);
    }

    @Test
    void givenValidPublicId_whenFindByPublicId_thenReturnCategoryResponse() {
        when(categoryMapper.toCategory(request)).thenReturn(category);
        when(categoryService.createCategory(request)).thenReturn(response);
        given(categoryRepository.findByPublicId(publicId))
                .willReturn(Optional.of(category));

        CategoryResponse byPublicId = categoryService.findByPublicId(publicId);

        assertNotNull(byPublicId);
        assertEquals(byPublicId, response);
    }

    @Test
    void givenValidCategoryName_whenFindByName_thenReturnCategoryResponse() {
        when(categoryMapper.toCategory(request)).thenReturn(category);
        when(categoryService.createCategory(request)).thenReturn(response);
        given(categoryRepository.findByName(request.name()))
                .willReturn(Optional.of(category));

        CategoryResponse byName = categoryService.findByName(request.name());

        assertNotNull(byName);
        assertEquals(byName.name(), response.name());
    }

    @Test
    void givenValidRequest_whenListCategories_thenCategoryList() {
        when(categoryMapper.toCategory(request)).thenReturn(category);
        when(categoryService.createCategory(request)).thenReturn(response);
        given(categoryRepository.findAll())
                .willReturn(List.of(category));

        List<CategoryResponse> categoryResponseList = categoryService.listCategories();

        assertNotNull(categoryResponseList);
        assertEquals(1, categoryResponseList.size());
        assertEquals(categoryResponseList.get(0).name(), response.name());
    }

    @Test
    void givenValidCategoryName_whenCategoryExistsByName_thenTrue() {
        when(categoryMapper.toCategory(request)).thenReturn(category);
        when(categoryService.createCategory(request)).thenReturn(response);
        given(categoryRepository.categoryExistsByName(request.name()))
                .willReturn(true);

        boolean b = categoryService.categoryExistsByName(request.name());

        assertTrue(b);
    }

    @Test
    void getCategoryEntityByName() {
    }

    @Test
    void existingCategories() {
    }
}