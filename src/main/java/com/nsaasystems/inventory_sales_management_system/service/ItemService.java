package com.nsaasystems.inventory_sales_management_system.service;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nsaasystems.inventory_sales_management_system.entity.Item;
import com.nsaasystems.inventory_sales_management_system.exception.ItemNotFoundException;
import com.nsaasystems.inventory_sales_management_system.repo.ItemRepository;

@Service
public class ItemService {

	private static final Logger log = LoggerFactory.getLogger(ItemService.class);

	@Autowired
	private ItemRepository itemRepository;

	public List<Item> getAllItems() {
		log.info("Fetching all items from inventory");
		return itemRepository.findAll();
	}

	public Item getItemById(Long id) {
		log.debug("Looking for item with id={}", id);
		return itemRepository.findById(id).orElseThrow(() -> {
			log.error("Item with id={} not found", id);
			return new ItemNotFoundException("Item not found with id: " + id);
		});
	}

	public Item createItem(Item item) {
		log.info("Creating new item: {}", item.getName());

		// ✅ Fallback for image
		if (item.getImageName() == null || item.getImageName().isEmpty()) {
			item.setImageName("default.jpg");
		}

		return itemRepository.save(item);
	}

	public Item updateItem(Long id, Item itemDetails) {
		log.info("Updating item with id={}", id);
		Item item = getItemById(id);

		item.setName(itemDetails.getName());
		item.setCategory(itemDetails.getCategory());
		item.setBrand(itemDetails.getBrand());
		item.setCostPrice(itemDetails.getCostPrice());
		item.setSellingPrice(itemDetails.getSellingPrice());
		item.setQuantityInStock(itemDetails.getQuantityInStock());

		// ✅ If frontend sends an imageName, use it, else keep current or fallback
		if (itemDetails.getImageName() != null && !itemDetails.getImageName().isEmpty()) {
			item.setImageName(itemDetails.getImageName());
		} else if (item.getImageName() == null || item.getImageName().isEmpty()) {
			item.setImageName("default.jpg");
		}

		return itemRepository.save(item);
	}

	public Item updateSellingPrice(Long id, BigDecimal newPrice) {
		log.info("Updating selling price for item id={} to {}", id, newPrice);
		Item item = getItemById(id);
		item.setSellingPrice(newPrice);
		return itemRepository.save(item);
	}

	public Item addStock(Long id, int additionalQuantity) {
		log.info("Adding stock for item id={} by {}", id, additionalQuantity);
		Item item = getItemById(id);
		item.setQuantityInStock(item.getQuantityInStock() + additionalQuantity);
		return itemRepository.save(item);
	}

	public List<Item> findByCategory(String category) {
		log.info("Fetching items by category={}", category);
		return itemRepository.findByCategory(category);
	}

	public boolean existsById(Long id) {
		log.debug("Checking if item with id={} exists", id);
		return itemRepository.existsById(id);
	}

	public void deleteById(Long id) {
		log.warn("Deleting item with id={}", id);
		Item item = getItemById(id); // throws if not found
		itemRepository.deleteById(id);
		log.info("Item with id={} deleted successfully", id);
	}
}

