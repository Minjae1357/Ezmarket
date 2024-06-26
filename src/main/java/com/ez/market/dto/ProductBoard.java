package com.ez.market.dto;

import java.sql.Date;

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
@Table(name="product_board")
@NoArgsConstructor
@RequiredArgsConstructor
public class ProductBoard 
{
	@Id
	@NonNull
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productboard_seq")
    @SequenceGenerator(name="productboard_seq", sequenceName="productboard_seq", allocationSize=1)
	private int pnum;
	@Column(nullable = false)
	private java.sql.Date pdate;
	@Column(name = "product_hit")
	private int pHit;
}
