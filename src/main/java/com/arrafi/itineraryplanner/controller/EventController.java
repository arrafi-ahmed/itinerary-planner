package com.arrafi.itineraryplanner.controller;

import com.arrafi.itineraryplanner.model.Event;
import com.arrafi.itineraryplanner.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {
    @Autowired
    EventService eventService;

    @GetMapping("/place-{id}")
    public ResponseEntity<List<Event>> getEventsByPlace(@PathVariable("id") Long placeId) {
        return ResponseEntity.ok(eventService.getEventsByPlace(placeId));
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<Event> update(@RequestBody Event event, @PathVariable("id") Long id) {
        return ResponseEntity.ok(eventService.update(event, id));
    }

    @GetMapping("/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable("id") Long eventId) {
        eventService.delete(eventId);
        return ResponseEntity.noContent().build();
    }
}
