package com.todocode.inventoryManagement.service;

import com.todocode.inventoryManagement.model.Customer;

import java.util.List;

public interface ICustomerService {
    List<Customer> findAllCustomers();
    Customer findCustomer(Long id);
    void saveCustomer(Customer customer);
    void editCustomer(Customer customer);
    void activateCustomer(Long id);
    void logicalDeleteCustomer(Long id);
    void deleteCustomer(Long id);
}
