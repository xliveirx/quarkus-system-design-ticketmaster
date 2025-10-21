package br.com.joao.controller;

import br.com.joao.controller.dto.BookingResponseDto;
import br.com.joao.controller.dto.CreateBookingDto;
import br.com.joao.service.BookingService;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @POST
    public Response createBooking(@Valid CreateBookingDto dto){

        var bookingId = bookingService.createBooking(dto);

        return Response.ok(new BookingResponseDto(bookingId)).build();
    }
}
