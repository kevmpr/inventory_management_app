package com.todocode.inventoryManagement.dto;

import java.time.LocalDate;
import java.util.List;

public record SaleDTO(
        Long saleId,
        String customerName,
        Double totalPrice,
        LocalDate saleDate,
        List<SalesDetailsDTO> salesDetails
) {
}
