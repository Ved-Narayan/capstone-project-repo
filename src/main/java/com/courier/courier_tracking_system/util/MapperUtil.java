package com.courier.courier_tracking_system.util;

import com.courier.courier_tracking_system.dto.*;
import com.courier.courier_tracking_system.entity.*;

public class MapperUtil {

	

	public static CustomerDTO toCustomerDTO(Customer c) {
		if (c == null)
			return null;

		return new CustomerDTO(c.getCustomerId(), c.getName(), c.getEmail(), c.getPhone(), c.getDefaultAddress());
	}

	public static Customer toCustomerEntity(CustomerDTO dto) {
		if (dto == null)
			return null;

		Customer c = new Customer();
		c.setCustomerId(dto.getCustomerId());
		c.setName(dto.getName());
		c.setEmail(dto.getEmail());
		c.setPhone(dto.getPhone());
		c.setDefaultAddress(dto.getDefaultAddress());
		return c;
	}

	

	public static ParcelDTO toParcelDTO(Parcel p) {

	    if (p == null) return null;

	    ParcelDTO dto = new ParcelDTO();

	    dto.setParcelId(p.getParcelId());
	    dto.setWeight(p.getWeight());
	    dto.setType(p.getType());
	    dto.setBookingDate(p.getBookingDate());
	    dto.setFromAddress(p.getFromAddress());
	    dto.setToAddress(p.getToAddress());


	    dto.setShipmentId(
	        p.getShipment() != null
	            ? p.getShipment().getShipmentId()
	            : null
	    );

	    dto.setCustomerId(
	        p.getCustomer() != null
	            ? p.getCustomer().getCustomerId()
	            : null
	    );

	    return dto;
	}

	

	public static Parcel toParcelEntity(ParcelDTO dto, Customer customer) {
	    if (dto == null) return null;

	    Parcel p = new Parcel();
	    p.setParcelId(dto.getParcelId());
	    p.setWeight(dto.getWeight());
	    p.setType(dto.getType());
	    p.setBookingDate(dto.getBookingDate());

	    p.setFromAddress(dto.getFromAddress());   
	    p.setToAddress(dto.getToAddress());      

	    p.setCustomer(customer);
	    return p;
	}

	

	public static ShipmentDTO toShipmentDTO(Shipment s) {

	    if (s == null) return null;

	    ShipmentDTO dto = new ShipmentDTO();

	    dto.setShipmentId(s.getShipmentId());
	    dto.setStatus(s.getStatus());  // 🔥 CRITICAL FIX
	    dto.setExpectedDelivery(s.getExpectedDelivery());

	    dto.setParcelId(
	        s.getParcel() != null ? s.getParcel().getParcelId() : null
	    );

	    return dto;
	}
	

	public static TrackingDTO toTrackingDTO(Tracking t) {

	    if (t == null) return null;

	    TrackingDTO dto = new TrackingDTO();

	    dto.setTrackingId(t.getTrackingId());
	    dto.setTrackingNumber(t.getTrackingNumber());
	    
	    dto.setCurrentLocation(t.getCurrentLocation());
	    dto.setStatus(t.getStatus());
	    dto.setTimestamp(t.getTimestamp());

	    dto.setShipmentId(
	        t.getShipment() != null ? t.getShipment().getShipmentId() : null
	    );

	    return dto;
	}
	
	

	public static DeliveryDTO toDeliveryDTO(Delivery d) {
		if (d == null)
			return null;

		return new DeliveryDTO(d.getDeliveryId(), d.getDeliveredDate(), d.getReceivedBy(),
				d.getShipment() != null ? d.getShipment().getShipmentId() : null);
	}

	public static Delivery toDeliveryEntity(DeliveryDTO dto, Shipment shipment) {
		if (dto == null)
			return null;

		Delivery d = new Delivery();
		d.setDeliveryId(dto.getDeliveryId());
		d.setDeliveredDate(dto.getDeliveredDate());
		d.setReceivedBy(dto.getReceivedBy());
		d.setShipment(shipment);
		return d;
	}
}