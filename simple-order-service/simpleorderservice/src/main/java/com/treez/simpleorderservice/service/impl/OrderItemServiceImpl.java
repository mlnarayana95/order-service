package com.treez.simpleorderservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treez.simpleorderservice.model.OrderItem;
import com.treez.simpleorderservice.repository.OrderItemRepository;
import com.treez.simpleorderservice.service.OrderItemService;

import javassist.tools.rmi.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService{
	@Autowired
	OrderItemRepository orderItemRepository;
	
	@Override
	public OrderItem createOrderItem(OrderItem item) {
		return orderItemRepository.save(item);
	}


	@Override
	public void deleteOrderItems(int orderId) throws ObjectNotFoundException {
		
			orderItemRepository.deleteAllItems(orderId);
		
	}


	@Override
	public Integer getOrderItem(Integer orderId) {
		return orderItemRepository.findAllOrderItems(orderId);
	}


	@Override
	public List<OrderItem> getOrderItems(Integer orderId) {
		return orderItemRepository.getAllOrderItems(orderId);
	}

}
