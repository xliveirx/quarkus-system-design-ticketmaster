package br.com.joao.controller.dto;

import br.com.joao.entity.EventEntity;

public record CreateEventDTO(String name, String description) {
    public EventEntity toEntity() {
        return new EventEntity(name, description);
    }
}
