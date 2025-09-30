package br.com.joao.service;

import br.com.joao.controller.dto.*;
import br.com.joao.entity.EventEntity;
import br.com.joao.entity.SeatEntity;
import br.com.joao.entity.SeatStatus;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class EventService {

    public ApiListDto<EventDto> findAll(int page, int pageSize) {
        var query = EventEntity.findAll()
                .page(Page.of(page, pageSize));

        List<EventDto> events = query
                .stream()
                .map(EventEntity.class::cast)
                .map(EventDto::fromEntity)
                .toList();

        var totalPages = query.pageCount();
        var totalItems = query.count();

        return new ApiListDto<>(
                events,
                new PaginationDto(page, pageSize, totalPages, totalItems));

    }

    @Transactional
    public EventDto createEvent(CreateEventDTO dto){

        var eventEntity = dto.toEntity();

        eventEntity.persist();

        SeatEntity seatEntity;
        for(int c = 0; c < dto.settings().numberOfSeats(); c++){
            var seatName = "S" + c;
            seatEntity = new SeatEntity(eventEntity, seatName, SeatStatus.AVAILABLE);
            seatEntity.persist();
        }

        return EventDto.fromEntity(eventEntity);

    }

    public Optional<EventDto> findById(Long id){

        return EventEntity.findByIdOptional(id)
                .map(EventEntity.class::cast)
                .map(EventDto::fromEntity);

    }

    public ApiListDto<SeatDto> findAllSeats(Long eventId, Integer page, Integer pageSize) {

        // TO-DO - tratar exception via ExceptionHandler
        var event = EventEntity.findByIdOptional(eventId).orElseThrow();

        var query = SeatEntity.find("eventId", event)
                .page(Page.of(page, pageSize));

        var totalPages = query.pageCount();
        var totalItems = query.count();
        var events = query.stream()
                .map(SeatEntity.class::cast)
                .map(SeatDto::fromEntity)
                .toList();

        return new ApiListDto<>(
                events,
                new PaginationDto(page, pageSize, totalPages, totalItems));
    }
}
