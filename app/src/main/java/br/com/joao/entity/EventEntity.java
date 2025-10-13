package br.com.joao.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class EventEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @OneToMany(mappedBy = "event")
    public Set<SeatEntity> seats;

    public String name;

    public String description;

    public EventEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public EventEntity(){}
}
