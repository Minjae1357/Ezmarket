package com.ez.market.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.ez.market.dto.Cart;
import com.ez.market.dto.UsersOrder;
import com.ez.market.repository.CartRepository;
import com.ez.market.repository.UsersOrderRepository;

@Service
public class CartService
{
	@Autowired
	CartRepository cartrepo;
	@Autowired
	UsersOrderRepository uorepo;
	
	// 전체 리스트 가져오기
	public List<Cart> getList() {
		List<Cart> cartList = cartrepo.findAll();
		return cartList;
	}
	
	public List<Cart> getListUser(String userid) {
		List<Cart> cartList = cartrepo.findByUserid(userid);
		return cartList;
	}
	public List<UsersOrder> getListUserOrder(String userid) {
		List<UsersOrder> cartList = uorepo.findByUserid(userid);
		return cartList;
	}
	
}
 