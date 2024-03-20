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
@Table(name="order_info")
public class OrderInfo
{
	@Id
	@NonNull
	@Column(name = "oi_num")
	@SequenceGenerator(sequenceName="oi_seq", allocationSize=1, name="oi_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "oi_seq")
	private int oinum;

	@Column(name = "res_name", nullable = false)
	private String resName;
	@Column(name = "res_address", nullable = false)
	private String resAddress;
	@Column(name = "res_phone", nullable = false)
	private int resPhone;
	@Column(name = "res_requirement")
	private String resRequirement;
	@Column(nullable = false)
	private String userid;
	@Column(nullable = false)
	private int totalprice;
	@Column(nullable = false)
	private java.sql.Date orderdate;
}
