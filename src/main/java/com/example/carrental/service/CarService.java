package com.example.carrental.service;

import com.example.carrental.model.Car;
import com.example.carrental.repository.CarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepository carRepository;
    private static final List<String> VALID_LOCATIONS = Arrays.asList("Sofia", "Plovdiv", "Varna", "Burgas");

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getCarsByLocation(String city) {
        if (!VALID_LOCATIONS.contains(city)) {
            throw new IllegalArgumentException("Invalid location. We only operate in Sofia, Plovdiv, Varna, and Burgas.");
        }
        return carRepository.findByLocation(city);
    }

    public Optional<Car> getCarById(Long carId) {
        return carRepository.findById(carId);
    }

    public Car addCar(Car car) {
        if (!VALID_LOCATIONS.contains(car.getLocation())) {
            throw new IllegalArgumentException("Car location must be one of: Sofia, Plovdiv, Varna, Burgas.");
        }
        return carRepository.save(car);
    }

    @Transactional
    public Optional<Car> updateCar(Long carId, Car updatedCar) {
        return carRepository.findById(carId)
                .map(car -> {
                    car.setBrand(updatedCar.getBrand());
                    car.setModel(updatedCar.getModel());
                    car.setLocation(updatedCar.getLocation());
                    car.setPricePerDay(updatedCar.getPricePerDay());
                    car.setAvailable(updatedCar.isAvailable());
                    return carRepository.save(car);
                });
    }

    public void deleteCar(Long id) {
        if (!carRepository.existsById(id)) {
            throw new IllegalArgumentException("Car not found.");
        }
        carRepository.deleteById(id);
    }
}

