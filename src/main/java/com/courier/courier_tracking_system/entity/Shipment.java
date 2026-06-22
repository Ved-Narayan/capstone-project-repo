package com.courier.courier_tracking_system.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shipment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shipmentId;

    private String status;
    private LocalDateTime createdAt;
    private LocalDate expectedDelivery;

    @OneToOne
    @JoinColumn(name = "parcel_id")
    private Parcel parcel;

    @OneToMany(mappedBy = "shipment",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Tracking> trackingList;

    @OneToOne(mappedBy = "shipment",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Delivery delivery;
}