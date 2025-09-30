package br.com.joao.controller;

import br.com.joao.controller.dto.ApiListDto;
import br.com.joao.controller.dto.CreateEventDTO;
import br.com.joao.controller.dto.EventDto;
import br.com.joao.controller.dto.SeatDto;
import br.com.joao.service.EventService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GET
    public ApiListDto<EventDto> getEvents(@QueryParam("page") @DefaultValue("0") Integer page,
                                          @QueryParam("pageSize") @DefaultValue("10") Integer pageSize){

        return eventService.findAll(page, pageSize);
    }

    @POST
    public Response createEvent(CreateEventDTO dto){

        var body = eventService.createEvent(dto);

        var location = URI.create("/events/" + body.id());

        return Response.created(location).build();
    }

    @GET
    @Path("/{id}")
    public Response getEvent(@PathParam("id") Long id){

        var event = eventService.findById(id);

        return event.isPresent()
                ? Response.ok(event).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/{id}/seats")
    public ApiListDto<SeatDto> getSeats(@PathParam("id") Long id,
                                        @QueryParam("page") @DefaultValue("0") Integer page,
                                        @QueryParam("pageSize") @DefaultValue("10") Integer pageSize){
        return eventService.findAllSeats(id, page, pageSize);
    }
}
