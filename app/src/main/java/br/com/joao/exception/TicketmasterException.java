package br.com.joao.exception;

import jakarta.ws.rs.core.Response;

public class TicketmasterException extends RuntimeException{

    protected ProblemDetails toProblemDetails(){
        return new ProblemDetails(
                new ExceptionResponse(
                        "about:blank",
                        "Ticketmaster Exception",
                        "There is an internal server error.",
                        Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                        null
                ),
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()
        );
    }
}
