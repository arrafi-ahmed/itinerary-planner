package com.arrafi.itineraryplanner.repo;

import com.arrafi.itineraryplanner.model.Event;
import com.arrafi.itineraryplanner.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepo extends JpaRepository<Event, Long> {
    List<Event> findAllByPlace(Place place);
}
