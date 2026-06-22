package com.courier.courier_tracking_system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.courier.courier_tracking_system.entity.Delivery;
import com.courier.courier_tracking_system.entity.Shipment;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long>  {
	
	Optional<Delivery> findByShipment(Shipment shipment);

}
