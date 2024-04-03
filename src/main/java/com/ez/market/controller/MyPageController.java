package com.ez.market.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ez.market.dto.UsersOrder;
import com.ez.market.service.OrderService;

@Controller
@RequestMapping("/mypage")
public class MyPageController 
{
	@Autowired
	OrderService ordersvc;
	
	
	@GetMapping("")
	public String mypage() {
		return "main/mypage";
	}
	@GetMapping("orders")
	public String orderList(Model m)
	{
		Authentication id = SecurityContextHolder.getContext().getAuthentication();
		String userid = id.getName();
		List<UsersOrder> list = ordersvc.userOrderList(userid); 
		m.addAttribute("list",list);		
		return "main/userorder";
	}
}
 