//package com.newdaylodgeandapartments.inventory_sales_management_system.service;
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
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.newdaylodgeandapartments.inventory_sales_management_system.entity.Item;
//import com.newdaylodgeandapartments.inventory_sales_management_system.exception.ItemNotFoundException;
//import com.newdaylodgeandapartments.inventory_sales_management_system.repo.ItemRepository;
//
//@Service
//public class ItemService {
//
//	private static final Logger log = LoggerFactory.getLogger(ItemService.class);
//
//	private static final String UPLOAD_DIR = "uploads/items/";
//
//	@Autowired
//	private ItemRepository itemRepository;
//
//	public List<Item> getAllItems() {
//		log.info("Fetching all items from inventory");
//		return itemRepository.findAll();
//	}
//
//	public Item getItemById(Long id) {
//		log.debug("Looking for item with id={}", id);
//		return itemRepository.findById(id).orElseThrow(() -> {
//			log.error("Item with id={} not found", id);
//			return new ItemNotFoundException("Item not found with id: " + id);
//		});
//	}
//
//	public Item createItem(Item item) {
//		log.info("Creating new item: {}", item.getName());
//		return itemRepository.save(item);
//	}
//
//	public Item updateSellingPrice(Long id, BigDecimal newPrice) {
//		log.info("Updating selling price for item id={} to {}", id, newPrice);
//		Item item = getItemById(id);
//		item.setSellingPrice(newPrice);
//		return itemRepository.save(item);
//	}
//
//	public Item addStock(Long id, int additionalQuantity) {
//		log.info("Adding stock for item id={} by {}", id, additionalQuantity);
//		Item item = getItemById(id);
//		item.setQuantityInStock(item.getQuantityInStock() + additionalQuantity);
//		return itemRepository.save(item);
//	}
//
//	public List<Item> findByCategory(String category) {
//		log.info("Fetching items by category={}", category);
//		return itemRepository.findByCategory(category);
//	}
//
//	public boolean existsById(Long id) {
//		log.debug("Checking if item with id={} exists", id);
//		return itemRepository.existsById(id);
//	}
//
//	public void deleteById(Long id) {
//		log.warn("Deleting item with id={}", id);
//		Item item = getItemById(id); // throws if not found
//
//		// ✅ Delete image file if it exists
//		if (item.getImageName() != null) {
//			Path imagePath = Paths.get(UPLOAD_DIR).resolve(item.getImageName());
//			try {
//				Files.deleteIfExists(imagePath);
//				log.info("Deleted image file for item id={} at {}", id, imagePath);
//			} catch (IOException e) {
//				log.error("Failed to delete image file for item id={} at {}", id, imagePath, e);
//			}
//		}
//
//		itemRepository.deleteById(id);
//		log.info("Item with id={} deleted successfully", id);
//	}
//
//	// ✅ Save new image and remove old one if exists
//	public Item updateImage(Long id, MultipartFile file) {
//		log.info("Updating image for item id={}", id);
//
//		Item item = getItemById(id);
//
//		try {
//			// Ensure directory exists
//			Path uploadPath = Paths.get(UPLOAD_DIR);
//			Files.createDirectories(uploadPath);
//
//			// ✅ Delete old image if exists
//			if (item.getImageName() != null) {
//				Path oldImagePath = uploadPath.resolve(item.getImageName());
//				try {
//					Files.deleteIfExists(oldImagePath);
//					log.info("Deleted old image for item id={} at {}", id, oldImagePath);
//				} catch (IOException e) {
//					log.warn("Could not delete old image for item id={} at {}", id, oldImagePath, e);
//				}
//			}
//
//			// Build unique filename
//			String fileName = id + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
//			Path filePath = uploadPath.resolve(fileName);
//
//			// Save the new file
//			Files.write(filePath, file.getBytes());
//
//			// Update DB with new image name
//			item.setImageName(fileName);
//			Item saved = itemRepository.save(item);
//
//			log.info("Image for item id={} saved successfully at {}", id, filePath);
//			return saved;
//
//		} catch (IOException e) {
//			log.error("Failed to store image for item id={}", id, e);
//			throw new RuntimeException("Image upload failed", e);
//		}
//	}
//}

