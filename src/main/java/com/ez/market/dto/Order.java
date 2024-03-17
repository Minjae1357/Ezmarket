package com.ez.market.dto;

import org.springframework.lang.NonNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Order
{
	@Id
	@NonNull
	@Column(name = "o_num")
	@SequenceGenerator(sequenceName="o_seq", allocationSize=1, name="o_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "o_seq")
	private int onum;


	@Column(name = "oi_num", nullable = false)
	private int oinum;
	@Column(nullable = false)
	private int cnum;
	@Column(nullable = false)
	private String status;
	@Column(nullable = false)
	private java.sql.Date pdate;
	@Column(nullable = false)
	private String userid;
}
