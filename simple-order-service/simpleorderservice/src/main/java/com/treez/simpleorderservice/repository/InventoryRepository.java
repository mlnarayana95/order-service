package com.treez.simpleorderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.treez.simpleorderservice.model.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer>{
	
	@Transactional
	@Modifying
	@Query("update Inventory  set qty_available = :qty_available where id = :id")
	int updateInventoryQty(@Param("qty_available") Integer qtyAvailable, @Param("id") Integer id);
}
