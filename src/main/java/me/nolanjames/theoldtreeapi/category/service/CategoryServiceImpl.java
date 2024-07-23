package me.nolanjames.theoldtreeapi.category.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nolanjames.theoldtreeapi.category.Category;
import me.nolanjames.theoldtreeapi.category.CategoryRepository;
import me.nolanjames.theoldtreeapi.category.dto.CategoryRequest;
import me.nolanjames.theoldtreeapi.category.dto.CategoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        log.info("Request to create category with object: {}", categoryRequest);
        if (categoryExistsByName(categoryRequest.name())) {
            log.info("Category already exists with name: {}", categoryRequest.name());
            throw new EntityExistsException("Category already exists");
        } else {
            Category category = categoryMapper.toCategory(categoryRequest);
            category.setPublicId(UUID.randomUUID().toString());
            categoryRepository.save(category);

            log.info("Category saved with object: {}", categoryRequest);
            return categoryMapper.toCategoryResponse(category);
        }
    }

    @Override
    public CategoryResponse findByPublicId(String publicId) {
        return categoryRepository.findByPublicId(publicId)
                .map(categoryMapper::toCategoryResponse)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with Public ID: " + publicId));
    }

    @Override
    public CategoryResponse findByName(String name) {
        return categoryRepository.findByName(name)
                .map(categoryMapper::toCategoryResponse)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with name: " + name));
    }


    @Override
    public List<CategoryResponse> listCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toCategoryResponse)
                .toList();
    }

    @Override
    public boolean categoryExistsByName(String name) {
        return categoryRepository.categoryExistsByName(name);
    }

    @Override
    public Category getCategoryEntityByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with name: " + name));
    }


    @Override
    public Set<Category> existingCategories(List<CategoryRequest> categoryRequests) {
        return categoryRequests.stream()
                .filter(categoryRequest -> categoryExistsByName(categoryRequest.name()))
                .map(categoryRequest -> getCategoryEntityByName(categoryRequest.name()))
                .collect(Collectors.toSet());

    }


}
