package com.nsaasystems.inventory_sales_management_system.controller;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nsaasystems.inventory_sales_management_system.entity.Item;
import com.nsaasystems.inventory_sales_management_system.service.ItemService;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "*")
public class ItemController {

	private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

	@Autowired
	private ItemService itemService;

	@GetMapping
	public List<Item> getAllItems() {
		logger.info("GET /api/items called");
		return itemService.getAllItems();
	}

	@GetMapping("/{id}")
	public Item getItem(@PathVariable Long id) {
		logger.info("GET /api/items/{} called", id);
		return itemService.getItemById(id);
	}

	@PostMapping
	public Item createItem(@RequestBody Item item) {
		logger.info("POST /api/items called with name={}, category={}, imageName={}", item.getName(),
				item.getCategory(), item.getImageName());

		// ✅ Ensure default image if none is provided
		if (item.getImageName() == null || item.getImageName().isEmpty()) {
			item.setImageName("default.jpg");
		}

		return itemService.createItem(item);
	}

	@PutMapping("/{id}")
	public Item updateItem(@PathVariable Long id, @RequestBody Item itemDetails) {
		logger.info("PUT /api/items/{} called", id);

		return itemService.updateItem(id, itemDetails);
	}

	@PutMapping("/{id}/price")
	public Item updatePrice(@PathVariable Long id, @RequestParam BigDecimal newPrice) {
		logger.info("PUT /api/items/{}/price called with newPrice={}", id, newPrice);
		return itemService.updateSellingPrice(id, newPrice);
	}

	@PutMapping("/{id}/stock")
	public Item addStock(@PathVariable Long id, @RequestParam int additionalQuantity) {
		logger.info("PUT /api/items/{}/stock called with additionalQuantity={}", id, additionalQuantity);
		return itemService.addStock(id, additionalQuantity);
	}

	@GetMapping("/category/{category}")
	public List<Item> findByCategory(@PathVariable String category) {
		logger.info("GET /api/items/category/{} called", category);
		return itemService.findByCategory(category);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
		logger.info("DELETE /api/items/{} called", id);
		if (itemService.existsById(id)) {
			itemService.deleteById(id);
			return ResponseEntity.noContent().build(); // 204 No Content
		} else {
			logger.warn("Item with id={} not found, cannot delete", id);
			return ResponseEntity.notFound().build(); // 404 Not Found
		}
	}

	// ❌ Removed: upload/update image endpoint
	// We no longer handle MultipartFile uploads
}

//###############################Working one ###################
//package com.newdaylodgeandapartments.inventory_sales_management_system.controller;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.newdaylodgeandapartments.inventory_sales_management_system.entity.Item;
//import com.newdaylodgeandapartments.inventory_sales_management_system.service.ItemService;
//
//@RestController
//@RequestMapping("/api/items")
//@CrossOrigin(origins = "*")
//public class ItemController {
//
//	private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
//	private static final String UPLOAD_DIR = "uploads/items/";
//
//	@Autowired
//	private ItemService itemService;
//
//	@GetMapping
//	public List<Item> getAllItems() {
//		logger.info("GET /api/items called");
//		return itemService.getAllItems();
//	}
//
//	@GetMapping("/{id}")
//	public Item getItem(@PathVariable Long id) {
//		logger.info("GET /api/items/{} called", id);
//		return itemService.getItemById(id);
//	}
//
//	@PostMapping
//	public Item createItem(@RequestBody Item item) {
//		logger.info("POST /api/items called with name={}, category={}", item.getName(), item.getCategory());
//		return itemService.createItem(item);
//	}
//
//	@PutMapping("/{id}/price")
//	public Item updatePrice(@PathVariable Long id, @RequestParam BigDecimal newPrice) {
//		logger.info("PUT /api/items/{}/price called with newPrice={}", id, newPrice);
//		return itemService.updateSellingPrice(id, newPrice);
//	}
//
//	@PutMapping("/{id}/stock")
//	public Item addStock(@PathVariable Long id, @RequestParam int additionalQuantity) {
//		logger.info("PUT /api/items/{}/stock called with additionalQuantity={}", id, additionalQuantity);
//		return itemService.addStock(id, additionalQuantity);
//	}
//
//	@GetMapping("/category/{category}")
//	public List<Item> findByCategory(@PathVariable String category) {
//		logger.info("GET /api/items/category/{} called", category);
//		return itemService.findByCategory(category);
//	}
//
//	@DeleteMapping("/{id}")
//	public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
//		logger.info("DELETE /api/items/{} called", id);
//		if (itemService.existsById(id)) {
//			itemService.deleteById(id);
//			return ResponseEntity.noContent().build(); // 204 No Content
//		} else {
//			logger.warn("Item with id={} not found, cannot delete", id);
//			return ResponseEntity.notFound().build(); // 404 Not Found
//		}
//	}
//
//	// ✅ Upload/Update item image
//	@PutMapping("/{id}/image")
//	public Item updateImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
//		logger.info("PUT /api/items/{}/image called with file={}", id, file.getOriginalFilename());
//		return itemService.updateImage(id, file);
//	}
//
//	// ✅ New endpoint: Serve the image file
//	@GetMapping("/{id}/image-file")
//	public ResponseEntity<byte[]> getItemImage(@PathVariable Long id) {
//		Item item = itemService.getItemById(id);
//
//		if (item.getImageName() == null) {
//			logger.warn("No image found for item id={}", id);
//			return ResponseEntity.notFound().build();
//		}
//
//		try {
//			Path imagePath = Paths.get(UPLOAD_DIR).resolve(item.getImageName());
//			if (!Files.exists(imagePath)) {
//				logger.error("Image file not found at {}", imagePath);
//				return ResponseEntity.notFound().build();
//			}
//
//			byte[] imageBytes = Files.readAllBytes(imagePath);
//
//			return ResponseEntity.ok().header("Content-Type", Files.probeContentType(imagePath)).body(imageBytes);
//
//		} catch (IOException e) {
//			logger.error("Failed to load image for item {}", id, e);
//			return ResponseEntity.internalServerError().build();
//		}
//	}
//}

