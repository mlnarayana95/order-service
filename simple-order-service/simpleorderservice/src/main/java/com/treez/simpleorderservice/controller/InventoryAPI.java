package com.treez.simpleorderservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.treez.simpleorderservice.model.Inventory;
import com.treez.simpleorderservice.service.InventoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@Api(value = "Simple Order Service")
@RequestMapping("/inventories")
public class InventoryAPI {
	@Autowired
	private InventoryService service;

	@GetMapping("")
	@ApiOperation(value = "Read all inventories")
	public @ResponseBody ResponseEntity<List<Inventory>> listAllInventories() {
		List<Inventory> inventoryList = null;
		inventoryList = service.getAllInventories();
		HttpStatus status = HttpStatus.OK;
		return new ResponseEntity<>(inventoryList, status);
	}

	@PostMapping("")
	@ApiOperation(value = "Create a new inventory")
	public @ResponseBody ResponseEntity<String> createInventory(@RequestBody Inventory Inventory) {
		String message = null;
		message = service.createOrUpdateInventory(Inventory);
		HttpStatus status = HttpStatus.OK;
		return new ResponseEntity<>(message, status);
	}

	@GetMapping("/{inventory_id}")
	@ApiOperation(value = "Find a single Inventory")
	public @ResponseBody ResponseEntity<Inventory> findInventory(@PathVariable("inventory_id") Integer inventoryId)
			throws ObjectNotFoundException {
		Inventory inventory = service.getInventory(inventoryId);
		HttpStatus status = HttpStatus.OK;
		return new ResponseEntity<>(inventory, status);
	}

	@PutMapping("")
	@ApiOperation(value = "Update Inventory")
	public @ResponseBody ResponseEntity<String> updateInventory(@RequestBody Inventory inventory) {
		String message = null;
		message = service.createOrUpdateInventory(inventory);
		HttpStatus status = HttpStatus.OK;
		return new ResponseEntity<>(message, status);
	}

	@DeleteMapping("/{inventoryId}")
	@ApiOperation(value = "Delete an inventory")
	public @ResponseBody ResponseEntity<Inventory> deleteOrder(@PathVariable Integer inventoryId) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		Inventory newInventory = new Inventory();
		try {
			service.deleteInventory(inventoryId);
			status = HttpStatus.OK;
		} catch (Exception e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(newInventory, status);
	}

}
