package br.com.joao.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
public class SeatEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    public EventEntity eventId;

    public String name;

    @Enumerated(EnumType.STRING)
    public SeatStatus status;

    public SeatEntity(EventEntity eventId, String name, SeatStatus status) {
        this.eventId = eventId;
        this.name = name;
        this.status = status;
    }

    public SeatEntity(){}
}
