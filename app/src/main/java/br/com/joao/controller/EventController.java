package br.com.joao.controller;

import br.com.joao.controller.dto.CreateEventDTO;
import br.com.joao.controller.dto.EventDto;
import br.com.joao.entity.EventEntity;
import br.com.joao.service.EventService;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Path("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GET
    public List<EventDto> getEvents(){
        return eventService.findAll();
    }

    @POST
    public Response createEvent(CreateEventDTO dto){

        var body = eventService.createEvent(dto);

        var location = URI.create("/events/" + body.id());

        return Response.created(location).build();
    }

    @GET
    @Path("/{id}")
    public Response getEvent(Long id){

        var event = eventService.findById(id);

        return event.isPresent()
                ? Response.ok(event).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }
}
