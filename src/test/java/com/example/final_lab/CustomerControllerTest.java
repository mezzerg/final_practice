package com.example.final_lab;

import com.example.final_lab.controller.CustomerController;
import com.example.final_lab.model.Customer;
import com.example.final_lab.repository.CustomerRepository;
import com.example.final_lab.service.ProjectionService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProjectionService projectionService;

    @Mock
    private Model model;

    @InjectMocks
    private CustomerController controller;

    public CustomerControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCustomerRejectsDuplicate() {
        Customer customer = new Customer(1L, 101, "Alice", 1000, 1, "Savings-Regular");
        when(customerRepository.existsByCustomerNumber(101)).thenReturn(true);

        String view = controller.saveCustomer(customer, model);

        assertEquals("index", view);
        verify(model).addAttribute(eq("error"), anyString());
        verify(customerRepository, never()).save(any());
    }

    @Test
    void testSaveCustomerAcceptsNew() {
        Customer customer = new Customer(1L, 102, "Bob", 1000, 1, "Savings-Regular");
        when(customerRepository.existsByCustomerNumber(102)).thenReturn(false);

        String view = controller.saveCustomer(customer, model);

        assertEquals("redirect:/", view);
        verify(customerRepository).save(customer);
    }

    @Test
    void testProjectInvestmentCallsService() {
        Customer customer = new Customer(1L, 103, "Charlie", 1000, 1, "Savings-Regular");
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(projectionService.calculateProjection(customer)).thenReturn(Collections.emptyList());

        String view = controller.projectInvestment(1L, model);

        assertEquals("project", view);
        verify(model).addAttribute("customer", customer);
        verify(model).addAttribute("projection", Collections.emptyList());
    }
}