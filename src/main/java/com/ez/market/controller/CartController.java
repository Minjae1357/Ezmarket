package com.ez.market.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ez.market.dto.Cart;
import com.ez.market.dto.QCart;
import com.ez.market.dto.QUsersOrder;
import com.ez.market.dto.UsersOrder;
import com.ez.market.service.CartService;
import com.nimbusds.oauth2.sdk.id.Subject;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/cart")
public class CartController
{
	@Autowired
	CartService cartsvc;
	@PersistenceContext
	EntityManager entityManager;
	
	@GetMapping("")
	@ResponseBody
	public String test()
	{
		return "CartController is running...";
	}
	
	/*
	// 전체 리스트 가져오기
	@GetMapping("/list")
	public String list(Model model)
	{
		List<Cart> clist = cartsvc.getList();
		model.addAttribute("clist", clist);
		return "cart/list";
	}
	*/
	
	// 테스트용 userid 에 맞는 장바구니만 가져오기
	@GetMapping("/list/{userid}")
	public String list(@PathVariable("userid") String userid, Model model)
	{
		List<Cart> clist = cartsvc.getListUser(userid);
		model.addAttribute("clist", clist);
		List<UsersOrder> uolist = cartsvc.getListUserOrder(userid);
		model.addAttribute("uolist", uolist);
		
		//조인
		var query = new JPAQueryFactory(entityManager);
		QCart CART = QCart.cart;
		QUsersOrder UO = QUsersOrder.usersOrder;
		        
		// INNER JOIN
		List<Tuple> _cuoList = query.select(CART.cnum, UO.pdate, UO.totalPrice, UO.orderQty)
		                       .from(CART)
		                       .innerJoin(UO)
		                       .on(UO.productId.eq(CART.productId)
		                       .and(UO.userid.eq(CART.userid)))
		                       .fetch();
		// 
//		_cuoList.stream().forEach(tuple -> {
//		    log.info("1. {}, 2. {}, 3. {}, 4. {}", tuple.get(CART.cnum), tuple.get(UO.pdate), tuple.get(UO.totalPrice), tuple.get(UO.orderQty));
//		});
		
		List<Map<String, Object>> cuoList = new ArrayList<>();
		_cuoList.forEach(tuple -> {
		    Map<String, Object> map = new HashMap<>();
		    map.put("cnum", tuple.get(CART.cnum));
		    map.put("pdate", tuple.get(UO.pdate));
		    map.put("totalPrice", tuple.get(UO.totalPrice));
		    map.put("orderQty", tuple.get(UO.orderQty));
		    cuoList.add(map);
		});

		model.addAttribute("cuoList", cuoList);

		return "cart/list";
	}


}


