package com.todocode.inventoryManagement.controller;

import com.todocode.inventoryManagement.dto.ProductDTO;
import com.todocode.inventoryManagement.model.Product;
import com.todocode.inventoryManagement.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private IProductService productService;

    @GetMapping("/product/getAll")
    public List<ProductDTO> getAllProducts() {
        List<Product> productList = productService.findAllProducts();
        List<ProductDTO> productDTOList = new ArrayList<>();

        for (Product product : productList) {
            ProductDTO productDTO = new ProductDTO(product.getProductId(), product.getName(), product.getBrand(), product.getPrice(), product.getStock());
            productDTOList.add(productDTO);
        }

        if (productList.isEmpty()) {
            throw new RuntimeException("No active customers found");
        } else {
            return productDTOList;
        }
    }

    @GetMapping("/product/get/{id}")
    public ProductDTO getProduct(@PathVariable Long id) {
        Product product = productService.findProduct(id);
        ProductDTO productDTO = new ProductDTO(product.getProductId(), product.getName(), product.getBrand(), product.getPrice(), product.getStock());

        return productDTO;
    }

    @PostMapping("/product/create")
    public String createProduct(@RequestBody Product product) {
        productService.saveProduct(product);
        return "Product created successfully";
    }

    @PutMapping("/product/edit")
    public String editProduct(@RequestBody Product product) {
        productService.editProduct(product);
        return "Product updated successfully";
    }

    @PutMapping("/product/activate/{id}")
    public String activateProduct(@PathVariable Long id) {
        productService.activateProduct(id);
        return "Product activated successfully";
    }

    @DeleteMapping("/product/logicalDelete/{id}")
    public String logicalDeleteProduct(@PathVariable Long id) {
        productService.logicalDeleteProduct(id);
        return "Product deactivated successfully";
    }

    @DeleteMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "Product deleted successfully";
    }
}
