package com.todocode.inventoryManagement.service;

import com.todocode.inventoryManagement.model.Sale;

import java.util.List;

public interface ISaleService {
    List<Sale> findAllSales();
    Sale findSale(Long id);
    void saveSale(Sale sale);
    void activateSale(Long id);
    void logicalDeleteSale(Long id);
    void deleteSale(Long id);
}
