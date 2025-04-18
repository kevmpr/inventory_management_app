package com.todocode.inventoryManagement.dto;

public record SalesDetailsDTO(
        Long salesDetailsId,
        String productName,
        Double unitPrice,
        Integer quantity,
        Double totalPrice
) {
}