//package com.newdaylodgeandapartments.inventory_sales_management_system.controller;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.newdaylodgeandapartments.inventory_sales_management_system.entity.Item;
//import com.newdaylodgeandapartments.inventory_sales_management_system.service.ItemService;
//
//@RestController
//@RequestMapping("/api/items")
//@CrossOrigin(origins = "*")
//public class ItemController {
//
//	private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
//
//	@Autowired
//	private ItemService itemService;
//
//	@GetMapping
//	public List<Item> getAllItems() {
//		logger.info("GET /api/items called");
//		return itemService.getAllItems();
//	}
//
//	@GetMapping("/{id}")
//	public Item getItem(@PathVariable Long id) {
//		logger.info("GET /api/items/{} called", id);
//		return itemService.getItemById(id);
//	}
//
//	@PostMapping
//	public Item createItem(@RequestBody Item item) {
//		logger.info("POST /api/items called with name={}, category={}", item.getName(), item.getCategory());
//		return itemService.createItem(item);
//	}
//
//	@PutMapping("/{id}/price")
//	public Item updatePrice(@PathVariable Long id, @RequestParam BigDecimal newPrice) {
//		logger.info("PUT /api/items/{}/price called with newPrice={}", id, newPrice);
//		return itemService.updateSellingPrice(id, newPrice);
//	}
//
//	@PutMapping("/{id}/stock")
//	public Item addStock(@PathVariable Long id, @RequestParam int additionalQuantity) {
//		logger.info("PUT /api/items/{}/stock called with additionalQuantity={}", id, additionalQuantity);
//		return itemService.addStock(id, additionalQuantity);
//	}
//
//	@GetMapping("/category/{category}")
//	public List<Item> findByCategory(@PathVariable String category) {
//		logger.info("GET /api/items/category/{} called", category);
//		return itemService.findByCategory(category);
//	}
//
//	@DeleteMapping("/{id}")
//	public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
//		logger.info("DELETE /api/items/{} called", id);
//		if (itemService.existsById(id)) {
//			itemService.deleteById(id);
//			return ResponseEntity.noContent().build(); // 204 No Content
//		} else {
//			logger.warn("Item with id={} not found, cannot delete", id);
//			return ResponseEntity.notFound().build(); // 404 Not Found
//		}
//	}
//
//	// ✅ New endpoint: Upload/Update item image
//	@PutMapping("/{id}/image")
//	public Item updateImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
//		logger.info("PUT /api/items/{}/image called with file={}", id, file.getOriginalFilename());
//		return itemService.updateImage(id, file);
//	}
//}

