package br.com.joao.service;

import br.com.joao.controller.BookingController;
import br.com.joao.controller.dto.CreateBookingDto;
import br.com.joao.entity.*;
import br.com.joao.exception.ResourceNotFoundException;
import br.com.joao.exception.SeatAlreadyBookedException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@ApplicationScoped
public class BookingService {

    private final BookingController bookingController;

    @Inject
    public BookingService(BookingController bookingController) {
        this.bookingController = bookingController;
    }

    @Transactional
    public Long createBooking(CreateBookingDto dto) {

        validateInputs(dto);

        Set<SeatEntity> availableSeats = getAvailableSeats(dto);

        var bookingEntity = buildBookingEntity(dto);
        bookingEntity.persist();

        createTickets(availableSeats, bookingEntity);

        updateSeats(availableSeats);

        return bookingEntity.id;
    }

    private static Set<SeatEntity> getAvailableSeats(CreateBookingDto dto) {

        Set<SeatEntity> availableSeats = new HashSet<>();

        dto.seats().forEach(seat -> {

            SeatEntity s = SeatEntity.findByIdOptional(seat.seatId())
                    .map(SeatEntity.class::cast)
                    .orElseThrow(() -> new ResourceNotFoundException("Seat not found", "Seat with id not found"));

            if(s.status == SeatStatus.BOOKED){
                throw new SeatAlreadyBookedException(s.name);
            }

            availableSeats.add(s);
        });
        return availableSeats;
    }

    private static void validateInputs(CreateBookingDto dto) {
        UserEntity.findByIdOptional(dto.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found",
                        "User with id not found."));

        EventEntity.findByIdOptional(dto.eventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found",
                        "Event with id not found."));
    }

    private static BookingEntity buildBookingEntity(CreateBookingDto dto) {
        BookingEntity booking = new BookingEntity();

        booking.bookedAt = Instant.now();
        booking.status = BookingStatus.PENDING;
        booking.user = UserEntity.findById(dto.userId());

        return booking;
    }

    private static void createTickets(Set<SeatEntity> seats, BookingEntity bookingEntity) {
        TicketEntity ticketEntity = new TicketEntity();
        seats.forEach(seat -> {
            ticketEntity.seat = seat;
            ticketEntity.externalId = UUID.randomUUID();
            ticketEntity.booking = bookingEntity;
            ticketEntity.persist();
        });
    }


    private static void updateSeats(Set<SeatEntity> seats) {
        seats.stream()
                .peek(seat -> seat.status = SeatStatus.BOOKED)
                .forEach(seat -> seat.persist());
    }
}
