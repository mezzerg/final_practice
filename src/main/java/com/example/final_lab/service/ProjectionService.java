package com.example.final_lab.service;

import com.example.final_lab.model.Customer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class ProjectionService {

    public List<Map<String, Object>> calculateProjection(Customer customer) {
        List<Map<String, Object>> projection = new ArrayList<>();

        double rate = switch (customer.getSavingsType()) {
            case "Savings-Deluxe" -> 0.15;
            case "Savings-Regular" -> 0.10;
            default -> 0.10; // fallback
        };

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
