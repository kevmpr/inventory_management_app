package com.todocode.inventoryManagement.service;

import com.todocode.inventoryManagement.model.Product;
import com.todocode.inventoryManagement.model.Sale;
import com.todocode.inventoryManagement.model.SalesDetails;
import com.todocode.inventoryManagement.repository.ISaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaleService implements ISaleService {
    @Autowired
    private ISaleRepository saleRepository;
    @Autowired
    private IProductService productService;
    @Autowired
    private ISalesDetailsService salesDetailsService;
    @Autowired
    private ICustomerService customerService;

    @Override
    public List<Sale> findAllSales() {
        List<Sale> saleList = new ArrayList<>();

        for (Sale sale : saleRepository.findAll()) {
            if (sale.getActive()){
                saleList.add(sale);
            }
        }

        if (saleList.isEmpty()) {
            throw new RuntimeException("No active products found");
        }

        return saleList;
    }

    @Override
    public Sale findSale(Long id) {
        Sale sale = saleRepository.findById(id).orElse(null);
        if (sale == null) {
            throw new NullPointerException("product not found");
        }

        if (!sale.getActive()){
            throw new RuntimeException("Product not found");
        }

        return sale;
    }

    @Override
    public void saveSale(Sale sale) {
        Double totalPrice = 0.0;

        if (!sale.getSalesDetailsList().isEmpty()){
            for (SalesDetails salesDetails : sale.getSalesDetailsList()) {
                Integer stock = 0;
                Product product = productService.findProduct(salesDetails.getProduct().getProductId());

                salesDetails.setUnitPrice(product.getPrice());
                totalPrice += salesDetails.getUnitPrice() * salesDetails.getQuantity();

                stock += salesDetails.getQuantity();

                if (product.getStock() < stock) {
                    throw new RuntimeException("Insufficient stock for product: " + product.getName());
                }

                product.setStock(product.getStock() - stock);
            }
        }

        sale.setTotalPrice(totalPrice);
        sale.setActive(true);
        saleRepository.save(sale);

        for (SalesDetails salesDetails : sale.getSalesDetailsList()) {
            salesDetails.setSale(sale);
            salesDetailsService.editSalesDetails(salesDetails);
        }
    }

    @Override
    public void activateSale(Long id) {
        Sale sale = saleRepository.findById(id).orElse(null);
        sale.setActive(true);
        saleRepository.save(sale);
    }

    @Override
    public void logicalDeleteSale(Long id) {
        Sale sale = this.findSale(id);
        sale.setActive(false);
        saleRepository.save(sale);
    }

    @Override
    public void deleteSale(Long id) {
        Sale sale = this.findSale(id);
        for (SalesDetails salesDetails : sale.getSalesDetailsList()) {
            salesDetailsService.deleteSalesDetails(salesDetails.getSalesDetailsId());
        }
        saleRepository.deleteById(id);
    }
}
