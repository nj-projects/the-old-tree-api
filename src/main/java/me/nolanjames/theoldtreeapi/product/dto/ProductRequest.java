package me.nolanjames.theoldtreeapi.product.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import me.nolanjames.theoldtreeapi.category.dto.CategoryRequest;

import java.math.BigDecimal;
import java.util.List;

public record ProductRequest(
        @NotEmpty
        @NotNull
        String name,

        @NotEmpty
        @NotNull
        String description,

        @PositiveOrZero
        @NotNull
        @DecimalMin(value = "0.00", inclusive = false)
        BigDecimal price,

        @PositiveOrZero
        @NotNull
        int stock,

        @NotNull
        boolean isActive,

        @NotNull
        ImageRequest heroImage,

        List<ImageRequest> images,

        List<CategoryRequest> categories
) {
}
