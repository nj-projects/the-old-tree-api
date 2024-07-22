package me.nolanjames.theoldtreeapi.category.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CategoryRequest(
        @NotNull
        @NotEmpty
        String name,
        @NotNull
        @NotEmpty
        String description
) {

}
