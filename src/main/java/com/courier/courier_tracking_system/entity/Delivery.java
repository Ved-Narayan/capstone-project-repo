package com.courier.courier_tracking_system.entity;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "delivery")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryId;

    private LocalDate deliveredDate;
    private String receivedBy;

    @OneToOne
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;
}