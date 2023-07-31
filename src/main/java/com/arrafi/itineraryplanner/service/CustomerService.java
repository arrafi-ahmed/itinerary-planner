package com.arrafi.itineraryplanner.service;

import com.arrafi.itineraryplanner.exception.UserNotFoundException;
import com.arrafi.itineraryplanner.model.Customer;
import com.arrafi.itineraryplanner.model.Role;
import com.arrafi.itineraryplanner.model.Trip;
import com.arrafi.itineraryplanner.repo.CustomerRepo;
import com.arrafi.itineraryplanner.repo.TripRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    TripRepo tripRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Customer save(Customer customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setRole(Role.USER);
        return customerRepo.save(customer);
    }

    public Trip addTrip(Trip trip, Long customerId) {
        Customer customer = customerRepo.findById(customerId).orElseThrow(() -> new UserNotFoundException(customerId));
        trip.setCustomer(customer);
        return tripRepo.save(trip);
    }

    public Customer getById(Long id) {
        return customerRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public Customer update(Customer updatedCustomer, Long id) {
        Customer customer = customerRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        if (updatedCustomer.getPassword() != null)
            customer.setPassword(updatedCustomer.getPassword());
        if (updatedCustomer.getName() != null)
            customer.setName(updatedCustomer.getName());
        if (updatedCustomer.getCurrency() != null)
            customer.setCurrency(updatedCustomer.getCurrency());
        return customerRepo.save(customer);
    }

    public void delete(Long id) {
        if (!customerRepo.existsById(id))
            throw new UserNotFoundException(id);
        customerRepo.deleteById(id);
    }

    public List<Customer> getAll() {
        return customerRepo.findAll();
    }
}
