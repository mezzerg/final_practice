package com.example.final_lab.controller;

import com.example.final_lab.model.Customer;
import com.example.final_lab.repository.CustomerRepository;
import com.example.final_lab.service.ProjectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProjectionService projectionService;

    // Show all customers
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("customers", customerRepository.findAll());
        return "index";
    }

    // Show add form
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "add";
    }

    // Save new customer
    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute Customer customer, Model model) {
        if (customerRepository.existsByCustomerNumber(customer.getCustomerNumber())) {
            model.addAttribute("error", "The record you  are trying to add is already existing. Choose a different customer number");
            model.addAttribute("customers", customerRepository.findAll());
            return "index";
        }
        customerRepository.save(customer);
        return "redirect:/";
    }

    // Show edit form
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        model.addAttribute("customer", customer);
        return "edit";
    }

    // Update customer
    @PostMapping("/update")
    public String updateCustomer(@ModelAttribute Customer customer, Model model) {

        // Check if another record already has this customerNumber
        Customer duplicate = customerRepository.findAll().stream()
                .filter(c -> c.getCustomerNumber() == customer.getCustomerNumber()
                        && !c.getId().equals(customer.getId()))
                .findFirst()
                .orElse(null);

        if (duplicate != null) {
            model.addAttribute("error", "Customer number already exists!");
            model.addAttribute("customer", customer);
            return "edit";
        }

        customerRepository.save(customer);
        return "redirect:/";
    }

    // Delete customer
    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerRepository.deleteById(id);
        return "redirect:/";
    }

    // Projected Investment
    @GetMapping("/project/{id}")
    public String projectInvestment(@PathVariable Long id, Model model) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        List<Map<String, Object>> projection = projectionService.calculateProjection(customer);

        model.addAttribute("customer", customer);
        model.addAttribute("projection", projection);
        return "project";
    }
}