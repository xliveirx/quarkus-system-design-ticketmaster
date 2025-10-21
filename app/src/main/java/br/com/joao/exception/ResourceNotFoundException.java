package br.com.joao.exception;

public class ResourceNotFoundException extends TicketmasterException{

    private final String title;
    private final String detail;

    public ResourceNotFoundException(String title, String detail) {
        this.title = title;
        this.detail = detail;
    }

    @Override
    protected ProblemDetails toProblemDetails(){

        var status = 422;

        return new ProblemDetails(
                new ExceptionResponse(
                        "about:blank",
                        title,
                        detail,
                        status,
                        null),
                status);
    }
}
