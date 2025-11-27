package com.nsaasystems.inventory_sales_management_system.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nsaasystems.inventory_sales_management_system.entity.Item;
import com.nsaasystems.inventory_sales_management_system.entity.Sale;
import com.nsaasystems.inventory_sales_management_system.entity.SaleRequest;
import com.nsaasystems.inventory_sales_management_system.exception.ItemNotFoundException;
import com.nsaasystems.inventory_sales_management_system.repo.ItemRepository;
import com.nsaasystems.inventory_sales_management_system.repo.SaleRepository;

@Service
public class SaleService {

	private static final Logger log = LoggerFactory.getLogger(SaleService.class);

	@Autowired
	private SaleRepository saleRepository;

	@Autowired
	private ItemRepository itemRepository;

	public Sale recordSale(SaleRequest request) {
		log.info("Recording sale for itemId={} with quantity={}", request.getItemId(), request.getQuantitySold());

		Item item = itemRepository.findById(request.getItemId()).orElseThrow(() -> {
			log.error("Item with id={} not found, sale cannot be recorded", request.getItemId());
			return new ItemNotFoundException("Item not found with id: " + request.getItemId());
		});

		if (item.getQuantityInStock() < request.getQuantitySold()) {
			log.error("Insufficient stock for item id={} (requested={}, available={})", item.getId(),
					request.getQuantitySold(), item.getQuantityInStock());
			throw new RuntimeException("Insufficient stock for item: " + item.getName());
		}

		// Deduct stock
		item.setQuantityInStock(item.getQuantityInStock() - request.getQuantitySold());
		itemRepository.save(item);
		log.debug("Stock updated for item id={}, new quantity={}", item.getId(), item.getQuantityInStock());

		// Save sale
		Sale sale = new Sale();
		sale.setItem(item);
		sale.setQuantitySold(request.getQuantitySold());

		Sale savedSale = saleRepository.save(sale);
		log.info("Sale recorded successfully for item={} (saleId={})", item.getName(), savedSale.getId());
		return savedSale;
	}

	public List<Sale> getAllSales() {
		log.info("Fetching all sales records");
		return saleRepository.findAll();
	}

	public Map<String, Object> getProfitReport() {
		log.info("Generating profit report");
		List<Sale> sales = saleRepository.findAll();

		BigDecimal totalCost = BigDecimal.ZERO;
		BigDecimal totalRevenue = BigDecimal.ZERO;

		for (Sale sale : sales) {
			BigDecimal cost = sale.getItem().getCostPrice().multiply(BigDecimal.valueOf(sale.getQuantitySold()));
			BigDecimal revenue = sale.getItem().getSellingPrice().multiply(BigDecimal.valueOf(sale.getQuantitySold()));
			totalCost = totalCost.add(cost);
			totalRevenue = totalRevenue.add(revenue);

			log.debug("Sale processed: itemId={}, qty={}, cost={}, revenue={}", sale.getItem().getId(),
					sale.getQuantitySold(), cost, revenue);
		}

		BigDecimal profit = totalRevenue.subtract(totalCost);
		log.info("Profit report generated: totalCost={}, totalRevenue={}, profit={}", totalCost, totalRevenue, profit);

		Map<String, Object> report = new HashMap<>();
		report.put("totalCost", totalCost);
		report.put("totalRevenue", totalRevenue);
		report.put("profit", profit);

		return report;
	}
}

