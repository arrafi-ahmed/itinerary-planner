package com.arrafi.itineraryplanner.controller;

import com.arrafi.itineraryplanner.model.Customer;
import com.arrafi.itineraryplanner.model.Trip;
import com.arrafi.itineraryplanner.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping("/save")
    public ResponseEntity<Customer> save(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.save(customer), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<Customer>> getAll() {
        return ResponseEntity.ok(customerService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getById(id));
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<Customer> update(@RequestBody Customer customer, @PathVariable Long id) {
        return ResponseEntity.ok(customerService.update(customer, id));
    }

    @GetMapping("/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/addTrip")
    public ResponseEntity<Trip> addTrip(@RequestBody Trip trip, @PathVariable("id") Long customerId) {
        return ResponseEntity.ok(customerService.addTrip(trip, customerId));
    }
}
