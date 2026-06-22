package com.courier.courier_tracking_system.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.courier.courier_tracking_system.dto.DeliveryDTO;
import com.courier.courier_tracking_system.service.DeliveryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/deliveries")
@RequiredArgsConstructor
@CrossOrigin
public class DeliveryController {

    private final DeliveryService service;

    @GetMapping("/getAllDelivery")
    public List<DeliveryDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/getById/{id}")
    public DeliveryDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/addDelivery")
    public DeliveryDTO create(@RequestBody DeliveryDTO dto) {
        return service.save(dto);
    }

    @DeleteMapping("/deleteDelivery/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}