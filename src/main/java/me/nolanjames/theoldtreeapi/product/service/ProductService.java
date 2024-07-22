package me.nolanjames.theoldtreeapi.product.service;

import me.nolanjames.theoldtreeapi.product.dto.ProductRequest;
import me.nolanjames.theoldtreeapi.product.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest productRequest);

    List<ProductResponse> listProducts();
}
