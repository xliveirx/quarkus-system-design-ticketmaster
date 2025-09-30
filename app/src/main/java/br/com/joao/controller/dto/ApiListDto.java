package br.com.joao.controller.dto;

import java.util.List;

public record ApiListDto<T>(List<T> data, PaginationDto pagination) {
}
