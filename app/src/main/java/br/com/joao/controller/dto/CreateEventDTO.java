package br.com.joao.controller.dto;

import br.com.joao.entity.EventEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateEventDTO(
        @NotBlank String name,
        @NotBlank String description,
        @Valid @NotNull EventSettingDto settings
) {
    public EventEntity toEntity() {
        return new EventEntity(name, description);
    }
}
