package com.arrafi.itineraryplanner.repo;

import com.arrafi.itineraryplanner.model.Place;
import com.arrafi.itineraryplanner.model.Transport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransportRepo extends JpaRepository<Transport, Long> {
    List<Transport> findAllByPlace(Place place);
}
