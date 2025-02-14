package com.example.carrental.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class OfferRequest {

    @NotNull(message = "customerId е задължителен")
    private Long customerId;

    @NotNull(message = "carId е задължителен")
    private Long carId;

    @Min(value = 1, message = "rentalDays трябва да е поне 1")
    private int rentalDays;

    public OfferRequest() {}

    public OfferRequest(Long customerId, Long carId, int rentalDays) {
        this.customerId = customerId;
        this.carId = carId;
        this.rentalDays = rentalDays;
    }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public Long getCarId() { return carId; }
    public void setCarId(Long carId) { this.carId = carId; }

    public int getRentalDays() { return rentalDays; }
    public void setRentalDays(int rentalDays) { this.rentalDays = rentalDays; }
}
