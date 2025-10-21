package br.com.joao.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_seats")
public class SeatEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    public EventEntity event;

    public String name;

    @Enumerated(EnumType.STRING)
    public SeatStatus status;

    public SeatEntity(EventEntity event, String name, SeatStatus status) {
        this.event = event;
        this.name = name;
        this.status = status;
    }

    public SeatEntity(){}
}
