package me.nolanjames.theoldtreeapi.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nolanjames.theoldtreeapi.ProductRepository;
import me.nolanjames.theoldtreeapi.product.Product;
import me.nolanjames.theoldtreeapi.product.ProductMapper;
import me.nolanjames.theoldtreeapi.product.dto.ProductRequest;
import me.nolanjames.theoldtreeapi.product.dto.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        log.info("Request to create product with object: {}", productRequest);
        Product product = productMapper.toProduct(productRequest);
        product.setPublicId(UUID.randomUUID().toString());
        product.setOnSale(false);
        productRepository.save(product);

        log.info("Product saved with object: {}", productRequest);
        return productMapper.toResponse(product);
    }
}
