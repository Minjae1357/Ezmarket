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
import com.ez.market.service.UsersService;

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
		System.out.println("시작되긴함?");
		boolean executed = adminsvc.updateEnabled(userid, enabled);
		System.out.println("활성화 관련"+executed);
		Map<String,Object> map = new HashMap<>();
		map.put("ox", executed);
		return map;
	}
}
