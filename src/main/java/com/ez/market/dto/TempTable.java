package com.ez.market.dto;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

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

@Table(name="TEMP_TABLE")
@RequiredArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Service
public class TempTable 
{
	@Id
	@NonNull
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_num")
	@SequenceGenerator(name="t_num", sequenceName = "t_num", allocationSize = 1)
	private int tnum;
	@Column(nullable = false)
	private String email;
}
