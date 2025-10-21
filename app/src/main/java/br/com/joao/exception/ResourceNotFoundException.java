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
        return new ProblemDetails(
                new ExceptionResponse("ResourceNotFoundException", title, detail),
                422);
    }
}