//package com.newdaylodgeandapartments.inventory_sales_management_system.controller;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.newdaylodgeandapartments.inventory_sales_management_system.entity.Item;
//import com.newdaylodgeandapartments.inventory_sales_management_system.service.ItemService;
//
//@RestController
//@RequestMapping("/api/items")
//@CrossOrigin(origins = "*")
//public class ItemController {
//
//	private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
//
//	private static final String UPLOAD_DIR = "uploads/images/"; // storage directory
//
//	@Autowired
//	private ItemService itemService;
//
//	@GetMapping
//	public List<Item> getAllItems() {
//		logger.info("GET /api/items called");
//		return itemService.getAllItems();
//	}
//
//	@GetMapping("/{id}")
//	public Item getItem(@PathVariable Long id) {
//		logger.info("GET /api/items/{} called", id);
//		return itemService.getItemById(id);
//	}
//
//	@PostMapping
//	public Item createItem(@RequestBody Item item) {
//		logger.info("POST /api/items called with name={}, category={}", item.getName(), item.getCategory());
//		return itemService.createItem(item);
//	}
//
//	@PostMapping("/upload")
//	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
//		logger.info("POST /api/items/upload called with file={}", file.getOriginalFilename());
//		try {
//			Files.createDirectories(Paths.get(UPLOAD_DIR));
//			String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
//			Path filePath = Paths.get(UPLOAD_DIR, fileName);
//			Files.write(filePath, file.getBytes());
//			logger.info("File uploaded successfully: {}", fileName);
//			return ResponseEntity.ok(fileName);
//		} catch (IOException e) {
//			logger.error("Error uploading file: {}", e.getMessage(), e);
//			return ResponseEntity.status(500).body("Upload failed");
//		}
//	}
//
//	@PutMapping("/{id}/image")
//	public Item updateImage(@PathVariable Long id, @RequestParam("imageName") String imageName) {
//		logger.info("PUT /api/items/{}/image called with imageName={}", id, imageName);
//		return itemService.updateImageName(id, imageName);
//	}
//
//	@PutMapping("/{id}/price")
//	public Item updatePrice(@PathVariable Long id, @RequestParam BigDecimal newPrice) {
//		logger.info("PUT /api/items/{}/price called with newPrice={}", id, newPrice);
//		return itemService.updateSellingPrice(id, newPrice);
//	}
//
//	@PutMapping("/{id}/stock")
//	public Item addStock(@PathVariable Long id, @RequestParam int additionalQuantity) {
//		logger.info("PUT /api/items/{}/stock called with additionalQuantity={}", id, additionalQuantity);
//		return itemService.addStock(id, additionalQuantity);
//	}
//
//	@GetMapping("/category/{category}")
//	public List<Item> findByCategory(@PathVariable String category) {
//		logger.info("GET /api/items/category/{} called", category);
//		return itemService.findByCategory(category);
//	}
//
//	@DeleteMapping("/{id}")
//	public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
//		logger.info("DELETE /api/items/{} called", id);
//		if (itemService.existsById(id)) {
//			itemService.deleteById(id);
//			logger.info("Item with id={} deleted successfully", id);
//			return ResponseEntity.noContent().build(); // 204 No Content
//		} else {
//			logger.warn("Item with id={} not found, cannot delete", id);
//			return ResponseEntity.notFound().build(); // 404 Not Found
//		}
//
//	@PutMapping("/{id}/image")
//	public Item updateImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
//		logger.info("PUT /api/items/{}/image called with file={}", id, file.getOriginalFilename());
//		return itemService.updateImage(id, file);
//	}
//
//}

