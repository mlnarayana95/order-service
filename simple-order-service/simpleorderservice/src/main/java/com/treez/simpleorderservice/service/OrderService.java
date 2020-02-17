package com.treez.simpleorderservice.service;
import java.util.List;
import java.util.Optional;

import com.treez.simpleorderservice.model.Order;

import javassist.tools.rmi.ObjectNotFoundException;

public interface OrderService {
	
	List<Order> getAllOrders();

	Order createOrUpdateOrder(Order order);
	
	Order getOrder(Integer orderid) throws NullPointerException;

	void deleteOrder(Integer order_id) throws ObjectNotFoundException;
}
