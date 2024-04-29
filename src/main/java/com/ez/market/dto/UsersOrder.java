package com.ez.market.dto;

import org.springframework.lang.NonNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name="users_order")
public class UsersOrder
{
	@Id
	@NonNull
	@Column(name = "o_num")
	@SequenceGenerator(sequenceName="o_seq", allocationSize=1, name="o_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "o_seq")
	private int oNum;
	@Column(nullable = false)
	private String status;
	@Column(nullable = false)
	private java.sql.Date pdate;  
	@Column(nullable = false)
	private String userid;
	@Column(name="totalprice",nullable = false)
	private int totalPrice;
	@Column(name="product_id",nullable = false)
	private int productId;
	@Column(name = "order_qty", nullable = false)
	private int orderQty;
	@Column(name = "order_result",nullable = false)
	private int orderResult;
	@Column(name = "s_id", nullable = false)
	private int sizeId;

}
