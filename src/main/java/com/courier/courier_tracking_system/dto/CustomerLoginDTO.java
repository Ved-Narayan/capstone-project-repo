package com.courier.courier_tracking_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerLoginDTO {

   
    private Long customerId;
    private String customerName;
    private String phone;


    private Long parcelId;
    private String parcelType;
    private Double weight;
    private String fromAddress;
    private String toAddress;

   
    private Long shipmentId;

  
    private String parcelStatus;
}