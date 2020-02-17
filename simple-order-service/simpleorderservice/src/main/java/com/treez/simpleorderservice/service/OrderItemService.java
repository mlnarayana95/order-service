package com.treez.simpleorderservice.service;

import java.util.List;

import com.treez.simpleorderservice.model.OrderItem;

import javassist.tools.rmi.ObjectNotFoundException;

public interface OrderItemService {

	OrderItem createOrderItem(OrderItem item);

	void deleteOrderItems(int orderId) throws ObjectNotFoundException;

	Integer getOrderItem(Integer orderId);

	List<OrderItem> getOrderItems(Integer orderId);

}
