package br.com.joao.exception;

import br.com.joao.exception.dto.InvalidParamsResponse;

import java.util.List;

public record ExceptionResponse(String type, String title, String detail, Integer status, List<InvalidParamsResponse> invalidParams) {
}
