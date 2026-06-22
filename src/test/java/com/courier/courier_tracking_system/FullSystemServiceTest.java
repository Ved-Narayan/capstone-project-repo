package com.courier.courier_tracking_system;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.courier.courier_tracking_system.dto.*;
import com.courier.courier_tracking_system.entity.*;
import com.courier.courier_tracking_system.repository.*;
import com.courier.courier_tracking_system.service.*;
import com.courier.courier_tracking_system.util.MapperUtil;

public class FullSystemServiceTest {


    @Mock private CustomerRepository customerRepo;
    @Mock private ParcelRepository parcelRepo;
    @Mock private ShipmentRepository shipmentRepo;
    @Mock private TrackingRepository trackingRepo;
    @Mock private DeliveryRepository deliveryRepo;

   

    @InjectMocks private CustomerService customerService;
    @InjectMocks private ParcelService parcelService;
    @InjectMocks private ShipmentService shipmentService;
    @InjectMocks private TrackingService trackingService;
    @InjectMocks private DeliveryService deliveryService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    

    @Test
    void testGetAllCustomers() {

        Customer c = new Customer();
        c.setName("John");

        when(customerRepo.findAll()).thenReturn(List.of(c));

        List<CustomerDTO> result = customerService.getAll();

        assertEquals(1, result.size());
    }

    @Test
    void testGetCustomerById() {

        Customer c = new Customer();
        c.setCustomerId(1L);
        c.setName("John");

        when(customerRepo.findById(1L)).thenReturn(Optional.of(c));

        CustomerDTO dto = customerService.getById(1L);

        assertEquals(1L, dto.getCustomerId());
    }

    

    @Test
    void testGetParcelById() {

        Parcel p = new Parcel();
        p.setParcelId(1L);

        when(parcelRepo.findById(1L)).thenReturn(Optional.of(p));

        ParcelDTO dto = parcelService.getById(1L);

        assertEquals(1L, dto.getParcelId());
    }

    @Test
    void testGetParcelsByCustomer() {

        Customer c = new Customer();
        c.setCustomerId(1L);

        Parcel p = new Parcel();
        p.setParcelId(10L);
        p.setCustomer(c);

        when(customerRepo.findById(1L)).thenReturn(Optional.of(c));
        when(parcelRepo.findAll()).thenReturn(List.of(p));

        List<ParcelDTO> result = parcelService.getByCustomer(1L);

        assertEquals(1, result.size());
    }

   

    @Test
    void testGetShipmentById() {

        Shipment s = new Shipment();
        s.setShipmentId(1L);

        when(shipmentRepo.findById(1L)).thenReturn(Optional.of(s));

        ShipmentDTO dto = shipmentService.getById(1L);

        assertEquals(1L, dto.getShipmentId());
    }

   

    @Test
    void testGetTrackingByShipment() {

        Shipment s = new Shipment();
        s.setShipmentId(1L);

        Tracking t = new Tracking();
        t.setShipment(s);

        when(shipmentRepo.findById(1L)).thenReturn(Optional.of(s));
        when(trackingRepo.findFirstByShipment(s)).thenReturn(Optional.of(t));

        TrackingDTO dto = trackingService.getByShipment(1L);

        assertNotNull(dto);
    }



    @Test
    void testGetDeliveryById() {

        Delivery d = new Delivery();
        d.setDeliveryId(1L);

        when(deliveryRepo.findById(1L)).thenReturn(Optional.of(d));

        DeliveryDTO dto = deliveryService.getById(1L);

        assertEquals(1L, dto.getDeliveryId());
    }

    @Test
    void testGetAllDeliveries() {

        Delivery d = new Delivery();
        d.setDeliveryId(1L);

        when(deliveryRepo.findAll()).thenReturn(List.of(d));

        List<DeliveryDTO> result = deliveryService.getAll();

        assertEquals(1, result.size());
    }
}