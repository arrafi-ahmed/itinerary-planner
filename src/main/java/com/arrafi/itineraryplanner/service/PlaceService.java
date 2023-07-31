package com.arrafi.itineraryplanner.service;

import com.arrafi.itineraryplanner.model.*;
import com.arrafi.itineraryplanner.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceService {
    @Autowired
    PlaceRepo placeRepo;

    @Autowired
    HotelRepo hotelRepo;

    @Autowired
    TransportRepo transportRepo;

    @Autowired
    EventRepo eventRepo;

    @Autowired
    TripRepo tripRepo;

    public Place save(Place place) {
        return placeRepo.save(place);
    }

    public Hotel addHotel(Hotel hotel, Long placeId) {
        Hotel savedHotel = hotelRepo.save(hotel);
        Place place = placeRepo.findById(placeId).orElseThrow();
        place.setHotel(hotel);
        placeRepo.save(place);
        return savedHotel;
    }

    public Transport addTransport(Transport transport, Long placeId) {
        Place place = placeRepo.findById(placeId).orElseThrow();
        transport.setPlace(place);
        return transportRepo.save(transport);
    }

    public Event addEvent(Event event, Long placeId) {
        Place place = placeRepo.findById(placeId).orElseThrow();
        event.setPlace(place);
        return eventRepo.save(event);
    }

    public List<Place> getPlacesByTripId(Long tripId) {
        Trip trip = tripRepo.findById(tripId).orElseThrow();
        return placeRepo.findAllByTrip(trip);
    }

    public void delete(Long placeId) {
        Place place = placeRepo.findById(placeId).orElseThrow();
        placeRepo.delete(place);
    }

    public Place update(Place updatedPlace, Long id) {
        Place place = placeRepo.findById(id).orElseThrow();
        if (updatedPlace.getAddress() != null)
            place.setAddress(updatedPlace.getAddress());
        if (updatedPlace.getName() != null)
            place.setName(updatedPlace.getName());
        if (updatedPlace.getDescription() != null)
            place.setDescription(updatedPlace.getDescription());
        if (updatedPlace.getPhoto() != null)
            place.setPhoto(updatedPlace.getPhoto());
        if (updatedPlace.getRating() != 0.0f)
            place.setRating(updatedPlace.getRating());
        return placeRepo.save(place);
    }
}
