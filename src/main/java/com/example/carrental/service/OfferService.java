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
    private final CarService carService;

    public OfferService(OfferRepository offerRepository, CustomerRepository customerRepository, CarRepository carRepository, CarService carService) {
        this.offerRepository = offerRepository;
        this.customerRepository = customerRepository;
        this.carRepository = carRepository;
        this.carService = carService;
    }

    @Transactional
    public Offer createOffer(Long customerId, Long carId, int rentalDays) {
        try {
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new RuntimeException("Customer not found!"));

            Car car = carRepository.findById(carId)
                    .orElseThrow(() -> new RuntimeException("Car not found!"));

            if (!car.isAvailable()) {
                throw new RuntimeException("This car is not available!");
            }

            double totalPrice = carService.calculatePrice(car, rentalDays, customer.getHasAccidents());

            Offer offer = new Offer(customer, car, LocalDate.now(), rentalDays, totalPrice, false);

            Offer savedOffer = offerRepository.save(offer);
            logger.info("Offer created successfully: {}", savedOffer);

            return savedOffer;
        } catch (Exception e) {
            logger.error("Error creating offer", e);
            throw new RuntimeException("Failed to create offer: " + e.getMessage());
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
