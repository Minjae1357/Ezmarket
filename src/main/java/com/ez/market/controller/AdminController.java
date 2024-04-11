package com.ez.market.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ez.market.dto.UserDetails;
import com.ez.market.service.AdminService;
import com.ez.market.service.ProductService;
import com.ez.market.service.UsersService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController 
{
	@Autowired
	private UsersService usersvc;
	@Autowired
	private AdminService adminsvc;
	@Autowired
	private ProductService productsvc;
	
	
	@GetMapping("orders")
	public String userorder(Model m) {
		log.info("유저 주문리스트" + productsvc.getUsersOrderList());
		m.addAttribute("list",productsvc.getUsersOrderList());
		return"main/userorder";
	}
	
	
	@GetMapping("/dashboard")
	public String dashBoard() {
		return "admin/dashboard";
	} 
	
	@GetMapping("/users")
	public String userList(Model m) {
		List<UserDetails> userlist = adminsvc.userList();
		log.info("userlist"+userlist);
		m.addAttribute("list",userlist);
		return "admin/users";
	}
	@ResponseBody
	@PostMapping("/updateEnabled")
	public Map<String,Object> upDateEnabled(@RequestParam("userid")String userid
			,@RequestParam("enabled")String enabled)
	{	
		boolean executed = adminsvc.updateEnabled(userid, enabled);
		System.out.println("활성화 관련"+executed);
		Map<String,Object> map = new HashMap<>();
		map.put("ox", executed);
		return map;
	}
	@ResponseBody
	@PostMapping("/status")
	public Map<String,Object>changeStatus(@RequestParam("status")String status,@RequestParam("orderId")int orderid) {
		System.out.println("스테이터스"+status);
		log.info("status" + status); 
		boolean update = adminsvc.updateStatus(status, orderid);
		Map<String,Object> map = new HashMap<>();
		map.put("status",update );
		return map;
	}
	
}
