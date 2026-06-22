package com.courier.courier_tracking_system.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.courier.courier_tracking_system.dto.CustomerLoginDTO;
import com.courier.courier_tracking_system.dto.ParcelDTO;
import com.courier.courier_tracking_system.entity.Customer;
import com.courier.courier_tracking_system.entity.Parcel;
import com.courier.courier_tracking_system.entity.Shipment;
import com.courier.courier_tracking_system.exception.ResourceNotFoundException;
import com.courier.courier_tracking_system.repository.CustomerRepository;
import com.courier.courier_tracking_system.repository.ParcelRepository;
import com.courier.courier_tracking_system.repository.ShipmentRepository;
import com.courier.courier_tracking_system.repository.TrackingRepository;
import com.courier.courier_tracking_system.util.MapperUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParcelService {

    private final ParcelRepository repo;
    private final CustomerRepository customerRepo;
    private final ShipmentRepository shipmentRepo;
    private final TrackingRepository trackingRepo;

   
    public List<ParcelDTO> getAll() {
        return repo.findAll()
                .stream()
                .map(MapperUtil::toParcelDTO)
                .collect(Collectors.toList());
    }

    
    public ParcelDTO getById(Long id) {

        Parcel p = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parcel not found"));

        return MapperUtil.toParcelDTO(p);
    }

   
    public List<ParcelDTO> getByCustomer(Long customerId) {

        customerRepo.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        return repo.findAll()
                .stream()
                .filter(p -> p.getCustomer() != null &&
                        p.getCustomer().getCustomerId().equals(customerId))
                .map(MapperUtil::toParcelDTO)
                .collect(Collectors.toList());
    }

    
    public CustomerLoginDTO customerLogin(String phone, Long parcelId) {

        Parcel parcel = repo.findById(parcelId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Parcel not found"));

        if (parcel.getCustomer() == null ||
                !parcel.getCustomer().getPhone().equals(phone)) {
            throw new RuntimeException("Invalid phone number");
        }

        CustomerLoginDTO dto = new CustomerLoginDTO();

        dto.setCustomerId(parcel.getCustomer().getCustomerId());
        dto.setCustomerName(parcel.getCustomer().getName());
        dto.setPhone(parcel.getCustomer().getPhone());

        dto.setParcelId(parcel.getParcelId());
        dto.setParcelType(parcel.getType());
        dto.setWeight(parcel.getWeight());

        dto.setFromAddress(parcel.getFromAddress());
        dto.setToAddress(parcel.getToAddress());

        
        dto.setShipmentId(
                parcel.getShipment() != null
                        ? parcel.getShipment().getShipmentId()
                        : null
        );

        dto.setParcelStatus(
                parcel.getShipment() != null
                        ? parcel.getShipment().getStatus()
                        : "BOOKED"
        );

        return dto;
    }

   
    public ParcelDTO save(ParcelDTO dto) {

        Customer customer = customerRepo.findById(dto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Parcel p = new Parcel();

        p.setWeight(dto.getWeight());
        p.setType(dto.getType());
        p.setFromAddress(dto.getFromAddress());
        p.setToAddress(dto.getToAddress());

       
        p.setBookingDate(LocalDate.now());

        p.setCustomer(customer);

        Parcel saved = repo.save(p);

        return MapperUtil.toParcelDTO(saved);
    }

    
    public ParcelDTO update(Long id, ParcelDTO dto) {

        Parcel p = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parcel not found"));

        if (dto.getWeight() != null) p.setWeight(dto.getWeight());
        if (dto.getType() != null) p.setType(dto.getType());
        if (dto.getBookingDate() != null) p.setBookingDate(dto.getBookingDate());

        if (dto.getFromAddress() != null) p.setFromAddress(dto.getFromAddress());
        if (dto.getToAddress() != null) p.setToAddress(dto.getToAddress());

        Parcel saved = repo.save(p);

        return MapperUtil.toParcelDTO(saved);
    }

  
    public void delete(Long parcelId) {

        Parcel parcel = repo.findById(parcelId)
                .orElseThrow(() -> new RuntimeException("Parcel not found"));

        Shipment shipment = parcel.getShipment();

        if (shipment != null) {
            trackingRepo.deleteAllByShipment(shipment);
            shipmentRepo.delete(shipment);
        }

        repo.delete(parcel);
    }
}