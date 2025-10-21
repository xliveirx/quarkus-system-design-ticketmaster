package br.com.joao.exception;

import jakarta.ws.rs.core.Response;

public class TicketmasterException extends RuntimeException{

    protected ProblemDetails toProblemDetails(){
        return new ProblemDetails(
                new ExceptionResponse("InternalServerError", "Ticketmaster Exception", ""),
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
    }
}
