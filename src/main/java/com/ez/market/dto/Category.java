package com.ez.market.dto;

import org.springframework.lang.NonNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Category {
	@Id
	@NonNull
	@Column(name="c_num")
	private int cNum;
	@Column(name="c_kind",nullable=false)
	private String cKind;
	@Column(name="c_gender", nullable=false)
	private int cGender;
}
