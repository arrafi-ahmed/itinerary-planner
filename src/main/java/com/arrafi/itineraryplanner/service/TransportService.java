package com.arrafi.itineraryplanner.service;

import com.arrafi.itineraryplanner.model.Place;
import com.arrafi.itineraryplanner.model.Transport;
import com.arrafi.itineraryplanner.repo.PlaceRepo;
import com.arrafi.itineraryplanner.repo.TransportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransportService {
    @Autowired
    TransportRepo transportRepo;

    @Autowired
    PlaceRepo placeRepo;

    public List<Transport> getTransportsByPlace(Long placeId) {
        Place place = placeRepo.findById(placeId).orElseThrow();
        return transportRepo.findAllByPlace(place);
    }

    public Transport update(Transport updatedTransport, Long id) {
        Transport transport = transportRepo.findById(id).orElseThrow();
        if (updatedTransport.getTransportType() != null)
            transport.setTransportType(updatedTransport.getTransportType());
        if (updatedTransport.getStartLocation() != null)
            transport.setStartLocation(updatedTransport.getStartLocation());
        if (updatedTransport.getEndLocation() != null)
            transport.setEndLocation(updatedTransport.getEndLocation());
        if (updatedTransport.getName() != null)
            transport.setName(updatedTransport.getName());
        if (updatedTransport.getCost() != null)
            transport.setCost(updatedTransport.getCost());
        if (updatedTransport.getEndTime() != null)
            transport.setEndTime(updatedTransport.getEndTime());
        if (updatedTransport.getStartTime() != null)
            transport.setStartTime(updatedTransport.getStartTime());
        return transportRepo.save(transport);
    }

    public void delete(Long id) {
        Transport transport = transportRepo.findById(id).orElseThrow();
        transportRepo.delete(transport);
    }
}
