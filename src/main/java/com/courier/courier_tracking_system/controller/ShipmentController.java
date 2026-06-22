package com.courier.courier_tracking_system.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.courier.courier_tracking_system.dto.ShipmentDTO;
import com.courier.courier_tracking_system.dto.ShipmentRequestDTO;
import com.courier.courier_tracking_system.service.ShipmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/shipments")
@RequiredArgsConstructor
@CrossOrigin
public class ShipmentController {

    private final ShipmentService service;

    @GetMapping("/getAllShipment")
    public List<ShipmentDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/getById/{id}")
    public ShipmentDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/addShipment")
    public ShipmentDTO create(@RequestBody ShipmentDTO dto) {
        return service.save(dto);
    }

    @PutMapping("/updateShip/{id}")
    public ShipmentDTO updateShipment(@PathVariable Long id,
                                      @RequestBody ShipmentDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/deleteShip/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}