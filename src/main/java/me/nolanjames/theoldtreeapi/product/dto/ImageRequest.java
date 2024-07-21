package me.nolanjames.theoldtreeapi.product.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ImageRequest(
        @NotNull
        @NotEmpty
        String link
) {
}
