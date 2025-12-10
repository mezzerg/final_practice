package com.example.final_lab.service;

import com.example.final_lab.model.Customer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * ProjectionService is responsible for calculating the yearly savings projection
 * for a given Customer based on their deposit, savings type, and investment duration.
 *
 * <p>The projection includes:
 * <ul>
 *   <li>Year number</li>
 *   <li>Starting amount for that year</li>
 *   <li>Interest earned</li>
 *   <li>Ending balance after interest</li>
 * </ul>
 *
 * <p>Interest rates are determined by the customer's savings type:
 * <ul>
 *   <li>Savings-Deluxe → 15%</li>
 *   <li>Savings-Regular → 10%</li>
 *   <li>Default/fallback → 10%</li>
 * </ul>
 */
@Service
public class ProjectionService {

    /**
     * Calculates the projection of savings growth for the given customer.
     *
     * @param customer the customer whose savings projection is to be calculated
     * @return a list of maps, each representing one year of the projection with keys:
     *         "year", "startingAmount", "interest", "endingBalance"
     */
    public List<Map<String, Object>> calculateProjection(Customer customer) {
        List<Map<String, Object>> projection = new ArrayList<>();

        // Determine interest rate based on savings type
        double rate = switch (customer.getSavingsType()) {
            case "Savings-Deluxe" -> 0.15;
            case "Savings-Regular" -> 0.10;
            default -> 0.10; // fallback rate
        };

        // Initial deposit amount
        double startingAmount = customer.getDeposit();

        // Loop through each year and calculate interest + ending balance
        for (int year = 1; year <= customer.getYears(); year++) {
            double interest = startingAmount * rate;
            double endingBalance = startingAmount + interest;

            // Add yearly projection details to the list
            projection.add(Map.of(
                    "year", year,
                    "startingAmount", String.format("$%.2f", startingAmount),
                    "interest", String.format("$%.2f", interest),
                    "endingBalance", String.format("$%.2f", endingBalance)
            ));

            // Carry forward ending balance as next year's starting amount
            startingAmount = endingBalance;
        }

        return projection;
    }
}