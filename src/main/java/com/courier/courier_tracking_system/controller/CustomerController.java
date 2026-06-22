package com.courier.courier_tracking_system.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.courier.courier_tracking_system.dto.CustomerDTO;
import com.courier.courier_tracking_system.service.CustomerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@CrossOrigin

public class CustomerController {

    private final CustomerService service;

    @GetMapping
    public List<CustomerDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/getById/{id}")
    public CustomerDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/addCust")
    public CustomerDTO save(@RequestBody CustomerDTO dto) {
        return service.save(dto);
    }

    @PutMapping("/updateCust/{id}")
    public CustomerDTO update(@PathVariable Long id, @RequestBody CustomerDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/deleteCust/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}