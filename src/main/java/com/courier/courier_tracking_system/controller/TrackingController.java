package com.courier.courier_tracking_system.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.courier.courier_tracking_system.dto.TrackingDTO;
import com.courier.courier_tracking_system.service.TrackingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/trackings")
@RequiredArgsConstructor
@CrossOrigin
public class TrackingController {

    private final TrackingService service;


    @GetMapping
    public List<TrackingDTO> getAll() {
        return service.getAll();
    }
    
    


    @GetMapping("/shipment/{shipmentId}")
    public TrackingDTO getByShipment(@PathVariable Long shipmentId) {
        return service.getByShipment(shipmentId);
    }

    
    @GetMapping("/track/{trackingNumber}")
    public TrackingDTO getByTrackingNumber(@PathVariable String trackingNumber) {
        return service.getByTrackingNumber(trackingNumber);
    }
    
    @PutMapping("/shipment/{shipmentId}")
    public TrackingDTO updateTrackingByShipment(
            @PathVariable Long shipmentId,
            @RequestBody TrackingDTO dto) {

        return service.updateTracking(shipmentId, dto);
    }
    
    @PostMapping
    public TrackingDTO create(@RequestBody TrackingDTO dto) {
        return service.save(dto);
    }

    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}