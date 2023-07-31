package com.arrafi.itineraryplanner.repo;

import com.arrafi.itineraryplanner.model.Hotel;
import com.arrafi.itineraryplanner.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepo extends JpaRepository<Hotel, Long> {
    Hotel findAllByPlace(Place place);
}
