package me.nolanjames.theoldtreeapi.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("""
            SELECT product
            FROM Product product
            WHERE LOWER(product.name) = LOWER(:name)
            """)
    Optional<Product> findByName(String name);
}
