package com.courier.courier_tracking_system.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tracking")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trackingId;

    private String trackingNumber;
    private String currentLocation;
    private String status;
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;
}