package com.treez.simpleorderservice.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treez.simpleorderservice.model.Inventory;
import com.treez.simpleorderservice.model.Order;
import com.treez.simpleorderservice.model.OrderItem;
import com.treez.simpleorderservice.repository.OrderRepository;
import com.treez.simpleorderservice.service.InventoryService;
import com.treez.simpleorderservice.service.OrderService;
import javassist.tools.rmi.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
	@Autowired
	OrderRepository orderRepository;
	InventoryService inventoryService;

	@Override
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	@Override
	public Order createOrUpdateOrder(Order order) {
		
			Order order1 = null;
			Date currentDate = new Date();
			if (order.getOrderId() != 0) {
				order1 = orderRepository.findById(order.getOrderId()).orElseThrow(() -> new NullPointerException("Order ID does not exist. Try again with a valid ID"));;
				
				order1.setCustomerEmail(order.getCustomerEmail());
				order1.setOrderStatus(order.getOrderStatus());
				order1.setUpdatedAt(currentDate);
				return orderRepository.save(order1);	
			
			}	else {
				return orderRepository.save(order);
			}
	
	}

	@Override
	public Order getOrder(Integer orderid){
			
		return orderRepository.findById(orderid)
				.orElseThrow(() -> new NullPointerException("Order ID does not exist. Try again with a valid ID"));
		
	}

	@Override
	public void deleteOrder(Integer orderId) throws ObjectNotFoundException {

		Optional<Order> deleteOrder = orderRepository.findById(orderId);
		Date currentDate = new Date();

		if (deleteOrder.isPresent()) {
			Order order2 = deleteOrder.get();
			order2.setDeletedAt(currentDate);
			orderRepository.save(order2);
		} else {
			throw new ObjectNotFoundException("Record Not found exception");
		}

	}

}