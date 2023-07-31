package com.arrafi.itineraryplanner.service;

import com.arrafi.itineraryplanner.model.Event;
import com.arrafi.itineraryplanner.model.Place;
import com.arrafi.itineraryplanner.repo.EventRepo;
import com.arrafi.itineraryplanner.repo.PlaceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    @Autowired
    EventRepo eventRepo;

    @Autowired
    PlaceRepo placeRepo;

    public List<Event> getEventsByPlace(Long placeId) {
        Place place = placeRepo.findById(placeId).orElseThrow();
        return eventRepo.findAllByPlace(place);
    }

    public Event update(Event updatedEvent, Long id) {
        Event event = eventRepo.findById(id).orElseThrow();
        if (updatedEvent.getNote() != null)
            event.setNote(updatedEvent.getNote());
        if (updatedEvent.getName() != null)
            event.setName(updatedEvent.getName());
        if (updatedEvent.getPlace() != null)
            event.setPlace(updatedEvent.getPlace());
        if (updatedEvent.getCost() != null)
            event.setCost(updatedEvent.getCost());
        if (updatedEvent.getEndTime() != null)
            event.setEndTime(updatedEvent.getEndTime());
        if (updatedEvent.getStartTime() != null)
            event.setStartTime(updatedEvent.getStartTime());
        return eventRepo.save(event);
    }

    public void delete(Long id) {
        Event event = eventRepo.findById(id).orElseThrow();
        eventRepo.delete(event);
    }
}
