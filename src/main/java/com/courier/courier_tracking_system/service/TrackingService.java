package com.courier.courier_tracking_system.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.courier.courier_tracking_system.dto.TrackingDTO;
import com.courier.courier_tracking_system.entity.Shipment;
import com.courier.courier_tracking_system.entity.Tracking;
import com.courier.courier_tracking_system.repository.ShipmentRepository;
import com.courier.courier_tracking_system.repository.TrackingRepository;
import com.courier.courier_tracking_system.util.MapperUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TrackingService {

    private final TrackingRepository repo;
    private final ShipmentRepository shipmentRepo;

    
    public List<TrackingDTO> getAll() {
        return repo.findAll()
                .stream()
                .map(MapperUtil::toTrackingDTO)
                .collect(Collectors.toList());
    }

   
    public TrackingDTO getByShipment(Long shipmentId) {

        Shipment s = shipmentRepo.findById(shipmentId)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));

        Tracking t = repo.findFirstByShipment(s)
                .orElseThrow(() -> new RuntimeException("Tracking not found"));

        return MapperUtil.toTrackingDTO(t);
    }

    
    public TrackingDTO save(TrackingDTO dto) {

        Shipment s = shipmentRepo.findById(dto.getShipmentId())
                .orElseThrow(() -> new RuntimeException("Shipment not found"));

        Tracking t = new Tracking();
        t.setTrackingNumber(generateTrackingNumber());
        t.setShipment(s);
        t.setStatus(dto.getStatus());
        t.setCurrentLocation(dto.getCurrentLocation());
        t.setTimestamp(LocalDateTime.now());

        return MapperUtil.toTrackingDTO(repo.save(t));
    }
    public TrackingDTO getByTrackingNumber(String trackingNumber) {

        Tracking t = repo.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new RuntimeException("Tracking not found"));

        return MapperUtil.toTrackingDTO(t);
    }

   
    @Transactional
    public TrackingDTO updateTracking(Long shipmentId, TrackingDTO dto) {

        Shipment s = shipmentRepo.findById(shipmentId)
            .orElseThrow(() -> new RuntimeException("Shipment not found"));

        List<Tracking> list = repo.findByShipment(s);

        for (Tracking t : list) {

            t.setStatus(dto.getStatus());

            if (dto.getStatus().equals("BOOKED")) {
                t.setCurrentLocation("Warehouse");
            }

            if (dto.getStatus().equals("IN_TRANSIT")) {
                t.setCurrentLocation("On the way");
            }

            if (dto.getStatus().equals("DELIVERED")) {
                t.setCurrentLocation("Delivered");
            }

            repo.save(t);
        }

        // SYNC shipment too
        s.setStatus(dto.getStatus());
        shipmentRepo.save(s);

        return MapperUtil.toTrackingDTO(list.get(0));
    }

   
    public void delete(Long id) {
        repo.deleteById(id);
    }

    private String generateTrackingNumber() {
        return "TRK-" + System.currentTimeMillis();
    }
}