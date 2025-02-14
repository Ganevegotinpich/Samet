package com.example.carrental.controller;

import com.example.carrental.model.Car;
import com.example.carrental.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;
    private static final List<String> ALLOWED_CITIES = Arrays.asList("Sofia", "Plovdiv", "Varna", "Burgas");

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping
    public ResponseEntity<?> addCar(@RequestBody Car car) {
        if (!ALLOWED_CITIES.contains(car.getLocation())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Автомобилът може да бъде добавен само в Sofia, Plovdiv, Varna или Burgas!");
        }
        Car savedCar = carService.addCar(car);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCar);
    }

    @GetMapping("/location/{city}")
    public ResponseEntity<?> getCarsByLocation(@PathVariable String city) {
        if (!ALLOWED_CITIES.contains(city)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Невалиден град. Изберете Sofia, Plovdiv, Varna или Burgas.");
        }
        List<Car> cars = carService.getCarsByLocation(city);
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{carId}")
    public ResponseEntity<?> getCarById(@PathVariable Long carId) {
        Optional<Car> car = carService.getCarById(carId);
        return car.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{carId}")
    public ResponseEntity<?> updateCar(@PathVariable Long carId, @RequestBody Car updatedCar) {
        if (!ALLOWED_CITIES.contains(updatedCar.getLocation())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Автомобилът може да бъде разположен само в Sofia, Plovdiv, Varna или Burgas!");
        }
        Optional<Car> car = carService.updateCar(carId, updatedCar);
        return car.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{carId}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long carId) {
        carService.deleteCar(carId);
        return ResponseEntity.noContent().build();
    }
}