//package com.newdaylodgeandapartments.inventory_sales_management_system.service;
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
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.newdaylodgeandapartments.inventory_sales_management_system.entity.Item;
//import com.newdaylodgeandapartments.inventory_sales_management_system.exception.ItemNotFoundException;
//import com.newdaylodgeandapartments.inventory_sales_management_system.repo.ItemRepository;
//
//@Service
//public class ItemService {
//
//	private static final Logger log = LoggerFactory.getLogger(ItemService.class);
//
//	private static final String UPLOAD_DIR = "uploads/items/";
//
//	@Autowired
//	private ItemRepository itemRepository;
//
//	public List<Item> getAllItems() {
//		log.info("Fetching all items from inventory");
//		return itemRepository.findAll();
//	}
//
//	public Item getItemById(Long id) {
//		log.debug("Looking for item with id={}", id);
//		return itemRepository.findById(id).orElseThrow(() -> {
//			log.error("Item with id={} not found", id);
//			return new ItemNotFoundException("Item not found with id: " + id);
//		});
//	}
//
//	public Item createItem(Item item) {
//		log.info("Creating new item: {}", item.getName());
//		return itemRepository.save(item);
//	}
//
//	public Item updateSellingPrice(Long id, BigDecimal newPrice) {
//		log.info("Updating selling price for item id={} to {}", id, newPrice);
//		Item item = getItemById(id);
//		item.setSellingPrice(newPrice);
//		return itemRepository.save(item);
//	}
//
//	public Item addStock(Long id, int additionalQuantity) {
//		log.info("Adding stock for item id={} by {}", id, additionalQuantity);
//		Item item = getItemById(id);
//		item.setQuantityInStock(item.getQuantityInStock() + additionalQuantity);
//		return itemRepository.save(item);
//	}
//
//	public List<Item> findByCategory(String category) {
//		log.info("Fetching items by category={}", category);
//		return itemRepository.findByCategory(category);
//	}
//
//	public boolean existsById(Long id) {
//		log.debug("Checking if item with id={} exists", id);
//		return itemRepository.existsById(id);
//	}
//
//	public void deleteById(Long id) {
//		log.warn("Deleting item with id={}", id);
//		Item item = getItemById(id); // throws if not found
//
//		// ✅ Delete image file from filesystem if it exists
//		if (item.getImageName() != null) {
//			Path imagePath = Paths.get(UPLOAD_DIR).resolve(item.getImageName());
//			try {
//				Files.deleteIfExists(imagePath);
//				log.info("Deleted image file for item id={} at {}", id, imagePath);
//			} catch (IOException e) {
//				log.error("Failed to delete image file for item id={} at {}", id, imagePath, e);
//			}
//		}
//
//		itemRepository.deleteById(id);
//		log.info("Item with id={} deleted successfully", id);
//	}
//
//	// ✅ Save image to filesystem and record path in DB
//	public Item updateImage(Long id, MultipartFile file) {
//		log.info("Updating image for item id={}", id);
//
//		Item item = getItemById(id);
//
//		try {
//			// Ensure directory exists
//			Path uploadPath = Paths.get(UPLOAD_DIR);
//			Files.createDirectories(uploadPath);
//
//			// Build unique filename
//			String fileName = id + "_" + file.getOriginalFilename();
//			Path filePath = uploadPath.resolve(fileName);
//
//			// Save the file
//			Files.write(filePath, file.getBytes());
//
//			// Save file name in DB
//			item.setImageName(fileName);
//			Item saved = itemRepository.save(item);
//
//			log.info("Image for item id={} saved successfully at {}", id, filePath);
//			return saved;
//
//		} catch (IOException e) {
//			log.error("Failed to store image for item id={}", id, e);
//			throw new RuntimeException("Image upload failed", e);
//		}
//	}
//}

