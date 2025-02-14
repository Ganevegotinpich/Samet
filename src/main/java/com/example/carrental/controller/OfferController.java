package com.example.carrental.controller;

import com.example.carrental.dto.OfferRequest;
import com.example.carrental.model.Offer;
import com.example.carrental.service.OfferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    // 📌 Създаване на нова оферта (Приема JSON с OfferRequest)
    @PostMapping
    public ResponseEntity<?> createOffer(@RequestBody OfferRequest request) {
        try {
            Offer offer = offerService.createOffer(request.getCustomerId(), request.getCarId(), request.getRentalDays());
            return ResponseEntity.status(HttpStatus.CREATED).body(offer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Грешка при създаване на оферта: " + e.getMessage());
        }
    }

    // 📌 Взимане на оферта по ID
    @GetMapping("/{offerId}")
    public ResponseEntity<Offer> getOfferById(@PathVariable Long offerId) {
        return offerService.getOfferById(offerId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // 📌 Взимане на всички оферти за клиент
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Offer>> getOffersByCustomer(@PathVariable Long customerId) {
        List<Offer> offers = offerService.getOffersByCustomer(customerId);
        return ResponseEntity.ok(offers);
    }

    // 📌 Приемане на оферта (активиране)
    @PutMapping("/{offerId}/accept")
    public ResponseEntity<Offer> acceptOffer(@PathVariable Long offerId) {
        Optional<Offer> acceptedOffer = offerService.acceptOffer(offerId);
        return acceptedOffer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 📌 Меко изтриване на оферта (деактивиране)
    @DeleteMapping("/{offerId}")
    public ResponseEntity<Void> deleteOffer(@PathVariable Long offerId) {
        offerService.deleteOffer(offerId);
        return ResponseEntity.noContent().build();
    }
}
