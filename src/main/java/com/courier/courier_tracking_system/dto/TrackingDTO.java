package com.courier.courier_tracking_system.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackingDTO {

	private Long trackingId;

    private String trackingNumber; 

    private String currentLocation;

    private String status;

    private LocalDateTime timestamp;

    private Long shipmentId;
}