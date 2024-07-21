package me.nolanjames.theoldtreeapi;

import me.nolanjames.theoldtreeapi.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
