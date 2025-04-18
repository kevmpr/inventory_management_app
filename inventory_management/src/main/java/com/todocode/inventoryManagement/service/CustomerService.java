package com.todocode.inventoryManagement.service;

import com.todocode.inventoryManagement.model.Customer;
import com.todocode.inventoryManagement.model.Product;
import com.todocode.inventoryManagement.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements ICustomerService {
    @Autowired
    private ICustomerRepository customerRepository;

    @Override
    public List<Customer> findAllCustomers() {
        List<Customer> customerList = new ArrayList<>();

        for (Customer customer : customerRepository.findAll()) {
            if (customer.getActive()){
                customerList.add(customer);
            }
        }

        if (customerList.isEmpty()) {
            throw new RuntimeException("No active customers found");
        }

        return customerList;
    }

    @Override
    public Customer findCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) {
            throw new NullPointerException("Customer not found");
        }

        if (!customer.getActive()){
            throw new RuntimeException("Customer not found");
        }

        return customer;
    }

    @Override
    public void saveCustomer(Customer customer) {
        customer.setActive(true);
        customerRepository.save(customer);
    }

    @Override
    public void editCustomer(Customer customer) {
        Customer customerTemp = this.findCustomer(customer.getCustomerId());

        Customer customerEdited = new Customer();
        customerEdited.setCustomerId(customer.getCustomerId());
        customerEdited.setName(Optional.ofNullable(customer.getName()).orElse(customerTemp.getName()));
        customerEdited.setLastName(Optional.ofNullable(customer.getLastName()).orElse(customerTemp.getLastName()));
        customerEdited.setDni(Optional.ofNullable(customer.getDni()).orElse(customerTemp.getDni()));
        customerEdited.setActive(Optional.ofNullable(customer.getActive()).orElse(customerTemp.getActive()));

        customerRepository.save(customerEdited);
    }

    @Override
    public void activateCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        customer.setActive(true);
        customerRepository.save(customer);
    }

    @Override
    public void logicalDeleteCustomer(Long id) {
        Customer customer = this.findCustomer(id);
        customer.setActive(false);
        customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
