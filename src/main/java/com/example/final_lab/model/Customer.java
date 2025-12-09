package com.example.final_lab.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Customer {
    @Id
    private int customerNumber;
    private String name;
    private double deposit;
    private int years;
    private String savingsType;

    public Customer() {}

    public Customer(int customerNumber, String name, double deposit, int years, String savingsType) {
        this.customerNumber = customerNumber;
        this.name = name;
        this.deposit = deposit;
        this.years = years;
        this.savingsType = savingsType;
    }

    // Getters and setters
    public int getCustomerNumber() { return customerNumber; }
    public void setCustomerNumber(int customerNumber) { this.customerNumber = customerNumber; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getDeposit() { return deposit; }
    public void setDeposit(double deposit) { this.deposit = deposit; }

    public int getYears() { return years; }
    public void setYears(int years) { this.years = years; }

    public String getSavingsType() { return savingsType; }
    public void setSavingsType(String savingsType) { this.savingsType = savingsType; }
}
