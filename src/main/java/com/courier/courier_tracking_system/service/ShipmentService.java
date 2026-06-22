package com.courier.courier_tracking_system.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.courier.courier_tracking_system.dto.ShipmentDTO;
import com.courier.courier_tracking_system.entity.Parcel;
import com.courier.courier_tracking_system.entity.Shipment;
import com.courier.courier_tracking_system.entity.Tracking;
import com.courier.courier_tracking_system.repository.ParcelRepository;
import com.courier.courier_tracking_system.repository.ShipmentRepository;
import com.courier.courier_tracking_system.repository.TrackingRepository;
import com.courier.courier_tracking_system.util.MapperUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final ShipmentRepository shipmentRepo;
    private final ParcelRepository parcelRepo;
    private final TrackingRepository trackingRepo;

    
    public ShipmentDTO save(ShipmentDTO dto) {

        Parcel parcel = parcelRepo.findById(dto.getParcelId())
                .orElseThrow(() -> new RuntimeException("Parcel not found"));

        Shipment s = new Shipment();

        s.setParcel(parcel);

        s.setExpectedDelivery(dto.getExpectedDelivery());

        
        s.setStatus("BOOKED");

        Shipment saved = shipmentRepo.save(s);

       
        Tracking t = new Tracking();

        t.setTrackingNumber("TRK-" + System.currentTimeMillis());

        t.setShipment(saved);
        t.setStatus("BOOKED");
        t.setCurrentLocation(parcel.getFromAddress());
        t.setTimestamp(LocalDateTime.now());

        trackingRepo.save(t);

        return MapperUtil.toShipmentDTO(saved);
    }
    
    public List<ShipmentDTO> getAll() {
        return shipmentRepo.findAll()
                .stream()
                .map(MapperUtil::toShipmentDTO)
                .collect(Collectors.toList());
    }

       public ShipmentDTO getById(Long id) {
        Shipment s = shipmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));
        return MapperUtil.toShipmentDTO(s);
    }

  
    @Transactional
    public ShipmentDTO update(Long id, ShipmentDTO dto) {

        Shipment s = shipmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));

        if (dto.getStatus() != null) {

            String status = dto.getStatus().toUpperCase();

            s.setStatus(status);

            List<Tracking> list = trackingRepo.findByShipment(s);

            for (Tracking t : list) {

                t.setStatus(status);

                if (status.equals("BOOKED")) {
                    t.setCurrentLocation("Warehouse");
                } 
                else if (status.equals("IN_TRANSIT")) {
                    t.setCurrentLocation("On the way");
                } 
                else if (status.equals("DELIVERED")) {
                    t.setCurrentLocation("Delivered");
                }

                trackingRepo.save(t);
            }
        }

        shipmentRepo.save(s);

        return MapperUtil.toShipmentDTO(s);
    }
    
    

   
    public void delete(Long id) {
        shipmentRepo.deleteById(id);
    }

    
    private String generateTrackingNumber() {
        return "TRK-" + System.currentTimeMillis();
    }
}