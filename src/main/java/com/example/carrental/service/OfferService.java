package com.example.carrental.service;

import com.example.carrental.model.Car;
import com.example.carrental.model.Customer;
import com.example.carrental.model.Offer;
import com.example.carrental.repository.CarRepository;
import com.example.carrental.repository.CustomerRepository;
import com.example.carrental.repository.OfferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OfferService {
    private static final Logger logger = LoggerFactory.getLogger(OfferService.class);

    private final OfferRepository offerRepository;
    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;

    public OfferService(OfferRepository offerRepository, CustomerRepository customerRepository, CarRepository carRepository) {
        this.offerRepository = offerRepository;
        this.customerRepository = customerRepository;
        this.carRepository = carRepository;
    }

    public Offer createOffer(Long customerId, Long carId, int rentalDays) {
        try {
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new RuntimeException("Клиентът не е намерен!"));

            Car car = carRepository.findById(carId)
                    .orElseThrow(() -> new RuntimeException("Автомобилът не е намерен!"));

            if (!car.isAvailable()) {
                throw new RuntimeException("Този автомобил не е наличен!");
            }

            double totalPrice = car.getPricePerDay() * rentalDays;

            if (customer.getHasAccidents()) {
                totalPrice += 200;
            }

            Offer offer = new Offer(customer, car, LocalDate.now(), rentalDays, totalPrice, false);
            return offerRepository.save(offer);
        } catch (Exception e) {
            logger.error("Грешка при създаване на оферта", e);
            throw new RuntimeException("Неуспешно създаване на оферта: " + e.getMessage());
        }
    }

    public Optional<Offer> getOfferById(Long offerId) {
        return offerRepository.findById(offerId);
    }

    public List<Offer> getOffersByCustomer(Long customerId) {
        return offerRepository.findByCustomerId(customerId);
    }

    public void deleteOffer(Long offerId) {
        offerRepository.findById(offerId).ifPresent(offer -> {
            offer.setAccepted(false);
            offerRepository.save(offer);
        });
    }

    public Optional<Offer> acceptOffer(Long offerId) {
        return offerRepository.findById(offerId).map(offer -> {
            if (!offer.isAccepted()) {
                offer.setAccepted(true);
                return offerRepository.save(offer);
            }
            return offer;
        });
    }
}
