package com.ez.market.dto;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "admin") 
public class Admin {
	@Id
	@NonNull
	@Column(name = "AdminInid")
    private String adminid;

	@Column(name = "admininpwd", nullable = false)
	private String adminpwd;
}
