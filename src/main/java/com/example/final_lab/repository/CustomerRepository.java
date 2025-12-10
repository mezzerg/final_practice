package com.example.final_lab.repository;

import com.example.final_lab.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CustomerRepository provides CRUD operations for the Customer entity.
 *
 * By extending JpaRepository, it inherits methods like save(), findById(), findAll(), deleteById(), etc.
 *
 * The custom method existsByCustomerNumber(int customerNumber) allows checking
 * if a customer with a given customer number already exists in the database.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Checks if a customer exists with the given customer number.
     *
     * @param customerNumber the unique customer number
     * @return true if a customer with this number exists, false otherwise
     */
    boolean existsByCustomerNumber(int customerNumber);
}