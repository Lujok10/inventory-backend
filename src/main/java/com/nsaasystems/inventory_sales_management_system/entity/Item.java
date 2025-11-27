//package com.newdaylodgeandapartments.inventory_sales_management_system.entity;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//
//@Entity
//public class Item {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//
//	private String name;
//	private String category;
//	private String brand;
//	private BigDecimal costPrice;
//	private BigDecimal sellingPrice;
//	private int quantityInStock;
//	private LocalDateTime createdAt = LocalDateTime.now();
//
//	public Item(Long id, String name, String category, String brand, BigDecimal costPrice, BigDecimal sellingPrice,
//			int quantityInStock, LocalDateTime createdAt) {
//		super();
//		this.id = id;
//		this.name = name;
//		this.category = category;
//		this.brand = brand;
//		this.costPrice = costPrice;
//		this.sellingPrice = sellingPrice;
//		this.quantityInStock = quantityInStock;
//		this.createdAt = createdAt;
//	}
//
//	public Item() {
//		super();
//	}
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getCategory() {
//		return category;
//	}
//
//	public void setCategory(String category) {
//		this.category = category;
//	}
//
//	public String getBrand() {
//		return brand;
//	}
//
//	public void setBrand(String brand) {
//		this.brand = brand;
//	}
//
//	public BigDecimal getCostPrice() {
//		return costPrice;
//	}
//
//	public void setCostPrice(BigDecimal costPrice) {
//		this.costPrice = costPrice;
//	}
//
//	public BigDecimal getSellingPrice() {
//		return sellingPrice;
//	}
//
//	public void setSellingPrice(BigDecimal sellingPrice) {
//		this.sellingPrice = sellingPrice;
//	}
//
//	public int getQuantityInStock() {
//		return quantityInStock;
//	}
//
//	public void setQuantityInStock(int quantityInStock) {
//		this.quantityInStock = quantityInStock;
//	}
//
//	public LocalDateTime getCreatedAt() {
//		return createdAt;
//	}
//
//	public void setCreatedAt(LocalDateTime createdAt) {
//		this.createdAt = createdAt;
//	}
//
//}

//package com.newdaylodgeandapartments.inventory_sales_management_system.entity;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//
//@Entity
//public class Item {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//
//	private String name;
//	private String category;
//	private String brand;
//	private BigDecimal costPrice;
//	private BigDecimal sellingPrice;
//	private int quantityInStock;
//	private String imageName; // ✅ NEW field for image filename
//	private LocalDateTime createdAt = LocalDateTime.now();
//
//	public Item(Long id, String name, String category, String brand, BigDecimal costPrice, BigDecimal sellingPrice,
//			int quantityInStock, String imageName, LocalDateTime createdAt) {
//		super();
//		this.id = id;
//		this.name = name;
//		this.category = category;
//		this.brand = brand;
//		this.costPrice = costPrice;
//		this.sellingPrice = sellingPrice;
//		this.quantityInStock = quantityInStock;
//		this.imageName = imageName;
//		this.createdAt = createdAt;
//	}
//
//	public Item() {
//		super();
//	}
//
//	// Getters & Setters
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getCategory() {
//		return category;
//	}
//
//	public void setCategory(String category) {
//		this.category = category;
//	}
//
//	public String getBrand() {
//		return brand;
//	}
//
//	public void setBrand(String brand) {
//		this.brand = brand;
//	}
//
//	public BigDecimal getCostPrice() {
//		return costPrice;
//	}
//
//	public void setCostPrice(BigDecimal costPrice) {
//		this.costPrice = costPrice;
//	}
//
//	public BigDecimal getSellingPrice() {
//		return sellingPrice;
//	}
//
//	public void setSellingPrice(BigDecimal sellingPrice) {
//		this.sellingPrice = sellingPrice;
//	}
//
//	public int getQuantityInStock() {
//		return quantityInStock;
//	}
//
//	public void setQuantityInStock(int quantityInStock) {
//		this.quantityInStock = quantityInStock;
//	}
//
//	public String getImageName() {
//		return imageName;
//	}
//
//	public void setImageName(String imageName) {
//		this.imageName = imageName;
//	}
//
//	public LocalDateTime getCreatedAt() {
//		return createdAt;
//	}
//
//	public void setCreatedAt(LocalDateTime createdAt) {
//		this.createdAt = createdAt;
//	}
//}

package com.nsaasystems.inventory_sales_management_system.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String category;
	private String brand;
	private BigDecimal costPrice;
	private BigDecimal sellingPrice;
	private int quantityInStock;
	private LocalDateTime createdAt = LocalDateTime.now();

	// ✅ New field for image filename
	private String imageName;

	public Item() {
		super();
	}

	public Item(Long id, String name, String category, String brand, BigDecimal costPrice, BigDecimal sellingPrice,
			int quantityInStock, LocalDateTime createdAt, String imageName) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.brand = brand;
		this.costPrice = costPrice;
		this.sellingPrice = sellingPrice;
		this.quantityInStock = quantityInStock;
		this.createdAt = createdAt;
		this.imageName = imageName;
	}

	// ===== Getters & Setters =====
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	public BigDecimal getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(BigDecimal sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public int getQuantityInStock() {
		return quantityInStock;
	}

	public void setQuantityInStock(int quantityInStock) {
		this.quantityInStock = quantityInStock;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
}
