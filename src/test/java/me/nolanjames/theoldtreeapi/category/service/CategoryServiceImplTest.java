package me.nolanjames.theoldtreeapi.category.service;

import jakarta.persistence.EntityExistsException;
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
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    void givenValidExistingCategoryRequest_whenCreateCategory_thenReturnError() {
        given(categoryRepository.categoryExistsByName(request.name()))
                .willReturn(true);

        assertThatThrownBy(() -> categoryService.createCategory(request)).isInstanceOf(EntityExistsException.class);
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
    void givenInvalidPublicId_whenFindByPublicId_thenReturnError() {
        given(categoryRepository.findByPublicId("333"))
                .willThrow(EntityExistsException.class);

        assertThatThrownBy(() -> categoryService.findByPublicId("333")).isInstanceOf(EntityExistsException.class);
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
    void givenInvalidCategoryName_whenFindByName_thenReturnError() {
        given(categoryRepository.findByName("333"))
                .willThrow(EntityExistsException.class);

        assertThatThrownBy(() -> categoryService.findByName("333")).isInstanceOf(EntityExistsException.class);
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
        given(categoryRepository.categoryExistsByName(request.name()))
                .willReturn(true);

        boolean b = categoryService.categoryExistsByName(request.name());

        assertTrue(b);
    }

    @Test
    void givenValidCategoryName_whenGetCategoryEntityByName_thenCategory() {
        given(categoryRepository.findByName(request.name()))
                .willReturn(Optional.of(category));

        Category categoryEntityByName = categoryService.getCategoryEntityByName(request.name());

        assertNotNull(categoryEntityByName);
        assertEquals(categoryEntityByName.getName(), response.name());
    }

    @Test
    void existingCategories() {

        given(categoryRepository.categoryExistsByName(request.name()))
                .willReturn(true);
        given(categoryRepository.findByName(request.name()))
                .willReturn(Optional.of(category));

        Set<Category> categories = categoryService.existingCategories(List.of(request));

        assertNotNull(categories);
    }
}