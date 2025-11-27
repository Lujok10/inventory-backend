
package com.nsaasystems.inventory_sales_management_system.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsaasystems.inventory_sales_management_system.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
}