//package com.newdaylodgeandapartments.inventory_sales_management_system.service;
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
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.newdaylodgeandapartments.inventory_sales_management_system.entity.Item;
//import com.newdaylodgeandapartments.inventory_sales_management_system.exception.ItemNotFoundException;
//import com.newdaylodgeandapartments.inventory_sales_management_system.repo.ItemRepository;
//
//@Service
//public class ItemService {
//
//	private static final Logger log = LoggerFactory.getLogger(ItemService.class);
//
//	private static final String UPLOAD_DIR = "uploads/items/";
//
//	@Autowired
//	private ItemRepository itemRepository;
//
//	public List<Item> getAllItems() {
//		log.info("Fetching all items from inventory");
//		return itemRepository.findAll();
//	}
//
//	public Item getItemById(Long id) {
//		log.debug("Looking for item with id={}", id);
//		return itemRepository.findById(id).orElseThrow(() -> {
//			log.error("Item with id={} not found", id);
//			return new ItemNotFoundException("Item not found with id: " + id);
//		});
//	}
//
//	public Item createItem(Item item) {
//		log.info("Creating new item: {}", item.getName());
//		return itemRepository.save(item);
//	}
//
//	public Item updateSellingPrice(Long id, BigDecimal newPrice) {
//		log.info("Updating selling price for item id={} to {}", id, newPrice);
//		Item item = getItemById(id);
//		item.setSellingPrice(newPrice);
//		return itemRepository.save(item);
//	}
//
//	public Item addStock(Long id, int additionalQuantity) {
//		log.info("Adding stock for item id={} by {}", id, additionalQuantity);
//		Item item = getItemById(id);
//		item.setQuantityInStock(item.getQuantityInStock() + additionalQuantity);
//		return itemRepository.save(item);
//	}
//
//	public List<Item> findByCategory(String category) {
//		log.info("Fetching items by category={}", category);
//		return itemRepository.findByCategory(category);
//	}
//
//	public boolean existsById(Long id) {
//		log.debug("Checking if item with id={} exists", id);
//		return itemRepository.existsById(id);
//	}
//
//	public void deleteById(Long id) {
//		log.warn("Deleting item with id={}", id);
//		if (!itemRepository.existsById(id)) {
//			log.error("Failed to delete. Item with id={} not found", id);
//			throw new ItemNotFoundException("Item not found with id: " + id);
//		}
//		itemRepository.deleteById(id);
//		log.info("Item with id={} deleted successfully", id);
//	}
//
//	// ✅ New: save image to filesystem and record path in DB
//	public Item updateImage(Long id, MultipartFile file) {
//		log.info("Updating image for item id={}", id);
//
//		Item item = getItemById(id);
//
//		try {
//			// Ensure directory exists
//			Path uploadPath = Paths.get(UPLOAD_DIR);
//			Files.createDirectories(uploadPath);
//
//			// Build unique filename
//			String fileName = id + "_" + file.getOriginalFilename();
//			Path filePath = uploadPath.resolve(fileName);
//
//			// Save the file
//			Files.write(filePath, file.getBytes());
//
//			// Save file name (or path) in DB
//			item.setImageName(fileName);
//			Item saved = itemRepository.save(item);
//
//			log.info("Image for item id={} saved successfully at {}", id, filePath);
//			return saved;
//
//		} catch (IOException e) {
//			log.error("Failed to store image for item id={}", id, e);
//			throw new RuntimeException("Image upload failed", e);
//		}
//	}
//}

