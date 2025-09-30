package br.com.joao.controller.dto;

public record PaginationDto(int page, int pageSize, int toatalPages, long totalItems) {
}
