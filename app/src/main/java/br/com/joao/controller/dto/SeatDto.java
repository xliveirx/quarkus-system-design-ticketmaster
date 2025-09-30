package br.com.joao.controller.dto;

import br.com.joao.entity.SeatEntity;

public record SeatDto(Long seatId, String name, String status) {
    public static SeatDto fromEntity(SeatEntity seatEntity) {
        return new SeatDto(seatEntity.id, seatEntity.name, seatEntity.status.name());
    }
}
