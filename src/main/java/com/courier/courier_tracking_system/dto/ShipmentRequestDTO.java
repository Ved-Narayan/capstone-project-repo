package com.courier.courier_tracking_system.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ShipmentRequestDTO {

    private String source;
    private String destination;
    private Double weight;
    private LocalDate expectedDelivery;
}