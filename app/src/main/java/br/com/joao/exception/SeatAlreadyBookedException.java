package br.com.joao.exception;

public class SeatAlreadyBookedException extends TicketmasterException{

    private final String seatId;

    public SeatAlreadyBookedException(String seatId) {
        this.seatId = seatId;
    }

    @Override
    protected ProblemDetails toProblemDetails() {
        return new ProblemDetails(
                new ExceptionResponse("SeatAlreadyBookedException", "Seat already booked", "Seat with id " + seatId + " is already booked."),
                422
        );
    }
}
