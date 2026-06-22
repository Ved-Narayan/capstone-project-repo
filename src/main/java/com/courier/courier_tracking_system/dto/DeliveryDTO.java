package com.courier.courier_tracking_system.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDTO {

    private Long deliveryId;
    private LocalDate deliveredDate;
    private String receivedBy;

    
    private Long shipmentId;
}