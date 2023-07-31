package com.arrafi.itineraryplanner.repo;

import com.arrafi.itineraryplanner.model.Customer;
import com.arrafi.itineraryplanner.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TripRepo extends JpaRepository<Trip, Long> {
    Set<Trip> findAllByCustomer(Customer customer);
}
