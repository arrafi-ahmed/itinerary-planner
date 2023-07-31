package com.arrafi.itineraryplanner.controller;

import com.arrafi.itineraryplanner.model.Transport;
import com.arrafi.itineraryplanner.service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transport")
public class TransportController {
    @Autowired
    TransportService transportService;

    @GetMapping("/place-{id}")
    public ResponseEntity<List<Transport>> getTransportByPlace(@PathVariable("id") Long placeId) {
        return ResponseEntity.ok(transportService.getTransportsByPlace(placeId));
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<Transport> update(@RequestBody Transport transport, @PathVariable("id") Long id) {
        return ResponseEntity.ok(transportService.update(transport, id));
    }

    @GetMapping("/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable("id") Long transportId) {
        transportService.delete(transportId);
        return ResponseEntity.noContent().build();
    }
}
