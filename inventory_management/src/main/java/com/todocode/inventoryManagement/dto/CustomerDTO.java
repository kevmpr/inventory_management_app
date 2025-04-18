package com.todocode.inventoryManagement.dto;

public record CustomerDTO(
        Long customerId,
        String name,
        String lastName,
        String dni) {
}
