package com.example.carrental.model;

import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    @Column(nullable = false)
    private Boolean hasAccidents;

    public Long getId() {
        return id;
    }

    public Boolean getHasAccidents() {
        return hasAccidents;
    }

    public void setHasAccidents(Boolean hasAccidents) {
        this.hasAccidents = hasAccidents;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
