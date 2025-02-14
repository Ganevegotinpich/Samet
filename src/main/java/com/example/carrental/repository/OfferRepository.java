package com.example.carrental.repository;

import com.example.carrental.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.example.carrental.model.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findByCustomerId(Long customerId);
    Optional<Offer> findById(Long id);
}
