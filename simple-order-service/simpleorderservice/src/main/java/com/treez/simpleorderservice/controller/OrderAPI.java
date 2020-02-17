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
import com.treez.simpleorderservice.model.Order;
import com.treez.simpleorderservice.model.OrderItem;
import com.treez.simpleorderservice.service.InventoryService;
import com.treez.simpleorderservice.service.OrderItemService;
import com.treez.simpleorderservice.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@Api(value = "Simple Order Service")
@RequestMapping("")
public class OrderAPI {
	@Autowired
	private OrderService service;
	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private OrderItemService orderItemService;

	@GetMapping("/orders")
	@ApiOperation(value = "Read all orders")
	public @ResponseBody ResponseEntity<List<Order>> listAllOrders() {
		HttpStatus status = HttpStatus.NOT_FOUND;
		List<Order> orderList = null;
		try {
			orderList = service.getAllOrders();
			status = HttpStatus.OK;
		} catch (Exception e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(orderList, status);
	}

	@PostMapping("/order")
	@ApiOperation(value = "Create a new order")
	public @ResponseBody ResponseEntity<String> createOrder(@RequestBody OrderForm form)
			throws ObjectNotFoundException {
		HttpStatus status = HttpStatus.NOT_FOUND;
		Integer orderId = 0;
		String message = null;
		Double totalPrice = 0.0;

		List<OrderItem> orderItems = form.getOrders();
		Order newOrder = form.getOrder();
		newOrder.setTotalPrice(totalPrice);
		newOrder = service.createOrUpdateOrder(newOrder);
		orderId = newOrder.getOrderId();

		Object orderItem = null;
		for (OrderItem item : orderItems) {
			item.setOrderId(orderId);
			Integer inventoryId = item.getInventoryId();
			Integer qtyOrdered = item.getQtyOrdered();

			Inventory inventory = inventoryService.getInventory(inventoryId);
			Double itemPrice = inventory.getUnitPrice() * qtyOrdered;
			totalPrice += itemPrice;

			Integer qtyAvailable = inventoryService.getQtyAvailable(inventoryId);
			if (qtyAvailable > qtyOrdered) {
				item.setPrice(itemPrice);
				item.setQtyOrdered(qtyOrdered);
				orderItem = orderItemService.createOrderItem(item);
				inventoryService.updateInventoryQty(inventoryId, qtyAvailable - qtyOrdered);

			} else {
				message = "We're running low in the inventory. Please try after some time";
				return new ResponseEntity<>(message, status);
			}

		}
		newOrder.setTotalPrice(totalPrice);
		newOrder = service.createOrUpdateOrder(newOrder);

		if (orderItem != null) {
			message = "Order place successfully with id " + newOrder.getOrderId();
		}

		status = HttpStatus.OK;
		return new ResponseEntity<>(message, status);

	}

	@GetMapping("/order/{order_id}")
	@ApiOperation(value = "Find a single order")
	public @ResponseBody ResponseEntity<Order> findOrder(@PathVariable("order_id") int orderId) {
		Order newOrder = null;
		newOrder = service.getOrder(orderId);
		HttpStatus status = HttpStatus.OK;
		return new ResponseEntity<>(newOrder, status);
	}

	@PutMapping("/order/{orderId}")
	@ApiOperation(value = "Update order")
	public @ResponseBody ResponseEntity<String> updateOrder(@PathVariable("orderId") int orderId,
			@RequestBody OrderForm orderForm) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		Order newOrder = orderForm.getOrder();
		newOrder.setOrderId(orderId);
		List<OrderItem> orderItems = orderForm.getOrders();
		String message = null;
		Integer updatedQty = 0;

		try {
			for (OrderItem item : orderItems) {
				Inventory inventory = inventoryService.getInventory(item.getInventoryId());
				Integer inventoryQty = inventory.getQtyAvailable();
				Integer itemQty = orderItemService.getOrderItem(orderId);
				updatedQty = inventoryQty + itemQty;
				inventory.setQtyAvailable(updatedQty);
				inventoryService.createOrUpdateInventory(inventory);
			}

			orderItemService.deleteOrderItems(orderId);

			newOrder = service.createOrUpdateOrder(newOrder);

			orderId = newOrder.getOrderId();

			for (OrderItem item : orderItems) {
				item.setOrderId(orderId);
				Integer inventoryId = item.getInventoryId();
				Integer qtyOrdered = item.getQtyOrdered();

				Double totalPrice = 0.0;
				Inventory inventory = inventoryService.getInventory(inventoryId);
				Double itemPrice = inventory.getUnitPrice() * qtyOrdered;
				totalPrice += itemPrice;
				Integer currentQty = updatedQty - qtyOrdered;
				inventory.setQtyAvailable(updatedQty);
				inventoryService.updateInventoryQty(inventoryId, currentQty);
				if (updatedQty > qtyOrdered) {
					item.setPrice(itemPrice);
					item.setQtyOrdered(qtyOrdered);
					orderItemService.createOrderItem(item);
					newOrder.setTotalPrice(totalPrice);
					service.createOrUpdateOrder(newOrder);
					message = "Order ID " + newOrder.getOrderId() + " updated successfully ";
				} else {
					message = "We're running low in the inventory. Please try after some time";
					return new ResponseEntity<>(message, status);
				}

			}
			status = HttpStatus.OK;
		} catch (Exception e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(message, status);
	}

	@DeleteMapping("/order/{orderId}")
	@ApiOperation(value = "Delete an order")
	public @ResponseBody ResponseEntity<String> deleteOrder(@PathVariable Integer orderId) {
		HttpStatus status = HttpStatus.NOT_FOUND;

		String message = null;
		try {
			List<OrderItem> orderItems = orderItemService.getOrderItems(orderId);
			for (OrderItem item : orderItems) {
				Integer qtyOrdered = item.getQtyOrdered();
				Inventory inventory = inventoryService.getInventory(item.getInventoryId());
				Integer updatedQty = inventory.getQtyAvailable() + qtyOrdered;
				inventoryService.updateInventoryQty(item.getInventoryId(), updatedQty);
				message = "Order deleted successfully";
			}

			service.deleteOrder(orderId);

			status = HttpStatus.OK;
		} catch (Exception e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			message = "Order could not be deleted";
		}
		return new ResponseEntity<>(message, status);
	}

	public static class OrderForm {
		private Order order;

		private List<OrderItem> orders;

		public Order getOrder() {
			return order;
		}

		public void setOrder(Order order) {
			this.order = order;
		}

		public List<OrderItem> getOrders() {
			return orders;

		}

		public void setOrders(List<OrderItem> orders) {
			this.orders = orders;
		}

	}

}
