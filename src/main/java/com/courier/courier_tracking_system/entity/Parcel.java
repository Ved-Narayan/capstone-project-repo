package com.courier.courier_tracking_system.entity;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "parcel")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parcel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parcelId;

    private Double weight;
    private String type;

    private String fromAddress;
    private String toAddress;

    private LocalDate bookingDate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(mappedBy = "parcel",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Shipment shipment;
}