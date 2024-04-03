package com.ez.market.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("productboard")
public class ProductBoardController 
{
	@GetMapping("")
	public String productBoard() 
	{
		
		return "";
	}
}
