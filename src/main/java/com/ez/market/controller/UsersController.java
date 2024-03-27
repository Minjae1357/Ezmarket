package com.ez.market.controller;

import java.util.HashMap;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ez.market.dto.Users;
import com.ez.market.dto.UsersGenderAge;
import com.ez.market.service.UsersService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.proxy.annotation.Post;
@Slf4j
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
	
		 
	@ResponseBody
	@PostMapping("/check")
	public Map<String,Object> idCheck(@RequestParam String userid)
	{
		System.out.println("엄");
		boolean idcheck = usersvc.idCheck(userid);
		Map<String,Object> map = new HashMap<>();
		map.put("check", idcheck);
		return map;
	}
	 
	@GetMapping("/loginForm")
	public String loginForm(Model m,HttpSession session)
	{
		System.out.println("엄준식"+session.getAttribute("message"));
		
		return "user/loginForm";
	}
	
	@GetMapping("/register")
	public String register() 
	{
		return "user/register";
	}
	
	@ResponseBody
	@PostMapping("/sendVerificationEmail")
	public Map<String,Object> sendVerificationEmail(@ModelAttribute Users user)
	{
		//여기에 이메일정보받아서 이메일인증번호보내는 메서드추가
		Map<String,Object> map = new HashMap<>();
		map.put("ok", false);
		return map;
	} 
	
	
	@ResponseBody
	@PostMapping("/register")
	public Map<String,Object> userRegister(@ModelAttribute Users user,
										   @ModelAttribute UsersGenderAge genderage)
	{
		Map<String,Object> map = new HashMap<>();
		System.out.println("genderage정보"+genderage);
		System.out.println("user정보"+user);
		if(user.getUserid().length()<5) {
			map.put("save", false);
			map.put("msg", "아이디는 5자 이상이여야합니다.");
		}
		boolean userEmailCheck = usersvc.emailcheck(user.getEmail());
		System.out.println("userEmailCheck정보"+userEmailCheck);
		if(userEmailCheck) {
			boolean registerCheck = usersvc.userSave(user);
			usersvc.userAuthority(user.getUserid(),user.getEmail());
			usersvc.userGenderAgeSave(genderage);
			map.put("save", registerCheck);
			return map;
		}else {
			map.put("save", false);
			map.put("msg", "인증한 이메일만 가입이 가능합니다");
			return map;
		}
		
	}
	
	@GetMapping("/googleRegister")
	public String googleRegister(Model m,@RequestParam String useremail) 
	{
		log.info("useremail : "+ useremail);	
		m.addAttribute("email",useremail);
		return "user/register";
	}
}
