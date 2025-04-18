package com.todocode.inventoryManagement.dto;

public record ProductDTO(
        Long productId,
        String name,
        String brand,
        Double price,
        Integer stock) {
}
