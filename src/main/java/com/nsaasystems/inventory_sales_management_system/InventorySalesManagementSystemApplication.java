package com.nsaasystems.inventory_sales_management_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.nsaasystems.inventory_sales_management_system.entity.User;
import com.nsaasystems.inventory_sales_management_system.entity.Role;
import com.nsaasystems.inventory_sales_management_system.repo.UserRepository;

@SpringBootApplication
public class InventorySalesManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventorySalesManagementSystemApplication.class, args);
    }

    // ✅ Seed a default MANAGER user if it doesn't exist
    @Bean
    public CommandLineRunner initDefaultManager(UserRepository userRepository) {
        return args -> {
            String defaultUsername = "manager";
            String defaultPassword = "Password123!"; // change later if you want

            // check if manager already exists
            if (userRepository.findByUsername(defaultUsername).isEmpty()) {
                User user = new User();
                user.setUsername(defaultUsername);
                user.setPassword(BCrypt.hashpw(defaultPassword, BCrypt.gensalt()));
                user.setRole(Role.MANAGER);

                userRepository.save(user);
                System.out.println("✅ Default MANAGER user created: " + defaultUsername);
            } else {
                System.out.println("✅ Default MANAGER already exists, skipping creation.");
            }
        };
    }
}
