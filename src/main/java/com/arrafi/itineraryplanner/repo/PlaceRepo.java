package com.arrafi.itineraryplanner.repo;

import com.arrafi.itineraryplanner.model.Hotel;
import com.arrafi.itineraryplanner.model.Place;
import com.arrafi.itineraryplanner.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepo extends JpaRepository<Place, Long> {
    List<Place> findAllByTrip(Trip trip);

    Place findByHotel(Hotel hotel);
}