//package com.newdaylodgeandapartments.inventory_sales_management_system.service;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.newdaylodgeandapartments.inventory_sales_management_system.entity.Item;
//import com.newdaylodgeandapartments.inventory_sales_management_system.exception.ItemNotFoundException;
//import com.newdaylodgeandapartments.inventory_sales_management_system.repo.ItemRepository;
//
//@Service
//public class ItemService {
//
//	private static final Logger log = LoggerFactory.getLogger(ItemService.class);
//
//	@Autowired
//	private ItemRepository itemRepository;
//
//	public List<Item> getAllItems() {
//		log.info("Fetching all items from inventory");
//		return itemRepository.findAll();
//	}
//
//	public Item getItemById(Long id) {
//		log.debug("Looking for item with id={}", id);
//		return itemRepository.findById(id).orElseThrow(() -> {
//			log.error("Item with id={} not found", id);
//			return new ItemNotFoundException("Item not found with id: " + id);
//		});
//	}
//
//	public Item createItem(Item item) {
//		log.info("Creating new item: {}", item.getName());
//		return itemRepository.save(item);
//	}
//
//	public Item updateSellingPrice(Long id, BigDecimal newPrice) {
//		log.info("Updating selling price for item id={} to {}", id, newPrice);
//		Item item = getItemById(id);
//		item.setSellingPrice(newPrice);
//		return itemRepository.save(item);
//	}
//
//	public Item addStock(Long id, int additionalQuantity) {
//		log.info("Adding stock for item id={} by {}", id, additionalQuantity);
//		Item item = getItemById(id);
//		item.setQuantityInStock(item.getQuantityInStock() + additionalQuantity);
//		return itemRepository.save(item);
//	}
//
//	public Item updateImageName(Long id, String imageName) {
//		log.info("Updating image for item id={} with imageName={}", id, imageName);
//		Item item = getItemById(id);
//		item.setImageName(imageName); // ✅ assumes Item entity has `private String imageName;`
//		return itemRepository.save(item);
//	}
//
//	public List<Item> findByCategory(String category) {
//		log.info("Fetching items by category={}", category);
//		return itemRepository.findByCategory(category);
//	}
//
////	public boolean existsById(Long id) {
////		log.debug("Checking if item with id={} exists", id);
////		return itemRepository.existsById(id);
////	}
////
////	public void deleteById(Long id) {
////		log.warn("Deleting item
////
//	public boolean existsById(Long id) {
//		log.debug("Checking if item with id={} exists", id);
//		return itemRepository.existsById(id);
//	}
//
//	public void deleteById(Long id) {
//		log.warn("Deleting item with id={}", id);
//		if (!itemRepository.existsById(id)) {
//			log.error("Failed to delete. Item with id={} not found", id);
//			throw new ItemNotFoundException("Item not found with id: " + id);
//		}
//		itemRepository.deleteById(id);
//		log.info("Item with id={} deleted successfully", id);
//	}
//}

//package com.newdaylodgeandapartments.inventory_sales_management_system.service;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.newdaylodgeandapartments.inventory_sales_management_system.entity.Item;
//import com.newdaylodgeandapartments.inventory_sales_management_system.exception.ItemNotFoundException;
//import com.newdaylodgeandapartments.inventory_sales_management_system.repo.ItemRepository;
//
//@Service
//public class ItemService {
//
//	private static final Logger log = LoggerFactory.getLogger(ItemService.class);
//
//	@Autowired
//	private ItemRepository itemRepository;
//
//	public List<Item> getAllItems() {
//		log.info("Fetching all items from inventory");
//		return itemRepository.findAll();
//	}
//
//	public Item getItemById(Long id) {
//		log.debug("Looking for item with id={}", id);
//		return itemRepository.findById(id).orElseThrow(() -> {
//			log.error("Item with id={} not found", id);
//			return new ItemNotFoundException("Item not found with id: " + id);
//		});
//	}
//
//	public Item createItem(Item item) {
//		log.info("Creating new item: {}", item.getName());
//		return itemRepository.save(item);
//	}
//
//	public Item updateSellingPrice(Long id, BigDecimal newPrice) {
//		log.info("Updating selling price for item id={} to {}", id, newPrice);
//		Item item = getItemById(id);
//		item.setSellingPrice(newPrice);
//		return itemRepository.save(item);
//	}
//
//	public Item addStock(Long id, int additionalQuantity) {
//		log.info("Adding stock for item id={} by {}", id, additionalQuantity);
//		Item item = getItemById(id);
//		item.setQuantityInStock(item.getQuantityInStock() + additionalQuantity);
//		return itemRepository.save(item);
//	}
//
//	public List<Item> findByCategory(String category) {
//		log.info("Fetching items by category={}", category);
//		return itemRepository.findByCategory(category);
//	}
//
//	public boolean existsById(Long id) {
//		log.debug("Checking if item with id={} exists", id);
//		return itemRepository.existsById(id);
//	}
//
//	public void deleteById(Long id) {
//		log.warn("Deleting item with id={}", id);
//		if (!itemRepository.existsById(id)) {
//			log.error("Failed to delete. Item with id={} not found", id);
//			throw new ItemNotFoundException("Item not found with id: " + id);
//		}
//		itemRepository.deleteById(id);
//		log.info("Item with id={} deleted successfully", id);
//	}
//}

