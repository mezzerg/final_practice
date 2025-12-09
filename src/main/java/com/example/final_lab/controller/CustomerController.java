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

    // Show all customers
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("customers", customerRepository.findAll());
        return "index"; // maps to templates/index.html
    }

    // Show add form
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "add"; // maps to templates/add.html
    }

    // Save new customer
    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute Customer customer, Model model) {
        if (customerRepository.existsByCustomerNumber(customer.getCustomerNumber())) {
            model.addAttribute("error", "Customer number already exists!");
            model.addAttribute("customers", customerRepository.findAll());
            return "index";
        }
        customerRepository.save(customer);
        return "redirect:/";
    }

    // Show edit form
    @GetMapping("/edit/{customerNumber}")
    public String editForm(@PathVariable int customerNumber, Model model) {
        Customer customer = customerRepository.findById(customerNumber).orElseThrow();
        model.addAttribute("customer", customer);
        return "edit"; // maps to templates/edit.html
    }

    // Update customer
    @PostMapping("/update")
    public String updateCustomer(@ModelAttribute Customer customer) {
        customerRepository.save(customer);
        return "redirect:/";
    }

    // Delete customer
    @GetMapping("/delete/{customerNumber}")
    public String deleteCustomer(@PathVariable int customerNumber) {
        customerRepository.deleteById(customerNumber);
        return "redirect:/";
    }

    // Projected Investment
    @GetMapping("/project/{customerNumber}")
    public String projectInvestment(@PathVariable int customerNumber, Model model) {
        Customer customer = customerRepository.findById(customerNumber).orElseThrow();
        ProjectionService service = new ProjectionService();
        List<Map<String, Object>> projection = service.calculateProjection(customer);

        model.addAttribute("customer", customer);
        model.addAttribute("projection", projection);

        return "project"; // maps to templates/project.html
    }
}
