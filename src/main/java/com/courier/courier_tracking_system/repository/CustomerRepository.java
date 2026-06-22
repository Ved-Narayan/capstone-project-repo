package com.courier.courier_tracking_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.courier.courier_tracking_system.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>  {
	
	Customer findByEmail(String email);

}
