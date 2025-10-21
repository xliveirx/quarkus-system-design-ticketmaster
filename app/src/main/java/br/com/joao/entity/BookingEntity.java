package br.com.joao.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "tb_bookings")
public class BookingEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public UserEntity user;

    @Enumerated(EnumType.STRING)
    public BookingStatus status;

    public Instant bookedAt;

    @CreationTimestamp
    public Instant createdAt;

    @CreationTimestamp
    public Instant updatedAt;

}
