package com.courier.courier_tracking_system.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.courier.courier_tracking_system.dto.DeliveryDTO;
import com.courier.courier_tracking_system.entity.Delivery;
import com.courier.courier_tracking_system.entity.Shipment;
import com.courier.courier_tracking_system.exception.ResourceNotFoundException;
import com.courier.courier_tracking_system.repository.DeliveryRepository;
import com.courier.courier_tracking_system.repository.ShipmentRepository;
import com.courier.courier_tracking_system.util.MapperUtil;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepo;
    private final ShipmentRepository shipmentRepo;

    public List<DeliveryDTO> getAll() {
        return deliveryRepo.findAll()
                .stream()
                .map(MapperUtil::toDeliveryDTO)
                .toList();
    }

    public DeliveryDTO getById(Long id) {
        Delivery delivery = deliveryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found"));

        return MapperUtil.toDeliveryDTO(delivery);
    }

    public DeliveryDTO save(DeliveryDTO dto) {

        Shipment shipment = shipmentRepo.findById(dto.getShipmentId())
                .orElseThrow(() -> new RuntimeException("Shipment not found"));

        Delivery d = new Delivery();
        d.setDeliveredDate(dto.getDeliveredDate());
        d.setReceivedBy(dto.getReceivedBy());
        d.setShipment(shipment);

        return MapperUtil.toDeliveryDTO(deliveryRepo.save(d));
    }

    public void delete(Long id) {
        deliveryRepo.deleteById(id);
    }
}