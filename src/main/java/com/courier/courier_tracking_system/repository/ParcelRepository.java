package com.courier.courier_tracking_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.courier.courier_tracking_system.entity.Customer;
import com.courier.courier_tracking_system.entity.Parcel;

@Repository
public interface ParcelRepository extends JpaRepository<Parcel, Long> {

	List<Parcel> findByCustomer(Customer customer);

	List<Parcel> findByCustomerCustomerId(Long customerId);

}
