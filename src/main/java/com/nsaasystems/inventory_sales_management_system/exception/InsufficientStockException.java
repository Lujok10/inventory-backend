package com.nsaasystems.inventory_sales_management_system.exception;

public class InsufficientStockException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InsufficientStockException(String message) {
		super(message);
	}
}