//package com.newdaylodgeandapartments.inventory_sales_management_system.controller;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.newdaylodgeandapartments.inventory_sales_management_system.entity.Item;
//import com.newdaylodgeandapartments.inventory_sales_management_system.service.ItemService;
//
//@RestController
//@RequestMapping("/api/items")
//@CrossOrigin(origins = "*")
//public class ItemController {
//
//	private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
//
//	@Autowired
//	private ItemService itemService;
//
//	@GetMapping
//	public List<Item> getAllItems() {
//		logger.info("GET /api/items called");
//		return itemService.getAllItems();
//	}
//
//	@GetMapping("/{id}")
//	public Item getItem(@PathVariable Long id) {
//		logger.info("GET /api/items/{} called", id);
//		return itemService.getItemById(id);
//	}
//
//	@PostMapping
//	public Item createItem(@RequestBody Item item) {
//		logger.info("POST /api/items called with name={}, category={}", item.getName(), item.getCategory());
//		return itemService.createItem(item);
//	}
//
//	@PutMapping("/{id}/price")
//	public Item updatePrice(@PathVariable Long id, @RequestParam BigDecimal newPrice) {
//		logger.info("PUT /api/items/{}/price called with newPrice={}", id, newPrice);
//		return itemService.updateSellingPrice(id, newPrice);
//	}
//
//	@PutMapping("/{id}/stock")
//	public Item addStock(@PathVariable Long id, @RequestParam int additionalQuantity) {
//		logger.info("PUT /api/items/{}/stock called with additionalQuantity={}", id, additionalQuantity);
//		return itemService.addStock(id, additionalQuantity);
//	}
//
//	@GetMapping("/category/{category}")
//	public List<Item> findByCategory(@PathVariable String category) {
//		logger.info("GET /api/items/category/{} called", category);
//		return itemService.findByCategory(category);
//	}
//
//	@DeleteMapping("/{id}")
//	public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
//		logger.info("DELETE /api/items/{} called", id);
//		if (itemService.existsById(id)) {
//			itemService.deleteById(id);
//			return ResponseEntity.noContent().build(); // 204 No Content
//		} else {
//			logger.warn("Item with id={} not found, cannot delete", id);
//			return ResponseEntity.notFound().build(); // 404 Not Found
//		}
//	}
//}

//import java.math.BigDecimal;

//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.newdaylodgeandapartments.inventory_sales_management_system.entity.Item;
//import com.newdaylodgeandapartments.inventory_sales_management_system.service.ItemService;
//
//@RestController
//@RequestMapping("/api/items")
//public class ItemController {
//	@Autowired
//	private ItemService itemService;
//
//	@PostMapping
//	public Item create(@RequestBody Item item) {
//		return itemService.save(item);
//	}
//
//	@GetMapping
//	public List<Item> getAll() {
//		return itemService.getAll();
//	}
//
//	@PutMapping("/{id}/stock")
//	public Item addStock(@PathVariable Long id, @RequestParam int additionalQuantity) {
//		return itemService.addStock(id, additionalQuantity);
//	}
//
//	@PutMapping("/{id}/price")
//	public Item updatePrice(@PathVariable Long id, @RequestParam BigDecimal newPrice) {
//		return itemService.updateSellingPrice(id, newPrice);
//	}
//}

//import java.math.BigDecimal;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.newdaylodgeandapartments.inventory_sales_management_system.entity.Item;
//import com.newdaylodgeandapartments.inventory_sales_management_system.service.ItemService;
//
//@RestController
//@RequestMapping("/api/items")
//@CrossOrigin(origins = "*")
//public class ItemController {
//
//	@Autowired
//	private ItemService itemService;
//
//	@GetMapping
//	public List<Item> getAllItems() {
//		return itemService.getAllItems();
//	}
//
//	@GetMapping("/{id}")
//	public Item getItem(@PathVariable Long id) {
//		return itemService.getItemById(id);
//	}
//
//	@PostMapping
//	public Item createItem(@RequestBody Item item) {
//		return itemService.createItem(item);
//	}
//
//	@PutMapping("/{id}/price")
//	public Item updatePrice(@PathVariable Long id, @RequestParam BigDecimal newPrice) {
//		return itemService.updateSellingPrice(id, newPrice);
//	}
//
//	@PutMapping("/{id}/stock")
//	public Item addStock(@PathVariable Long id, @RequestParam int additionalQuantity) {
//		return itemService.addStock(id, additionalQuantity);
//	}
//
//	@GetMapping("/category/{category}")
//	public List<Item> findByCategory(@PathVariable String category) {
//		return itemService.findByCategory(category);
//	}
//	@DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
//        if (itemService.existsById(id)) { // you may need to add this method in service/repo
//            itemService.deleteById(id);   // you may need to add this method in service/repo
//            return ResponseEntity.noContent().build(); // 204 No Content
//        } else {
//            return ResponseEntity.notFound().build(); // 404 Not Found
//        }
//    }
//
//
//}
