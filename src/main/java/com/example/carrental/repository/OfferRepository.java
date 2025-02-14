package com.example.carrental.repository;

import com.example.carrental.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findByCustomerId(Long customerId);
}
