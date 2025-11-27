package com.nsaasystems.inventory_sales_management_system.entity;

public class SaleRequest {
	private Long itemId;
	private int quantitySold;

	public SaleRequest() {
		super();
	}

	public SaleRequest(Long itemId, int quantitySold) {
		super();
		this.itemId = itemId;
		this.quantitySold = quantitySold;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public int getQuantitySold() {
		return quantitySold;
	}

	public void setQuantitySold(int quantitySold) {
		this.quantitySold = quantitySold;
	}
}
