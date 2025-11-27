package com.nsaasystems.inventory_sales_management_system.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsaasystems.inventory_sales_management_system.entity.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
