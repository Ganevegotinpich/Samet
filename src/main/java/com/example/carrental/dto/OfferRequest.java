package com.example.carrental.dto;

public class OfferRequest {
    private Long customerId;
    private Long carId;
    private int rentalDays;

    // Празен конструктор (нужен за JSON парсване)
    public OfferRequest() {
    }

    // Пълен конструктор
    public OfferRequest(Long customerId, Long carId, int rentalDays) {
        this.customerId = customerId;
        this.carId = carId;
        this.rentalDays = rentalDays;
    }

    // Getter-и
    public Long getCustomerId() {
        return customerId;
    }

    public Long getCarId() {
        return carId;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    // Setter-и
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    // toString метод за дебъгване
    @Override
    public String toString() {
        return "OfferRequest{" +
                "customerId=" + customerId +
                ", carId=" + carId +
                ", rentalDays=" + rentalDays +
                '}';
    }
}
