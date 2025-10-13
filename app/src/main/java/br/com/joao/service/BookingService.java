package br.com.joao.service;

import br.com.joao.controller.BookingController;
import br.com.joao.controller.dto.CreateBookingDto;
import br.com.joao.controller.dto.ReserveSeatDto;
import br.com.joao.entity.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class BookingService {

    private final BookingController bookingController;

    @Inject
    public BookingService(BookingController bookingController) {
        this.bookingController = bookingController;
    }

    @Transactional
    public Long createBooking(CreateBookingDto dto) {

        // 1. recuperar todos assentos (seats)

        Set<Long> seatsId = getSeatsId(dto);
        var seats = findSeats(seatsId);

        // 2. vincular os tickets a reserva (booking)
        var bookingEntity = buildBookingEntity(dto);
        bookingEntity.persist();

        // 3. criar os tickets (ticket)
        createTickets(seats, bookingEntity);

        // 4. atualizar o status dos assentos (seats)
        updateSeats(seats);

        // 5. retornar o id da reserva (booking)
        return bookingEntity.id;
    }

    private static Set<Long> getSeatsId(CreateBookingDto dto) {
        return dto.seats()
                .stream()
                .map(ReserveSeatDto::seatId)
                .collect(Collectors.toSet());
    }

    private static Set<SeatEntity> findSeats(Set<Long> seatsId) {
        return SeatEntity.find("id in (?1)", seatsId)
                .stream()
                .map(SeatEntity.class::cast)
                .peek(s -> {
                    if(s.status.equals(SeatStatus.BOOKED)){
                        throw new RuntimeException("Seat already booked");
                    }
                })
                .collect(Collectors.toSet());
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
