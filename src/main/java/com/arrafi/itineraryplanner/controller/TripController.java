package com.arrafi.itineraryplanner.controller;

import com.arrafi.itineraryplanner.model.Place;
import com.arrafi.itineraryplanner.model.Trip;
import com.arrafi.itineraryplanner.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/trip")
public class TripController {
    @Autowired
    TripService tripService;

    @PostMapping("/save")
    public ResponseEntity<Trip> save(@RequestBody Trip trip) {
        return new ResponseEntity<>(tripService.save(trip), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<Trip>> getAll() {
        return ResponseEntity.ok(tripService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getById(@PathVariable Long id) {
        return ResponseEntity.ok(tripService.getById(id));
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<Trip> update(@RequestBody Trip trip, @PathVariable Long id) {
        return ResponseEntity.ok(tripService.update(trip, id));
    }

    @GetMapping("/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        tripService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/addPlace")
    public ResponseEntity<Place> addPlace(@RequestBody Place place, @PathVariable("id") Long tripId) {
        return ResponseEntity.ok(tripService.addPlace(place, tripId));
    }

    @GetMapping("/customer-{id}")
    public ResponseEntity<Set<Trip>> getTripsByCustomerId(@PathVariable("id") Long customerId) {
        return ResponseEntity.ok(tripService.getTripsByCustomerId(customerId));
    }
}
