package com.todocode.inventoryManagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class SalesDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long salesDetailsId;
    private Integer quantity;
    private Double unitPrice;
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "saleId")
    private Sale sale;
}
