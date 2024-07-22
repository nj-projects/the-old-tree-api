package me.nolanjames.theoldtreeapi.category;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

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
                .name("Toys")
                .description("Toys Description")
                .build();
        Category category2 = Category.builder()
                .name("Furniture")
                .description("Furniture Description")
                .build();

        categoryRepository.save(category);
        categoryRepository.save(category2);

        List<Category> categoryList = categoryRepository.findAll();

        assertThat(categoryList).isNotNull();
        assertThat(categoryList.size()).isEqualTo(2);
    }

    @Test
    void findByPublicId() {
    }

    @Test
    void findByName() {
    }

    @Test
    void categoryExistsByName() {
    }
}