package com.ez.market.dto;

import org.springframework.lang.NonNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Colors {
	@Id
	@NonNull
	@Column(name="c_name",nullable=false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "color_seq")
    @SequenceGenerator(name="color_seq", sequenceName="color_seq", allocationSize=1)
	private int cName;
	@Column(name="c_colors",nullable=false)
	private String cColors;
	
}
