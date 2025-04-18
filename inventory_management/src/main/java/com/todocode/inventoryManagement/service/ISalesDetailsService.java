package com.todocode.inventoryManagement.service;

import com.todocode.inventoryManagement.model.SalesDetails;

public interface ISalesDetailsService {
    SalesDetails findSalesDetails(Long id);
    void editSalesDetails(SalesDetails salesDetails);
    void deleteSalesDetails(Long id);
}
