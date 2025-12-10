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

/**
 * CustomerController handles all web requests related to Customer management.
 *
 * <p>Responsibilities include:
 * <ul>
 *   <li>Displaying all customers</li>
 *   <li>Adding new customers</li>
 *   <li>Editing and updating existing customers</li>
 *   <li>Deleting customers</li>
 *   <li>Generating savings projections for a customer</li>
 * </ul>
 *
 * <p>This controller uses:
 * <ul>
 *   <li>{@link CustomerRepository} for database operations</li>
 *   <li>{@link ProjectionService} for calculating investment projections</li>
 * </ul>
 */
@Controller
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProjectionService projectionService;

    /**
     * Displays the index page with a list of all customers.
     *
     * @param model the model to pass data to the view
     * @return the index view
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("customers", customerRepository.findAll());
        return "index";
    }

    /**
     * Displays the form for adding a new customer.
     *
     * @param model the model to pass a new Customer object to the view
     * @return the add view
     */
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "add";
    }

    /**
     * Saves a new customer to the database.
     * Prevents duplicate customer numbers.
     *
     * @param customer the customer to save
     * @param model the model to pass error messages if needed
     * @return redirect to index if successful, otherwise reload index with error
     */
    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute Customer customer, Model model) {
        if (customerRepository.existsByCustomerNumber(customer.getCustomerNumber())) {
            model.addAttribute("error", "The record you are trying to add is already existing. Choose a different customer number");
            model.addAttribute("customers", customerRepository.findAll());
            return "index";
        }
        customerRepository.save(customer);
        return "redirect:/";
    }

    /**
     * Displays the form for editing an existing customer.
     *
     * @param id the customer ID
     * @param model the model to pass the customer to the view
     * @return the edit view
     */
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        model.addAttribute("customer", customer);
        return "edit";
    }

    /**
     * Updates an existing customer in the database.
     * Prevents duplicate customer numbers across different records.
     *
     * @param customer the updated customer
     * @param model the model to pass error messages if needed
     * @return redirect to index if successful, otherwise reload edit with error
     */
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

    /**
     * Deletes a customer by ID.
     *
     * @param id the customer ID
     * @return redirect to index after deletion
     */
    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerRepository.deleteById(id);
        return "redirect:/";
    }

    /**
     * Generates and displays the savings projection for a customer.
     *
     * @param id the customer ID
     * @param model the model to pass customer and projection data to the view
     * @return the project view
     */
    @GetMapping("/project/{id}")
    public String projectInvestment(@PathVariable Long id, Model model) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        List<Map<String, Object>> projection = projectionService.calculateProjection(customer);

        model.addAttribute("customer", customer);
        model.addAttribute("projection", projection);
        return "project";
    }
}