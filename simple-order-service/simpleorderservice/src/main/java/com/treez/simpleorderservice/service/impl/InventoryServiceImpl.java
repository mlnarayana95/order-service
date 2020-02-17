package com.treez.simpleorderservice.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treez.simpleorderservice.model.Inventory;
import com.treez.simpleorderservice.repository.InventoryRepository;
import com.treez.simpleorderservice.service.InventoryService;

import javassist.tools.rmi.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
	@Autowired
	InventoryRepository inventoryRepository;

	@Override
	public List<Inventory> getAllInventories() {
		return inventoryRepository.findAll();
	}

	@Override
	public String createOrUpdateInventory(Inventory inventory) {
		if (inventory.getInventoryId() != 0) {
			Inventory inventory1 = inventoryRepository.findById(inventory.getInventoryId()).orElseThrow(
					() -> new NullPointerException("Inventory ID does not exist. Try again with a valid ID"));
			
			Date currentDate = new Date();
			Inventory inventory2 = inventory1;
			inventory2.setDescription(inventory.getDescription());
			inventory2.setName(inventory.getName());
			inventory2.setQtyAvailable(inventory.getQtyAvailable());
			inventory2.setUnitPrice(inventory.getUnitPrice());
			inventory2.setUpdatedAt(currentDate);
			inventory2 = inventoryRepository.save(inventory2);
			return "Inventory ID " + inventory2.getInventoryId() + " updated successfully";
		} else {
			inventory = inventoryRepository.save(inventory);
			return "Inventory with Id " + inventory.getInventoryId() + " created successfully";
		}

	}

	@Override
	public Inventory getInventory(Integer inventoryId) {
		return inventoryRepository.findById(inventoryId)
				.orElseThrow(() -> new NullPointerException("Inventory ID does not exist. Try again with a valid ID"));
	}

	@Override
	public void deleteInventory(Integer inventoryId) throws ObjectNotFoundException {
		Optional<Inventory> order = inventoryRepository.findById(inventoryId);
		Date currentDate = new Date();

		if (order.isPresent()) {
			Inventory inventory = order.get();
			inventory.setDeletedAt(currentDate);
			inventoryRepository.save(inventory);
		} else {
			throw new ObjectNotFoundException("Record Not found exception");
		}

	}

	@Override
	public Integer getQtyAvailable(Integer inventoryId) {
		return inventoryRepository.findById(inventoryId).get().getQtyAvailable();
	}

	@Override
	public void updateInventoryQty(Integer inventoryId, Integer qty) {
		inventoryRepository.updateInventoryQty(qty, inventoryId);

	}

	@Override
	public boolean inventoryItemExists(Integer inventoryId) {
		return inventoryRepository.existsById(inventoryId);
	}

}
