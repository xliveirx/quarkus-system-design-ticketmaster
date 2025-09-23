package br.com.joao.service;

import br.com.joao.controller.dto.CreateEventDTO;
import br.com.joao.controller.dto.EventDto;
import br.com.joao.entity.EventEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class EventService {

    public List<EventDto> findAll() {
        return EventEntity.findAll().stream()
                .map(EventEntity.class::cast)
                .map(EventDto::fromEntity)
                .toList();
    }

    @Transactional
    public EventDto createEvent(CreateEventDTO dto){

        var entity = dto.toEntity();

        entity.persist();

        return EventDto.fromEntity(entity);

    }

    public Optional<EventDto> findById(Long id){

        return EventEntity.findByIdOptional(id)
                .map(EventEntity.class::cast)
                .map(EventDto::fromEntity);

    }
}
