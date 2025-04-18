package com.todocode.inventoryManagement.service;

import com.todocode.inventoryManagement.model.Product;

import java.util.List;

public interface IProductService {
    List<Product> findAllProducts();
    Product findProduct(Long id);
    void saveProduct(Product product);
    void editProduct(Product product);
    void activateProduct(Long id);
    void logicalDeleteProduct(Long id);
    void deleteProduct(Long id);
}
