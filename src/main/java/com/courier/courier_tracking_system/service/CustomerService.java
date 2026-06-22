package com.courier.courier_tracking_system.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.courier.courier_tracking_system.dto.CustomerDTO;
import com.courier.courier_tracking_system.entity.Customer;
import com.courier.courier_tracking_system.exception.ResourceNotFoundException;
import com.courier.courier_tracking_system.repository.CustomerRepository;
import com.courier.courier_tracking_system.util.MapperUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repo;

    public List<CustomerDTO> getAll() {
        return repo.findAll()
                .stream()
                .map(MapperUtil::toCustomerDTO)
                .collect(Collectors.toList());
    }

    public CustomerDTO getById(Long id) {
        Customer c = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        return MapperUtil.toCustomerDTO(c);
    }

    public CustomerDTO save(CustomerDTO dto) {
        Customer c = MapperUtil.toCustomerEntity(dto);
        return MapperUtil.toCustomerDTO(repo.save(c));
    }

    public CustomerDTO update(Long id, CustomerDTO dto) {

        Customer existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setPhone(dto.getPhone());
        existing.setDefaultAddress(dto.getDefaultAddress());

        return MapperUtil.toCustomerDTO(repo.save(existing));
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}