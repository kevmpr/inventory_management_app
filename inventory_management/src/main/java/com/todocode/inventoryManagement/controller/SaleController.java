package com.todocode.inventoryManagement.controller;

import com.todocode.inventoryManagement.dto.SaleDTO;
import com.todocode.inventoryManagement.dto.SalesDetailsDTO;
import com.todocode.inventoryManagement.model.Sale;
import com.todocode.inventoryManagement.service.ISaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SaleController {
    @Autowired
    private ISaleService saleService;

    @GetMapping("/sale/getAll")
    public List<SaleDTO> getAllSales() {
        List<Sale> saleList = saleService.findAllSales();
        List<SaleDTO> saleDTOList  = new ArrayList<>();

        for (Sale sale : saleList) {
            Sale saleTemp = saleService.findSale(sale.getSaleId());
            List<SalesDetailsDTO> salesDetails = saleTemp.getSalesDetailsList().stream()
                    .map(salesDetailsItem -> new SalesDetailsDTO(
                            salesDetailsItem.getSalesDetailsId(),
                            salesDetailsItem.getProduct().getName(),
                            salesDetailsItem.getUnitPrice(),
                            salesDetailsItem.getQuantity(),
                            salesDetailsItem.getUnitPrice() * salesDetailsItem.getQuantity()))
                    .toList();

            SaleDTO saleDTO = new SaleDTO(saleTemp.getSaleId(), saleTemp.getCustomer().getName(), saleTemp.getTotalPrice(), saleTemp.getSaleDate(), salesDetails);
            saleDTOList.add(saleDTO);
        }

        if (saleList.isEmpty()) {
            throw new RuntimeException("No active customers found");
        } else {
            return saleDTOList;
        }
    }

    @GetMapping("/sale/get/{id}")
    public SaleDTO getSale(@PathVariable Long id) {
        Sale sale = saleService.findSale(id);
        List<SalesDetailsDTO> salesDetails = sale.getSalesDetailsList().stream()
                .map(salesDetailsItem -> new SalesDetailsDTO(
                        salesDetailsItem.getSalesDetailsId(),
                        salesDetailsItem.getProduct().getName(),
                        salesDetailsItem.getUnitPrice(),
                        salesDetailsItem.getQuantity(),
                        salesDetailsItem.getUnitPrice() * salesDetailsItem.getQuantity()))
                .toList();
        SaleDTO saleDTO = new SaleDTO(sale.getSaleId(), sale.getCustomer().getName(), sale.getTotalPrice(), sale.getSaleDate(), salesDetails);

        return saleDTO;
    }

    @PostMapping("/sale/create")
    public String createSale(@RequestBody Sale sale) {
        saleService.saveSale(sale);
        return "Sale created successfully";
    }

    @PutMapping("/sale/activate/{id}")
    public String activateSale(@PathVariable Long id) {
        saleService.activateSale(id);
        return "Sale activated successfully";
    }

    @DeleteMapping("/sale/logicalDelete/{id}")
    public String logicalDeleteProduct(@PathVariable Long id) {
        saleService.logicalDeleteSale(id);
        return "Sale deactivated successfully";
    }

    @DeleteMapping("/sale/delete/{id}")
    public String deleteSale(@PathVariable Long id) {
        saleService.deleteSale(id);
        return "Sale deleted successfully";
    }
}