//package com.newdaylodgeandapartments.inventory_sales_management_system.service;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.newdaylodgeandapartments.inventory_sales_management_system.entity.Item;
//import com.newdaylodgeandapartments.inventory_sales_management_system.repo.ItemRepository;
//
//@Service
//public class ItemService {
//
//	private static final Logger logger = LoggerFactory.getLogger(ItemService.class);
//
//	@Autowired
//	private ItemRepository itemRepository;
//
//	public List<Item> getAllItems() {
//		logger.info("Fetching all items");
//		return itemRepository.findAll();
//	}
//
//	public Item getItemById(Long id) {
//		logger.info("Fetching item with id={}", id);
//		return itemRepository.findById(id).orElseThrow(() -> {
//			logger.warn("Item with id={} not found", id);
//			return new RuntimeException("Item not found");
//		});
//	}
//
//	public Item createItem(Item item) {
//		Item saved = itemRepository.save(item);
//		logger.info("Created new item: id={}, name={}", saved.getId(), saved.getName());
//		return saved;
//	}
//
//	public Item updateSellingPrice(Long id, BigDecimal newPrice) {
//		Item item = getItemById(id);
//		BigDecimal oldPrice = item.getSellingPrice();
//		item.setSellingPrice(newPrice);
//		Item updated = itemRepository.save(item);
//		logger.info("Updated price for item id={} from {} to {}", id, oldPrice, newPrice);
//		return updated;
//	}
//
//	public Item addStock(Long id, int additionalQuantity) {
//		Item item = getItemById(id);
//		int oldQty = item.getQuantityInStock();
//		item.setQuantityInStock(oldQty + additionalQuantity);
//		Item updated = itemRepository.save(item);
//		logger.info("Added stock for item id={}: oldQty={}, addedQty={}, newQty={}", id, oldQty, additionalQuantity,
//				updated.getQuantityInStock());
//		return updated;
//	}
//
//	public List<Item> findByCategory(String category) {
//		logger.info("Fetching items in category '{}'", category);
//		return itemRepository.findByCategory(category);
//	}
//
//	public boolean existsById(Long id) {
//		boolean exists = itemRepository.existsById(id);
//		logger.info("Checking if item exists with id={}: {}", id, exists);
//		return exists;
//	}
//
//	public void deleteById(Long id) {
//		if (existsById(id)) {
//			itemRepository.deleteById(id);
//			logger.info("Deleted item with id={}", id);
//		} else {
//			logger.warn("Attempted to delete item with id={} but it does not exist", id);
//		}
//	}
//
//	// Other existing service methods...
//}

//@Service
//public class ItemService {
//	@Autowired
//	private ItemRepository itemRepository;
//
//	public Item save(Item item) {
//		return itemRepository.save(item);
//	}
//
//	public List<Item> getAll() {
//		return itemRepository.findAll();
//	}
//
//	public Item addStock(Long id, int additionalQuantity) {
//		Item item = itemRepository.findById(id).orElseThrow();
//		item.setQuantityInStock(item.getQuantityInStock() + additionalQuantity);
//		return itemRepository.save(item);
//	}
//
//	public Item updateSellingPrice(Long id, BigDecimal newPrice) {
//		Item item = itemRepository.findById(id).orElseThrow();
//		item.setSellingPrice(newPrice);
//		return itemRepository.save(item);
//	}
//}

//}