package com.arrafi.itineraryplanner.service;

import com.arrafi.itineraryplanner.exception.UserNotFoundException;
import com.arrafi.itineraryplanner.model.Customer;
import com.arrafi.itineraryplanner.model.Place;
import com.arrafi.itineraryplanner.model.Trip;
import com.arrafi.itineraryplanner.repo.CustomerRepo;
import com.arrafi.itineraryplanner.repo.PlaceRepo;
import com.arrafi.itineraryplanner.repo.TripRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TripService {
    @Autowired
    TripRepo tripRepo;

    @Autowired
    PlaceRepo placeRepo;

    @Autowired
    CustomerRepo customerRepo;

    public Trip save(Trip trip) {
        return tripRepo.save(trip);
    }

    public Place addPlace(Place place, Long tripId) {
        Trip trip = tripRepo.findById(tripId).orElseThrow();
        place.setTrip(trip);
        return placeRepo.save(place);
    }

    public Trip getById(Long id) {
        return tripRepo.findById(id).orElseThrow();
    }

    public Trip update(Trip trip, Long id) {
        Trip updatedTrip = tripRepo.findById(id).orElseThrow();
        if (trip.getName() != null)
            updatedTrip.setName(trip.getName());
        if (trip.getStartTime() != null)
            updatedTrip.setStartTime(trip.getStartTime());
        if (trip.getEndTime() != null)
            updatedTrip.setEndTime(trip.getEndTime());
        if (trip.getCost() != null)
            updatedTrip.setCost(trip.getCost());
        return tripRepo.save(updatedTrip);
    }

    public void delete(Long id) {
        Trip trip = tripRepo.findById(id).orElseThrow();
        tripRepo.delete(trip);
    }

    public Set<Trip> getTripsByCustomerId(Long customerId) {
        Customer customer = customerRepo.findById(customerId).orElseThrow(() -> new UserNotFoundException(customerId));
        return tripRepo.findAllByCustomer(customer);
    }

    public List<Trip> getAll() {
        return tripRepo.findAll();
    }
}
