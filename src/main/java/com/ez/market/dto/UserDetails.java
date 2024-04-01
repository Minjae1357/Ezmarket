package com.ez.market.dto;

import java.sql.Date;

import org.springframework.stereotype.Service;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.NonFinal;

@ToString
@Data
@Service
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class UserDetails 
{
	@Nonnull
	private String userid;
	private String username;
	private String email;
	private int phone;
	private Date regdate;
	private String enabled;
	private String authority;
}
