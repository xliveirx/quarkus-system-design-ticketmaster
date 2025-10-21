package br.com.joao.exception;

import br.com.joao.exception.dto.InvalidParamsResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {


    @Override
    public Response toResponse(ConstraintViolationException exception) {

        var status = 400;
        var invalidParams = exception.getConstraintViolations()
                .stream()
                .map(cv -> {
                    var fullPath = cv.getPropertyPath().toString();
                    var paramName = fullPath.substring(fullPath.lastIndexOf('.') + 1);

                    return new InvalidParamsResponse(paramName, cv.getMessage());
                }).toList();

        return Response
                .status(status)
                .entity(new ExceptionResponse(
                        "about:blank",
                        "Constraint violation",
                        "There is a constraint violation on the request.",
                        status,
                        invalidParams
                        ))
                .build();
    }
}
