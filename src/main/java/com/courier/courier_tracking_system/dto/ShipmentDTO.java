
package com.courier.courier_tracking_system.dto;

import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentDTO {

	 private Long shipmentId;
	 private Long parcelId;
	 private LocalDate expectedDelivery;
	 private String status;
}