package com.arrafi.itineraryplanner.controller;

import com.arrafi.itineraryplanner.model.Hotel;
import com.arrafi.itineraryplanner.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    HotelService hotelService;

    @GetMapping("/place-{id}")
    public ResponseEntity<Hotel> getHotelByPlace(@PathVariable("id") Long placeId) {
        return ResponseEntity.ok(hotelService.getHotelByPlace(placeId));
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<Hotel> update(@RequestBody Hotel hotel, @PathVariable Long id) {
        return ResponseEntity.ok(hotelService.update(hotel, id));
    }

    @GetMapping("/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable("id") Long hotelId) {
        hotelService.delete(hotelId);
        return ResponseEntity.noContent().build();
    }
}
