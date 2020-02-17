package com.treez.simpleorderservice.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Entity
@Table(name = "orders_line_items")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class OrderItem {
	
	@Id
	@ApiModelProperty(notes = "The database generated order ID", required = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "order_id")
	@ApiModelProperty(required = true)
	private int orderId;
	
	@Column(name = "inventory_id")
	@ApiModelProperty(required = true)
	private int inventoryId;

	@Column(name = "price")
	@ApiModelProperty(required = true)
	private Double price;

	@Column(name = "qty_ordered")
	@ApiModelProperty(required = true)
	private Integer qtyOrdered;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(int inventoryId) {
		this.inventoryId = inventoryId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQtyOrdered() {
		return qtyOrdered;
	}

	public void setQtyOrdered(Integer qtyOrdered) {
		this.qtyOrdered = qtyOrdered;
	}

}
