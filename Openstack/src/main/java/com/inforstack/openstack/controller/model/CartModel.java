package com.inforstack.openstack.controller.model;

public class CartModel {

	private CartItemModel[] items;

	private String currentItemUUID;

	private Float amount;

	public CartItemModel[] getItems() {
		return items;
	}

	public void setItems(CartItemModel[] items) {
		this.items = items;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public String getCurrentItemUUID() {
		return currentItemUUID;
	}

	public void setCurrentItemUUID(String currentItemUUID) {
		this.currentItemUUID = currentItemUUID;
	}

}
