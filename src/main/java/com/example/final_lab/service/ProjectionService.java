package com.example.final_lab.service;

import com.example.final_lab.model.Customer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProjectionService {

    public List<Map<String, Object>> calculateProjection(Customer customer) {
        List<Map<String, Object>> projection = new ArrayList<>();

        double rate = customer.getSavingsType().equals("Savings-Deluxe") ? 0.15 : 0.10;
        double startingAmount = customer.getDeposit();

        for (int year = 1; year <= customer.getYears(); year++) {
            double interest = startingAmount * rate;
            double endingBalance = startingAmount + interest;

            projection.add(Map.of(
                    "year", year,
                    "startingAmount", String.format("$%.2f", startingAmount),
                    "interest", String.format("$%.2f", interest),
                    "endingBalance", String.format("$%.2f", endingBalance)
            ));

            startingAmount = endingBalance; // carry forward
        }

        return projection;
    }
}
