package com.todocode.inventoryManagement.service;

import com.todocode.inventoryManagement.model.Customer;
import com.todocode.inventoryManagement.model.Product;
import com.todocode.inventoryManagement.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductRepository productRepository;

    @Override
    public List<Product> findAllProducts() {
        List<Product> productList = new ArrayList<>();

        for (Product product : productRepository.findAll()) {
            if (product.getActive()){
                productList.add(product);
            }
        }

        if (productList.isEmpty()) {
            throw new RuntimeException("No active products found");
        }

        return productList;
    }

    @Override
    public Product findProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new NullPointerException("product not found");
        }

        if (!product.getActive()){
            throw new RuntimeException("Product not found");
        }

        return product;
    }

    @Override
    public void saveProduct(Product product) {
        product.setActive(true);
        productRepository.save(product);
    }

    @Override
    public void editProduct(Product product) {
        Product productTemp = this.findProduct(product.getProductId());

        Product productEdited = new Product();
        productEdited.setProductId(product.getProductId());
        productEdited.setName(Optional.ofNullable(product.getName()).orElse(productTemp.getName()));
        productEdited.setBrand(Optional.ofNullable(product.getBrand()).orElse(productTemp.getBrand()));
        productEdited.setPrice(Optional.ofNullable(product.getPrice()).orElse(productTemp.getPrice()));
        productEdited.setStock(Optional.ofNullable(product.getStock()).orElse(productTemp.getStock()));
        productEdited.setActive(Optional.ofNullable(product.getActive()).orElse(productTemp.getActive()));

        productRepository.save(productEdited);
    }

    @Override
    public void activateProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        product.setActive(true);
        productRepository.save(product);
    }

    @Override
    public void logicalDeleteProduct(Long id) {
        Product product = this.findProduct(id);
        product.setActive(false);
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
