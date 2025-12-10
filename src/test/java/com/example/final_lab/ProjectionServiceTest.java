package com.example.final_lab;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.example.final_lab.model.Customer;
import com.example.final_lab.service.ProjectionService;

import java.util.List;
import java.util.Map;

class ProjectionServiceTest {

    private final ProjectionService service = new ProjectionService();

    @Test
    void testDeluxeRateApplied() {
        Customer customer = new Customer(1L, 101, "Alice", 1000, 1, "Savings-Deluxe");
        List<Map<String,Object>> projection = service.calculateProjection(customer);

        assertEquals("$1000.00", projection.get(0).get("startingAmount"));
        assertEquals("$150.00", projection.get(0).get("interest"));
        assertEquals("$1150.00", projection.get(0).get("endingBalance"));
    }

    @Test
    void testRegularRateApplied() {
        Customer customer = new Customer(1L, 102, "Bob", 1000, 1, "Savings-Regular");
        List<Map<String,Object>> projection = service.calculateProjection(customer);

        assertEquals("$1000.00", projection.get(0).get("startingAmount"));
        assertEquals("$100.00", projection.get(0).get("interest"));
        assertEquals("$1100.00", projection.get(0).get("endingBalance"));
    }

    @Test
    void testCompoundInterestOverYears() {
        Customer customer = new Customer(1L, 103, "Charlie", 1000, 2, "Savings-Regular");
        List<Map<String,Object>> projection = service.calculateProjection(customer);

        assertEquals("$1100.00", projection.get(0).get("endingBalance")); // Year 1
        assertEquals("$1210.00", projection.get(1).get("endingBalance")); // Year 2
    }
}