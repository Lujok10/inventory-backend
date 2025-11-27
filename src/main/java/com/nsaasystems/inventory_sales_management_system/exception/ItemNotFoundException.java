package com.nsaasystems.inventory_sales_management_system.exception;

public class ItemNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ItemNotFoundException(String message) {
        super(message);
    }
}
