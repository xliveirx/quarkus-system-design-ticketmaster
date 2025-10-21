package br.com.joao.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class TicketmasterExceptionMapper implements ExceptionMapper<TicketmasterException> {

    @Override
    public Response toResponse(TicketmasterException e) {
        return Response
                .status(e.toProblemDetails().status())
                .entity(e.toProblemDetails().response())
                .build();
    }
}
