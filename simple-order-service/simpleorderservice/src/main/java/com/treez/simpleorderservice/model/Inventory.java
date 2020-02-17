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
@Table(name = "inventory")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Where(clause = "deleted_at is NULL")
public class Inventory {

	@Id
	@ApiModelProperty(notes = "The database generated order ID", required = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int inventoryId;

	@Column(name = "name")
	@ApiModelProperty(required = true)
	private String name;

	@Column(name = "description")
	@ApiModelProperty(required = true)
	private String description;

	@Column(name = "qty_unit_price")
	@ApiModelProperty(required = true)
	private Double unitPrice;

	@Column(name = "qty_available")
	@ApiModelProperty(required = true)
	private Integer qtyAvailable;

	@CreationTimestamp
	private Date createdAt;

	private Date updatedAt;

	private Date deletedAt;

	public Inventory(int inventoryId, String name, String description, Double unitPrice, Integer qtyAvailable,
			Date createdAt, Date updatedAt, Date deletedAt) {
		super();
		this.inventoryId = inventoryId;
		this.name = name;
		this.description = description;
		this.unitPrice = unitPrice;
		this.qtyAvailable = qtyAvailable;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
	}

	public Inventory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Date getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}

	public int getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(int inventoryId) {
		this.inventoryId = inventoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getQtyAvailable() {
		return qtyAvailable;
	}

	public void setQtyAvailable(Integer qtyAvailable) {
		this.qtyAvailable = qtyAvailable;
	}

}
