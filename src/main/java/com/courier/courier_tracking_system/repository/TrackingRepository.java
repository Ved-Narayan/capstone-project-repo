package com.courier.courier_tracking_system.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.courier.courier_tracking_system.entity.Shipment;
import com.courier.courier_tracking_system.entity.Tracking;

@Repository
public interface TrackingRepository extends JpaRepository<Tracking, Long> {

    List<Tracking> findByShipment(Shipment shipment);

    Optional<Tracking> findByTrackingNumber(String trackingNumber);
    
    void deleteAllByShipment(Shipment shipment);
    
    Optional<Tracking> findFirstByShipment(Shipment shipment);
    
}