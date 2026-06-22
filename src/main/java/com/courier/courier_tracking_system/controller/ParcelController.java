package com.courier.courier_tracking_system.controller;

import java.util.List;
import com.courier.courier_tracking_system.dto.CustomerLoginDTO;

import org.springframework.web.bind.annotation.*;

import com.courier.courier_tracking_system.dto.ParcelDTO;
import com.courier.courier_tracking_system.service.ParcelService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/parcels")
@RequiredArgsConstructor
@CrossOrigin
public class ParcelController {

    private final ParcelService service;

    @GetMapping("/getAllParcel")
    public List<ParcelDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/customer/{customerId}")
    public List<ParcelDTO> getByCustomer(@PathVariable Long customerId) {
        return service.getByCustomer(customerId);
    }

    
    @GetMapping("/{id}")
    public ParcelDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/addParcel")
    public ParcelDTO create(@RequestBody ParcelDTO dto) {
        return service.save(dto);
    }

    @PutMapping("/updateServ/{id}")
    public ParcelDTO update(@PathVariable Long id, @RequestBody ParcelDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/deleteServ/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/customer/login")
    public CustomerLoginDTO login(
            @RequestParam String phone,
            @RequestParam Long parcelId) {

        return service.customerLogin(phone, parcelId);
    }
}