
package com.courier.courier_tracking_system.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.courier.courier_tracking_system.dto.*;
import com.courier.courier_tracking_system.service.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin
public class AdminController {

    private final CustomerService customerService;
    private final ParcelService parcelService;
    private final ShipmentService shipmentService;
    private final TrackingService trackingService;
    private final DeliveryService deliveryService;

   
    @GetMapping("/customers")
    public List<CustomerDTO> getCustomers() {
        return customerService.getAll();
    }

    @PutMapping("/customers/{id}")
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO dto) {
        return customerService.update(id, dto);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.delete(id);
    }

  
    @GetMapping("/parcels")
    public List<ParcelDTO> getParcels() {
        return parcelService.getAll();
    }

    @PutMapping("/parcels/{id}")
    public ParcelDTO updateParcel(@PathVariable Long id, @RequestBody ParcelDTO dto) {
        return parcelService.update(id, dto);
    }

    @DeleteMapping("/parcels/{id}")
    public void deleteParcel(@PathVariable Long id) {
        parcelService.delete(id);
    }

    
    @GetMapping("/shipments")
    public List<ShipmentDTO> getShipments() {
        return shipmentService.getAll();
    }

    @DeleteMapping("/shipments/{id}")
    public void deleteShipment(@PathVariable Long id) {
        shipmentService.delete(id);
    }

    
    @GetMapping("/trackings")
    public List<TrackingDTO> getTrackings() {
        return trackingService.getAll();
    }

    
    @GetMapping("/deliveries")
    public List<DeliveryDTO> getDeliveries() {
        return deliveryService.getAll();
    }
}