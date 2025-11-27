//package com.newdaylodgeandapartments.inventory_sales_management_system.controller;
//
//import java.util.List;
//import java.util.Map;

//import java.util.List;

//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.newdaylodgeandapartments.inventory_sales_management_system.entity.Sale;
//import com.newdaylodgeandapartments.inventory_sales_management_system.entity.SaleRequest;
//import com.newdaylodgeandapartments.inventory_sales_management_system.service.SaleService;
//
//@RestController
//@RequestMapping("/api/sales")
//public class SaleController {
//	@Autowired
//	private SaleService saleService;
//
//	@PostMapping
//	public Sale sell(@RequestBody SaleRequest request) {
//		return saleService.recordSale(request);
//	}
//
//	@GetMapping
//	public List<Sale> getAllSales() {
//		return saleService.getAllSales();
//	}
//
//	@GetMapping("/report")
//	public Map<String, Object> getReport() {
//		return saleService.getProfitReport();
//	}
//}

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.newdaylodgeandapartments.inventory_sales_management_system.entity.Sale;
//import com.newdaylodgeandapartments.inventory_sales_management_system.entity.SaleRequest;
//import com.newdaylodgeandapartments.inventory_sales_management_system.service.SaleService;
//
//@RestController
//@RequestMapping("/api/sales")
//@CrossOrigin(origins = "*")
//public class SaleController {
//
//	@Autowired
//	private SaleService saleService;
//
//	@PostMapping
//	public Sale createSale(@RequestBody SaleRequest request) {
//		return saleService.recordSale(request);
//	}
//
//	@GetMapping
//	public List<Sale> getAllSales() {
//		return saleService.getAllSales();
//	}
//
//	@GetMapping("/report")
//	public Map<String, Object> getProfitReport() {
//		return saleService.getProfitReport();
//	}
//}

package com.nsaasystems.inventory_sales_management_system.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nsaasystems.inventory_sales_management_system.entity.Sale;
import com.nsaasystems.inventory_sales_management_system.entity.SaleRequest;
import com.nsaasystems.inventory_sales_management_system.service.SaleService;

@RestController
@RequestMapping("/api/sales")
@CrossOrigin(origins = "*")
public class SaleController {

	private static final Logger logger = LoggerFactory.getLogger(SaleController.class);

	@Autowired
	private SaleService saleService;

	@PostMapping
	public Sale createSale(@RequestBody SaleRequest request) {
		logger.info("POST /api/sales called with itemId={} quantity={}", request.getItemId(), request.getQuantitySold());
		return saleService.recordSale(request);
	}

	@GetMapping
	public List<Sale> getAllSales() {
		logger.info("GET /api/sales called");
		return saleService.getAllSales();
	}

	@GetMapping("/report")
	public Map<String, Object> getProfitReport() {
		logger.info("GET /api/sales/report called");
		return saleService.getProfitReport();
	}
}
