//package com.newdaylodgeandapartments.inventory_sales_management_system.repo;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import com.newdaylodgeandapartments.inventory_sales_management_system.entity.Item;
//
//public interface ItemRepository extends JpaRepository<Item, Long> {
//	 List<Item> findByCategory(String category);
//}

package com.nsaasystems.inventory_sales_management_system.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsaasystems.inventory_sales_management_system.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByCategory(String category);
}