//package com.newdaylodgeandapartments.inventory_sales_management_system.service;
//
//import java.math.BigDecimal;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.newdaylodgeandapartments.inventory_sales_management_system.entity.Item;
//import com.newdaylodgeandapartments.inventory_sales_management_system.entity.Sale;
//import com.newdaylodgeandapartments.inventory_sales_management_system.entity.SaleRequest;
//import com.newdaylodgeandapartments.inventory_sales_management_system.repo.ItemRepository;
//import com.newdaylodgeandapartments.inventory_sales_management_system.repo.SaleRepository;
//
//@Service
//public class SaleService {
//
//	private static final Logger logger = LoggerFactory.getLogger(SaleService.class);
//
//	@Autowired
//	private SaleRepository saleRepository;
//
//	@Autowired
//	private ItemRepository itemRepository;
//
//	public Sale recordSale(SaleRequest request) {
//		logger.info("Recording sale for itemId={} quantity={}", request.getItemId(), request.getQuantitySold());
//
//		Item item = itemRepository.findById(request.getItemId()).orElseThrow(() -> {
//			logger.error("Item not found with id={}", request.getItemId());
//			return new RuntimeException("Item not found");
//		});
//
//		if (item.getQuantityInStock() < request.getQuantitySold()) {
//			logger.error("Insufficient stock for item={} requested={} available={}", item.getName(),
//					request.getQuantitySold(), item.getQuantityInStock());
//			throw new RuntimeException("Insufficient stock for item: " + item.getName());
//		}
//
//		item.setQuantityInStock(item.getQuantityInStock() - request.getQuantitySold());
//		itemRepository.save(item);
//		logger.info("Updated stock for item={} newQuantity={}", item.getName(), item.getQuantityInStock());
//
//		Sale sale = new Sale();
//		sale.setItem(item);
//		sale.setQuantitySold(request.getQuantitySold());
//
//		Sale savedSale = saleRepository.save(sale);
//		logger.info("Sale recorded successfully with id={}", savedSale.getId());
//
//		return savedSale;
//	}
//
//	public List<Sale> getAllSales() {
//		logger.info("Fetching all sales");
//		return saleRepository.findAll();
//	}
//
//	public Map<String, Object> getProfitReport() {
//		logger.info("Generating profit report");
//		List<Sale> sales = saleRepository.findAll();
//
//		BigDecimal totalCost = BigDecimal.ZERO;
//		BigDecimal totalRevenue = BigDecimal.ZERO;
//
//		for (Sale sale : sales) {
//			BigDecimal cost = sale.getItem().getCostPrice().multiply(BigDecimal.valueOf(sale.getQuantitySold()));
//			BigDecimal revenue = sale.getItem().getSellingPrice().multiply(BigDecimal.valueOf(sale.getQuantitySold()));
//			totalCost = totalCost.add(cost);
//			totalRevenue = totalRevenue.add(revenue);
//		}
//
//		Map<String, Object> report = new HashMap<>();
//		report.put("totalCost", totalCost);
//		report.put("totalRevenue", totalRevenue);
//		report.put("profit", totalRevenue.subtract(totalCost));
//
//		logger.info("Profit report generated: totalCost={}, totalRevenue={}, profit={}", totalCost, totalRevenue,
//				totalRevenue.subtract(totalCost));
//
//		return report;
//	}
//}

//package com.newdaylodgeandapartments.inventory_sales_management_system.service;
//
//import java.math.BigDecimal;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.newdaylodgeandapartments.inventory_sales_management_system.entity.Item;
//import com.newdaylodgeandapartments.inventory_sales_management_system.entity.Sale;
//import com.newdaylodgeandapartments.inventory_sales_management_system.entity.SaleRequest;
//import com.newdaylodgeandapartments.inventory_sales_management_system.repo.ItemRepository;
//import com.newdaylodgeandapartments.inventory_sales_management_system.repo.SaleRepository;
//
//@Service
//public class SaleService {
//
//	@Autowired
//	private SaleRepository saleRepository;
//
//	@Autowired
//	private ItemRepository itemRepository;
//
//	public Sale recordSale(SaleRequest request) {
//		Item item = itemRepository.findById(request.getItemId())
//				.orElseThrow(() -> new RuntimeException("Item not found"));
//
//		if (item.getQuantityInStock() < request.getQuantitySold()) {
//			throw new RuntimeException("Insufficient stock for item: " + item.getName());
//		}
//
//		item.setQuantityInStock(item.getQuantityInStock() - request.getQuantitySold());
//		itemRepository.save(item);
//
//		Sale sale = new Sale();
//		sale.setItem(item);
//		sale.setQuantitySold(request.getQuantitySold());
//
//		return saleRepository.save(sale);
//	}
//
//	public List<Sale> getAllSales() {
//		return saleRepository.findAll();
//	}
//
//	public Map<String, Object> getProfitReport() {
//		List<Sale> sales = saleRepository.findAll();
//
//		BigDecimal totalCost = BigDecimal.ZERO;
//		BigDecimal totalRevenue = BigDecimal.ZERO;
//
//		for (Sale sale : sales) {
//			BigDecimal cost = sale.getItem().getCostPrice().multiply(BigDecimal.valueOf(sale.getQuantitySold()));
//			BigDecimal revenue = sale.getItem().getSellingPrice().multiply(BigDecimal.valueOf(sale.getQuantitySold()));
//			totalCost = totalCost.add(cost);
//			totalRevenue = totalRevenue.add(revenue);
//		}
//
//		Map<String, Object> report = new HashMap<>();
//		report.put("totalCost", totalCost);
//		report.put("totalRevenue", totalRevenue);
//		report.put("profit", totalRevenue.subtract(totalCost));
//		return report;
//	}
//}
