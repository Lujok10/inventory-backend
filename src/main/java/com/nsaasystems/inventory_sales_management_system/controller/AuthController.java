package com.nsaasystems.inventory_sales_management_system.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nsaasystems.inventory_sales_management_system.entity.Role;
import com.nsaasystems.inventory_sales_management_system.entity.User;
import com.nsaasystems.inventory_sales_management_system.repo.UserRepository;

@RestController
@CrossOrigin(origins = {"https://coral-app-4mdso.ondigitalocean.app/api/auth/login"})
@RequestMapping("/api/auth")
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private UserRepository userRepository;

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User user) {
		logger.info("Registration attempt for username: {}", user.getUsername());

		Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
		if (existingUser.isPresent()) {
			logger.warn("Registration failed - Username already exists: {}", user.getUsername());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
		}

		String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(hashedPassword);

		userRepository.save(user);
		logger.info("User registered successfully: {}", user.getUsername());

		return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
	}

	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody User loginRequest) {
		logger.info("Login attempt for username: {}", loginRequest.getUsername());

		Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());
		if (userOptional.isEmpty()) {
			logger.warn("Login failed - Username not found: {}", loginRequest.getUsername());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
		}

		User user = userOptional.get();

		if (!BCrypt.checkpw(loginRequest.getPassword(), user.getPassword())) {
			logger.warn("Login failed - Invalid password for username: {}", loginRequest.getUsername());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
		}

		if (user.getRole() != Role.MANAGER && user.getRole() != Role.RECEPTION) {
			logger.warn("Login denied - Unauthorized role: {} for username: {}", user.getRole(),
					loginRequest.getUsername());
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied for this role");
		}

		logger.info("Login successful for username: {} with role: {}", user.getUsername(), user.getRole());
		return ResponseEntity.ok("Login successful as " + user.getRole());
	}
}

//package com.kabaso.lodge_management_app_v3.controller;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCrypt;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.kabaso.lodge_management_app_v3.entity.Role;
//import com.kabaso.lodge_management_app_v3.entity.User;
//import com.kabaso.lodge_management_app_v3.repo.UserRepository;
//
//@RestController
//@CrossOrigin(origins = "http://localhost:3000")
//@RequestMapping("/api/auth")
//public class AuthController {
//
//	@Autowired
//	private UserRepository userRepository;
//
//	// @Autowired private PasswordEncoder passwordEncoder;
//
//	// @Autowired private JwtService jwtService;
//
//	@PostMapping("/register")
//	public ResponseEntity<String> registerUser(@RequestBody User user) {
//		Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
//
//		if (existingUser.isPresent()) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
//		}
//
//		// Optionally hash password before saving
//		// user.setPassword(passwordEncoder.encode(user.getPassword()));
//		// String hashed = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
//		// boolean matched = BCrypt.checkpw(enteredPassword, hashed);
//		// Hash the password manually using BCrypt
//		String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
//		user.setPassword(hashedPassword);
//
//		userRepository.save(user);
//		// userRepository.save(user);
//		return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
//	}
//	
//	@PostMapping("/login")
//
//	public ResponseEntity<String> loginUser(@RequestBody User loginRequest) {
//		Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());
//
//		if (userOptional.isEmpty()) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
//		}
//
//		User user = userOptional.get();
//
//		if (!BCrypt.checkpw(loginRequest.getPassword(), user.getPassword())) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
//		}
//
//		// Role-based restriction: only allow MANAGER and RECEPTION
//		if (user.getRole() != Role.MANAGER && user.getRole() != Role.RECEPTION) {
//			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied for this role");
//		}
//
//		// Optionally return user info (see DTO below)
//		return ResponseEntity.ok("Login successful as " + user.getRole());
//	}
//}

// @PostMapping("/login")
// public ResponseEntity<?> login(@RequestBody LoginRequest login) {
/*
 * var user = userRepository.findByUsername(login.getUsername()).orElseThrow();
 * if (passwordEncoder.matches(login.getPassword(), user.getPassword())) {
 * String token = jwtService.generateToken(user); return
 * ResponseEntity.ok(Map.of("token", token, "role", user.getRole())); } return
 * ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); }
 */

/*
 * public ResponseEntity<String> loginUser(@RequestBody User loginRequest) {
 * Optional<User> userOptional =
 * userRepository.findByUsername(loginRequest.getUsername());
 * 
 * if (userOptional.isEmpty()) { return
 * ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username"); }
 * 
 * User user = userOptional.get();
 */

/*
 * if (!user.getPassword().equals(loginRequest.getPassword())) { return
 * ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password"); }
 */

// Compare raw password with hashed one
/*
 * if (!BCrypt.checkpw(loginRequest.getPassword(), user.getPassword())) { return
 * ResponseEntity.status(HttpStatus.UNAUTHORIZED).
 * body("Invalid username or password"); } // You could generate a dummy token
 * or return basic info return ResponseEntity.ok("Login successful"); }
 */
/*
 * if (!BCrypt.checkpw(loginRequest.getPassword(), user.getPassword())) { return
 * ResponseEntity.status(HttpStatus.UNAUTHORIZED).
 * body("Invalid username or password"); }
 * 
 * if (user.getRole() != Role.MANAGER && user.getRole() != Role.RECEPTION) {
 * return ResponseEntity.status(HttpStatus.FORBIDDEN).
 * body("Access denied for this role"); }
 * 
 * UserDto responseDto = new UserDto(user.getUsername(), user.getRole()); return
 * ResponseEntity.ok(responseDto); }
 */

/*
 * Optional<User> userOptional =
 * userRepository.findByUsername(loginRequest.getUsername());
 * 
 * if (userOptional.isEmpty()) { return
 * ResponseEntity.status(HttpStatus.UNAUTHORIZED).
 * body("Invalid username or password"); }
 * 
 * User user = userOptional.get();
 * 
 * if (!BCrypt.checkpw(loginRequest.getPassword(), user.getPassword())) { return
 * ResponseEntity.status(HttpStatus.UNAUTHORIZED).
 * body("Invalid username or password"); }
 * 
 * if (user.getRole() != Role.MANAGER && user.getRole() != Role.RECEPTION) {
 * return ResponseEntity.status(HttpStatus.FORBIDDEN).
 * body("Access denied for this role"); }
 * 
 * UserDto responseDto = new UserDto(user.getUsername(), user.getRole()); return
 * ResponseEntity.ok(responseDto); }
 */
//}
