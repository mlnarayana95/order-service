package com.treez.simpleorderservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.treez.simpleorderservice.model.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

	@Transactional
	@Modifying
	@Query("delete from OrderItem where order_id = :id")
	void deleteAllItems( @Param("id") Integer orderId);
	
	@Transactional
	@Query(value=" SELECT qty_ordered from orders_line_items where order_id = :id",nativeQuery= true)
	Integer findAllOrderItems(@Param("id") Integer id);
	
	@Transactional
	@Query(value=" SELECT * from orders_line_items where order_id = :id",nativeQuery= true)
	List<OrderItem> getAllOrderItems(@Param("id") Integer id);
	
	

}
