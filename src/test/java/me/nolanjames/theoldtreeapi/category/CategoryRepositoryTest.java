package me.nolanjames.theoldtreeapi.category;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @DisplayName("Create new category")
    @Test
    void givenCategoryObject_whenSave_thenReturnSavedCategory() {
        Category category = Category.builder()
                .name("Toys")
                .description("Toys Description")
                .build();
        Category savedCategory = categoryRepository.save(category);

        assertThat(savedCategory).isNotNull();
        assertThat(savedCategory.getId()).isGreaterThan(0);
    }

    @DisplayName("List all categories")
    @Test
    void givenCategoryList_whenFindAll_thenReturnCategoryList() {

        Category category = Category.builder()
                .name("Toys_Test")
                .description("Toys Description")
                .build();
        Category category2 = Category.builder()
                .name("Furniture_Test")
                .description("Furniture Description")
                .build();

        categoryRepository.save(category);
        categoryRepository.save(category2);

        List<Category> categoryList = categoryRepository.findAll();

        assertThat(categoryList).isNotNull();
        assertThat(categoryList.size()).isEqualTo(2);


    }


    @Test
    void givenExistingCategoryPublicId_whenFindByPublicId_thenReturnCategory() {
        String randomUUID = UUID.randomUUID().toString();
        Category category = Category.builder()
                .name("Toys")
                .description("Toys Description")
                .publicId(randomUUID)
                .build();

        categoryRepository.save(category);

        Category categoryByPublicId = categoryRepository.findByPublicId(category.getPublicId()).get();

        assertThat(categoryByPublicId).isNotNull();
        assertThat(categoryByPublicId.getPublicId()).isEqualTo(category.getPublicId());

    }

    @Test
    void givenExistingCategoryName_whenFindByName_thenReturnCategory() {
        String randomUUID = UUID.randomUUID().toString();
        Category category = Category.builder()
                .name("Toys")
                .description("Toys Description")
                .publicId(randomUUID)
                .build();

        categoryRepository.save(category);

        Category categoryByName = categoryRepository.findByName(category.getName()).get();

        assertThat(categoryByName).isNotNull();
        assertThat(categoryByName.getName()).isEqualTo(category.getName());
    }

    @Test
    void givenExistingCategoryName_whenCategoryExistsByName_thenReturnTrue() {
        String randomUUID = UUID.randomUUID().toString();
        Category category = Category.builder()
                .name("Toys")
                .description("Toys Description")
                .publicId(randomUUID)
                .build();

        categoryRepository.save(category);

        boolean exists = categoryRepository.categoryExistsByName(category.getName());

        assertThat(exists).isTrue();

    }
}