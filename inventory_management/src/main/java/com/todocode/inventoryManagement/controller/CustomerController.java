package com.todocode.inventoryManagement.controller;

import com.todocode.inventoryManagement.dto.CustomerDTO;
import com.todocode.inventoryManagement.model.Customer;
import com.todocode.inventoryManagement.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    private ICustomerService customerService;

    @GetMapping("/customer/getAll")
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customerList = customerService.findAllCustomers();
        List<CustomerDTO> customerDTOList = new ArrayList<>();

        for (Customer customer : customerList) {
            CustomerDTO customerDTO = new CustomerDTO(customer.getCustomerId(), customer.getName(), customer.getLastName(), customer.getDni());
            customerDTOList.add(customerDTO);
        }

        if (customerList.isEmpty()) {
            throw new RuntimeException("No active customers found");
        } else {
            return customerDTOList;
        }
    }

    @GetMapping("/customer/get/{id}")
    public CustomerDTO getCustomer(@PathVariable Long id) {
        Customer customer = customerService.findCustomer(id);
        CustomerDTO customerDTO = new CustomerDTO(customer.getCustomerId(), customer.getName(), customer.getLastName(), customer.getDni());

        return customerDTO;
    }

    @PostMapping("/customer/create")
    public String createCustomer(@RequestBody Customer customer) {
        customerService.saveCustomer(customer);
        return "Customer created successfully";
    }

    @PutMapping("/customer/edit")
    public String editCustomer(@RequestBody Customer customer) {
        customerService.editCustomer(customer);
        return "Customer updated successfully";
    }

    @PutMapping("/customer/activate/{id}")
    public String activateCustomer(@PathVariable Long id) {
        customerService.activateCustomer(id);
        return "Customer activated successfully";
    }

    @DeleteMapping("/customer/logicalDelete/{id}")
    public String logicalDeleteCustomer(@PathVariable Long id) {
        customerService.logicalDeleteCustomer(id);
        return "Customer deactivated successfully";
    }

    @DeleteMapping("/customer/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "Customer deleted successfully";
    }
}
