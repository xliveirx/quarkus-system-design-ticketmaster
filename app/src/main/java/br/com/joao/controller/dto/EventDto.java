package br.com.joao.controller.dto;

import br.com.joao.entity.EventEntity;

public record EventDto (Long id, String name, String description) {

    public static EventDto fromEntity(EventEntity event){
        return new EventDto(event.id, event.name, event.description);
    }
}
