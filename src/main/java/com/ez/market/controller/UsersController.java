package com.ez.market.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ez.market.service.UsersService;

import oracle.jdbc.proxy.annotation.Post;

@Controller
@RequestMapping("/user")
public class UsersController 
{
	@Autowired
	UsersService usersvc;
	
	
		@GetMapping("/login")
	   public String googleLogin()
	   {
	      return "user/GoogleLogin";
	   }
		 @GetMapping("/main")
		   @ResponseBody
		   public String main()
		   {
		      return "main";
		   }
	
	@ResponseBody
	@PostMapping("/check")
	public Map<String,Object> Idcheck(@RequestParam String userid)
	{
		boolean idcheck = usersvc.IdCheck(userid);
		Map<String,Object> map = new HashMap<>();
		map.put("check", idcheck);
		return map;
	}
	
	@GetMapping("/loginForm")
	public String loginForm(){
		return "/user/loginForm";
	}
	@GetMapping("/register")
	public String register() {
		return "user/register";
	}
}
