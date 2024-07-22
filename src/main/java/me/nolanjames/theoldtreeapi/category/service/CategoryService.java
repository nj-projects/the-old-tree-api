package me.nolanjames.theoldtreeapi.category.service;

import me.nolanjames.theoldtreeapi.category.Category;
import me.nolanjames.theoldtreeapi.category.dto.CategoryRequest;
import me.nolanjames.theoldtreeapi.category.dto.CategoryResponse;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest categoryRequest);

    CategoryResponse findByPublicId(String publicId);

    CategoryResponse findByName(String name);

    List<CategoryResponse> listCategories();

    boolean categoryExistsByName(String name);

    Category getCategoryEntityByName(String name);

    Set<Category> existingCategories(List<CategoryRequest> categoryRequests);
}
