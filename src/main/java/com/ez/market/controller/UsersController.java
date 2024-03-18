package com.ez.market.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/users")
public class UsersController {

	@GetMapping("")
	@ResponseBody
	public String index() {
		return "Test Controller";
	}
	
	@GetMapping("/loginForm")
	public String loginForm() {
		return "users/loginForm";
	}
	
	@GetMapping("info")
	public String logininfo() {
		return "users/info";
	}
}
