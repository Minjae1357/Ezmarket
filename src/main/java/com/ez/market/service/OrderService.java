package com.ez.market.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ez.market.dto.UsersOrder;
import com.ez.market.repository.UsersOrderRepository;

@Service
public class OrderService 
{
	@Autowired
	private UsersOrderRepository userorderrepo;
	
	public List<UsersOrder> userOrderList(String userid) {
		return userorderrepo.findByUserid(userid);
	}
}
