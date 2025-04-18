package com.todocode.inventoryManagement.service;

import com.todocode.inventoryManagement.model.Product;
import com.todocode.inventoryManagement.model.SalesDetails;
import com.todocode.inventoryManagement.repository.ISalesDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalesDetailsService implements ISalesDetailsService {
    @Autowired
    private ISalesDetailsRepository salesDetailsRepository;

    @Override
    public SalesDetails findSalesDetails(Long id) {
        return salesDetailsRepository.findById(id).orElse(null);
    }

    @Override
    public void editSalesDetails(SalesDetails salesDetails) {
        SalesDetails salesDetailsTemp = this.findSalesDetails(salesDetails.getSalesDetailsId());

        SalesDetails salesDetailsEdited = new SalesDetails();
        salesDetailsEdited.setSalesDetailsId(salesDetails.getSalesDetailsId());
        salesDetailsEdited.setQuantity(Optional.ofNullable(salesDetails.getQuantity()).orElse(salesDetailsTemp.getQuantity()));
        salesDetailsEdited.setUnitPrice(Optional.ofNullable(salesDetails.getUnitPrice()).orElse(salesDetailsTemp.getUnitPrice()));
        salesDetailsEdited.setProduct(Optional.ofNullable(salesDetails.getProduct()).orElse(salesDetailsTemp.getProduct()));
        salesDetailsEdited.setSale(Optional.ofNullable(salesDetails.getSale()).orElse(salesDetailsTemp.getSale()));

        salesDetailsRepository.save(salesDetailsEdited);
    }

    @Override
    public void deleteSalesDetails(Long id) {
        salesDetailsRepository.deleteById(id);
    }
}
