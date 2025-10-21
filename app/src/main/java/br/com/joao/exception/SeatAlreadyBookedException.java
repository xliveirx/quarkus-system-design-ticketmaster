package br.com.joao.exception;

public class SeatAlreadyBookedException extends TicketmasterException{

    private final String seatId;

    public SeatAlreadyBookedException(String seatId) {
        this.seatId = seatId;
    }

    @Override
    protected ProblemDetails toProblemDetails() {

        var status = 422;

        return new ProblemDetails(
                new ExceptionResponse(
                        "about:blank",
                        "Seat already booked",
                        "Seat with id " + seatId + " is already booked.",
                        status,
                        null
                        ),
                status
        );
    }
}
