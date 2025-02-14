package com.example.carrental.service;

import com.example.carrental.model.Car;
import com.example.carrental.model.Customer;
import com.example.carrental.model.Offer;
import com.example.carrental.repository.CarRepository;
import com.example.carrental.repository.CustomerRepository;
import com.example.carrental.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;

    @Autowired
    public OfferService(OfferRepository offerRepository, CustomerRepository customerRepository, CarRepository carRepository) {
        this.offerRepository = offerRepository;
        this.customerRepository = customerRepository;
        this.carRepository = carRepository;
    }

    // ✅ Създаване на нова оферта
    public Offer createOffer(Long customerId, Long carId, int rentalDays) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Клиентът не е намерен!"));

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Автомобилът не е намерен!"));

        if (!car.getAvailable()) {
            throw new RuntimeException("Този автомобил не е наличен!");
        }

        double totalPrice = calculateTotalPrice(car, customer, rentalDays);
        LocalDate today = LocalDate.now();

        Offer offer = new Offer(customer, car, today, rentalDays, totalPrice, false);
        return offerRepository.save(offer);
    }

    // ✅ Метод за калкулиране на крайната цена
    private double calculateTotalPrice(Car car, Customer customer, int rentalDays) {
        double basePrice = car.getPricePerDay() * rentalDays;

        // Уикенд такса (+10%)
        LocalDate today = LocalDate.now();
        for (int i = 0; i < rentalDays; i++) {
            LocalDate currentDay = today.plusDays(i);
            if (currentDay.getDayOfWeek() == DayOfWeek.SATURDAY || currentDay.getDayOfWeek() == DayOfWeek.SUNDAY) {
                basePrice += car.getPricePerDay() * 0.1; // +10% за уикенд
            }
        }

        // Ако клиентът има ПТП, добавяме +200 лв.
        if (customer.getHasAccidents()) {
            basePrice += 200;
        }

        return basePrice;
    }
    // ✅ Метод за запазване на оферта
    public Offer saveOffer(Offer offer) {
        return offerRepository.save(offer);
    }

    // ✅ Метод за търсене на оферта по ID
    public Optional<Offer> getOfferById(Long offerId) {
        return offerRepository.findById(offerId);
    }

    // ✅ Приемане на оферта (прави колата "неналична")
    public Offer acceptOffer(Long offerId) {
        return offerRepository.findById(offerId).map(offer -> {
            offer.setAccepted(true);
            Car car = offer.getCar();
            car.setAvailable(false);
            carRepository.save(car);
            return offerRepository.save(offer);
        }).orElseThrow(() -> new RuntimeException("Офертата не е намерена!"));
    }

    // ✅ Листване на всички оферти за клиент
    public List<Offer> getOffersByCustomer(Long customerId) {
        return offerRepository.findByCustomerId(customerId);
    }

    // ✅ Меко изтриване на оферта (само маркира като изтрита)
    public void deleteOffer(Long offerId) {
        offerRepository.findById(offerId).ifPresent(offer -> {
            offer.setAccepted(false); // Маркираме като неактивна
            offerRepository.save(offer);
        });
    }
}
