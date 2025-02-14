package com.example.carrental.service;

import com.example.carrental.model.Customer;
import com.example.carrental.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Листване на всички клиенти
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Намиране на клиент по ID
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    // Добавяне на нов клиент
    public Customer addCustomer(Customer customer) {
        System.out.println("Запазваме клиент в базата: " + customer.getEmail());
        return customerRepository.save(customer);
    }

    // Изтриване на клиент (по-късно може да направим "меко" изтриване)
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
