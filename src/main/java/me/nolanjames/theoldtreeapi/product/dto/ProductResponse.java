package me.nolanjames.theoldtreeapi.product.dto;

import me.nolanjames.theoldtreeapi.category.dto.CategoryResponse;

import java.math.BigDecimal;
import java.util.List;

public record ProductResponse(
        String name,
        String description,
        BigDecimal price,
        int stock,
        boolean isActive,
        ImageResponse heroImage,
        List<ImageResponse> images,
        List<CategoryResponse> categories,

        boolean isOnSale,
        BigDecimal salePrice,
        int discountPercentage,
        String publicId
) {
}
