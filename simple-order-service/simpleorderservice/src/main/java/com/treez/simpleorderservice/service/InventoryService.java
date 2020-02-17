package com.treez.simpleorderservice.service;

import java.util.List;

import com.treez.simpleorderservice.model.Inventory;

import javassist.tools.rmi.ObjectNotFoundException;

public interface InventoryService {

	List<Inventory> getAllInventories();

	String createOrUpdateInventory(Inventory inventory);
	
	Inventory getInventory(Integer inventoryId) throws ObjectNotFoundException;

	void deleteInventory(Integer inventoryId) throws ObjectNotFoundException;

	Integer getQtyAvailable(Integer inventoryId);

	void updateInventoryQty(Integer inventoryId, Integer qty);

	boolean inventoryItemExists(Integer inventoryId);

}
