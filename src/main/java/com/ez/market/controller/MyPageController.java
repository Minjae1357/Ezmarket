package com.ez.market.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ez.market.dto.Coments;
import com.ez.market.dto.Product;
import com.ez.market.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ez.market.dto.OrderPage;
import com.ez.market.dto.UsersOrder;

@Controller
@RequestMapping("/mypage")
public class MyPageController 
{
	@Autowired
	OrderService ordersvc;
	@Autowired
	CartService cartsvc;
	@Autowired
	ComentsService comentSvc;
	@Autowired
	ProductService productSvc;
	@Autowired
	ProductBoardService productBoardSvc;
	
	@GetMapping("")
	public String mypage() {
		return "main/mypage"; 
	}
	@GetMapping("/settings")
	public String updateUser() {  
		return "user/updateuser";
	}
	@GetMapping("orders")
	public String orderList(Model model)
	{
//		Authentication id = SecurityContextHolder.getContext().getAuthentication();
//		String userid = id.getName();
//		List<UsersOrder> list = ordersvc.userOrderList(userid); 
//		m.addAttribute("list",list);		
		
		List<OrderPage> usersOrderList = cartsvc.getUsersOrderList();
		model.addAttribute("usersOrderList", usersOrderList);
		return "cart/usersOrderPage";
	}
	@GetMapping("review")
	public String writeReview(Model m,@RequestParam("productName") String productName,@RequestParam("userid") String uid){
		Authentication id = SecurityContextHolder.getContext().getAuthentication();
		String userid = id.getName();
		System.out.println(productName+"확인"+userid);
		Product pnum = productSvc.findByProductName(productName);
		m.addAttribute("pnum",pnum.getProductId());
		m.addAttribute("userid",userid);
		return "user/reviewForm";
	}

	@PostMapping("addReview")
	@ResponseBody
	public Map<String,Boolean> addReview(@ModelAttribute Coments coments){
		Map<String,Boolean> map = new HashMap<>();
		boolean ox = comentSvc.saveComents(coments);
		boolean increaseHit = productBoardSvc.increaseHit(coments.getPnum());
		map.put("result",ox==increaseHit);
		return map;
	}
}
 