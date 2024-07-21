package me.nolanjames.theoldtreeapi.product.service;

import me.nolanjames.theoldtreeapi.product.dto.ProductRequest;
import me.nolanjames.theoldtreeapi.product.dto.ProductResponse;

public interface ProductService {

    ProductResponse createProduct(ProductRequest productRequest);
}
