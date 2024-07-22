package me.nolanjames.theoldtreeapi.product.service;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nolanjames.theoldtreeapi.category.service.CategoryService;
import me.nolanjames.theoldtreeapi.product.Product;
import me.nolanjames.theoldtreeapi.product.ProductRepository;
import me.nolanjames.theoldtreeapi.product.dto.ProductRequest;
import me.nolanjames.theoldtreeapi.product.dto.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryService categoryService;

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        log.info("Request to create product with object: {}", productRequest);
        if (productRepository.findByName(productRequest.name()).isEmpty()) {
            Product product = productMapper.toProduct(productRequest);
            product.setCategories(categoryService.existingCategories(productRequest.categories()));
            product.setPublicId(UUID.randomUUID().toString());
            product.setOnSale(false);
            productRepository.save(product);

            log.info("Product saved with object: {}", productRequest);
            return productMapper.toResponse(product);
        } else {
            throw new EntityExistsException("Product with name " + productRequest.name() + " already exists");
        }
    }

    @Override
    public List<ProductResponse> listProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toResponse)
                .toList();
    }
}
