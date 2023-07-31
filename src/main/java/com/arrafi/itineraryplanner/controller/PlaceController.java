package com.arrafi.itineraryplanner.controller;

import com.arrafi.itineraryplanner.model.Event;
import com.arrafi.itineraryplanner.model.Hotel;
import com.arrafi.itineraryplanner.model.Place;
import com.arrafi.itineraryplanner.model.Transport;
import com.arrafi.itineraryplanner.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/place")
public class PlaceController {
    @Autowired
    PlaceService placeService;

    @PostMapping("/save")
    public ResponseEntity<Place> save(@RequestBody Place place) {
        return new ResponseEntity<>(placeService.save(place), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/addHotel")
    public ResponseEntity<Hotel> addHotel(@RequestBody Hotel hotel, @PathVariable("id") Long placeId) {
        return ResponseEntity.ok(placeService.addHotel(hotel, placeId));
    }

    @PostMapping("/{id}/addTransport")
    public ResponseEntity<Transport> addTransport(@RequestBody Transport transport, @PathVariable("id") Long placeId) {
        return ResponseEntity.ok(placeService.addTransport(transport, placeId));
    }

    @PostMapping("/{id}/addEvent")
    public ResponseEntity<Event> addEvent(@RequestBody Event event, @PathVariable("id") Long placeId) {
        return ResponseEntity.ok(placeService.addEvent(event, placeId));
    }

    @GetMapping("/trip-{id}")
    public ResponseEntity<List<Place>> getPlacesByTripId(@PathVariable("id") Long tripId) {
        return ResponseEntity.ok(placeService.getPlacesByTripId(tripId));
    }

    @GetMapping("/{id}/delete")
    public ResponseEntity<?> deletePlace(@PathVariable("id") Long placeId) {
        placeService.delete(placeId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<Place> update(@RequestBody Place place, @PathVariable Long id) {
        return ResponseEntity.ok(placeService.update(place, id));
    }
}
