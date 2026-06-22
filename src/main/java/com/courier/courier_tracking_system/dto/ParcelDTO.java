package com.courier.courier_tracking_system.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParcelDTO {

    private Long parcelId;
    private Double weight;
    private String type;

    private String fromAddress;
    private String toAddress;

    private LocalDate bookingDate;
    private Long customerId;
    
    private Long shipmentId;
}