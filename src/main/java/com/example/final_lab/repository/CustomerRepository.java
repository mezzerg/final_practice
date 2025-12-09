package com.example.final_lab.repository;

import com.example.final_lab.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsByCustomerNumber(int customerNumber);
}
