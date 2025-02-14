package com.example.carrental.service;

import com.example.carrental.model.Car;
import com.example.carrental.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    // Листване на всички автомобили по локация
    public List<Car> getCarsByLocation(String location) {
        return carRepository.findByLocation(location);
    }

    // Листване на конкретен автомобил по ID
    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }

    // Добавяне на нов автомобил
    public Car addCar(Car car) {
        return carRepository.save(car);
    }

    // Актуализация на съществуващ автомобил
    public Car updateCar(Long id, Car updatedCar) {
        return carRepository.findById(id).map(car -> {
            car.setBrand(updatedCar.getBrand());
            car.setModel(updatedCar.getModel());
            car.setLocation(updatedCar.getLocation());
            car.setPricePerDay(updatedCar.getPricePerDay());
            car.setAvailable(updatedCar.getAvailable());
            return carRepository.save(car);
        }).orElseThrow(() -> new RuntimeException("Автомобилът не е намерен!"));
    }

    // Изтриване на автомобил (мека изтриване - просто го правим "неактивен")
    public void deleteCar(Long id) {
        carRepository.findById(id).ifPresent(car -> {
            car.setAvailable(false);
            carRepository.save(car);
        });
    }
}
