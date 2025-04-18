package com.todocode.inventoryManagement.repository;

import com.todocode.inventoryManagement.model.SalesDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISalesDetailsRepository extends JpaRepository<SalesDetails, Long> {
}
