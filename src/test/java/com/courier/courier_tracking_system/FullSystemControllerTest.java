package com.courier.courier_tracking_system;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.courier.courier_tracking_system.controller.*;
import com.courier.courier_tracking_system.dto.*;
import com.courier.courier_tracking_system.service.*;

public class FullSystemControllerTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private ParcelService parcelService;

    @Mock
    private ShipmentService shipmentService;

    @Mock
    private TrackingService trackingService;

    @Mock
    private DeliveryService deliveryService;

    @InjectMocks
    private CustomerController customerController;

    @InjectMocks
    private ParcelController parcelController;

    @InjectMocks
    private ShipmentController shipmentController;

    @InjectMocks
    private TrackingController trackingController;

    @InjectMocks
    private DeliveryController deliveryController;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    
    @Test
    void testGetAllCustomers() {

        CustomerDTO dto = new CustomerDTO();

        when(customerService.getAll()).thenReturn(List.of(dto));

        assertEquals(1, customerController.getAll().size());
    }

    
    @Test
    void testGetParcelById() {

        ParcelDTO dto = new ParcelDTO();
        dto.setParcelId(1L);

        when(parcelService.getById(1L)).thenReturn(dto);

        assertEquals(1L, parcelController.getById(1L).getParcelId());
    }

    
    @Test
    void testGetShipmentById() {

        ShipmentDTO dto = new ShipmentDTO();
        dto.setShipmentId(1L);

        when(shipmentService.getById(1L)).thenReturn(dto);

        assertEquals(1L, shipmentController.getById(1L).getShipmentId());
    }

   
    @Test
    void testGetTrackingByShipment() {

        TrackingDTO dto = new TrackingDTO();
        dto.setShipmentId(1L);

        when(trackingService.getByShipment(1L)).thenReturn(dto);

        assertEquals(1L,
                trackingController.getByShipment(1L).getShipmentId());
    }

    
    @Test
    void testGetDeliveryById() {

        DeliveryDTO dto = new DeliveryDTO();
        dto.setDeliveryId(1L);

        when(deliveryService.getById(1L)).thenReturn(dto);

        assertEquals(1L,
                deliveryController.getById(1L).getDeliveryId());
    }

    
    @Test
    void testCustomerLogin() {

        CustomerLoginDTO dto = new CustomerLoginDTO();
        dto.setCustomerName("Ved");

        when(parcelService.customerLogin("9999999999", 1L))
                .thenReturn(dto);

        assertEquals("Ved",
                parcelController.login("9999999999", 1L)
                        .getCustomerName());
    }

    
    @Test
    void testAdminCustomers() {

        CustomerDTO dto = new CustomerDTO();

        when(customerService.getAll()).thenReturn(List.of(dto));

        assertEquals(1,
                adminController.getCustomers().size());
    }
}