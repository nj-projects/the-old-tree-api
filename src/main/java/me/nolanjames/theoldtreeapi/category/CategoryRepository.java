package me.nolanjames.theoldtreeapi.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("""
            SELECT category
            FROM Category category
            WHERE category.publicId = :publicId
            """)
    Optional<Category> findByPublicId(String publicId);

    @Query("""
            SELECT category
            FROM Category category
            WHERE category.name = :name
            """)
    Optional<Category> findByName(String name);

    @Query("""
            SELECT (COUNT(*) > 0) as exists
            FROM Category category
            WHERE category.name = :name
            """)
    boolean categoryExistsByName(String name);
}
