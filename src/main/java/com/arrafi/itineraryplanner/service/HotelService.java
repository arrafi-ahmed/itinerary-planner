package com.arrafi.itineraryplanner.service;

import com.arrafi.itineraryplanner.model.Hotel;
import com.arrafi.itineraryplanner.model.Place;
import com.arrafi.itineraryplanner.repo.HotelRepo;
import com.arrafi.itineraryplanner.repo.PlaceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelService {
    @Autowired
    HotelRepo hotelRepo;

    @Autowired
    PlaceRepo placeRepo;

    public Hotel getHotelByPlace(Long placeId) {
        Place place = placeRepo.findById(placeId).orElseThrow();
        return hotelRepo.findAllByPlace(place);
    }

    public Hotel update(Hotel updatedHotel, Long id) {
        Hotel hotel = hotelRepo.findById(id).orElseThrow();
        if (updatedHotel.getAddress() != null)
            hotel.setAddress(updatedHotel.getAddress());
        if (updatedHotel.getName() != null)
            hotel.setName(updatedHotel.getName());
        if (updatedHotel.getCost() != null)
            hotel.setCost(updatedHotel.getCost());
        if (updatedHotel.getEndTime() != null)
            hotel.setEndTime(updatedHotel.getEndTime());
        if (updatedHotel.getStartTime() != null)
            hotel.setStartTime(updatedHotel.getStartTime());
        return hotelRepo.save(hotel);
    }

    public void delete(Long id) {
        Hotel hotel = hotelRepo.findById(id).orElseThrow();
        Place place = placeRepo.findByHotel(hotel);
        place.setHotel(null);
        placeRepo.save(place);
        hotelRepo.delete(hotel);
    }
}
