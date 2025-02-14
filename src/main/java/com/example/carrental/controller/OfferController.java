package com.example.carrental.controller;

import com.example.carrental.dto.OfferRequest;
import com.example.carrental.model.Offer;
import com.example.carrental.service.OfferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping
    public ResponseEntity<Offer> createOffer(@RequestBody OfferRequest offerRequest) {
        Offer newOffer = offerService.createOffer(
                offerRequest.getCustomerId(),
                offerRequest.getCarId(),
                offerRequest.getRentalDays()
        );
        return ResponseEntity.created(URI.create("/offers/" + newOffer.getId())).body(newOffer);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Offer>> getOffersByCustomer(@PathVariable Long customerId) {
        List<Offer> offers = offerService.getOffersByCustomer(customerId);
        if (offers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(offers);
    }

    @PutMapping("/{offerId}/accept")
    public ResponseEntity<Offer> acceptOffer(@PathVariable Long offerId) {
        Optional<Offer> offer = offerService.getOfferById(offerId);
        if (offer.isPresent()) {
            Offer updatedOffer = offerService.acceptOffer(offerId);
            return ResponseEntity.ok(updatedOffer);
        }
        return ResponseEntity.notFound().build();
    }
}
