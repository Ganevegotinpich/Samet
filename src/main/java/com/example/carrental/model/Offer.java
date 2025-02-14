package com.example.carrental.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "offers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private int rentalDays;

    @Column(nullable = false)
    private Double totalPrice;

    @Column(nullable = false)
    private Boolean accepted;

    public Long getId() {
        return id;
    }
    public Offer(Customer customer, Car car, LocalDate startDate, int rentalDays, Double totalPrice, Boolean accepted) {
        this.customer = customer;
        this.car = car;
        this.startDate = startDate;
        this.rentalDays = rentalDays;
        this.totalPrice = totalPrice;
        this.accepted = accepted;
    }
    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }


    public Car getCar() {
        return car;
    }



}
