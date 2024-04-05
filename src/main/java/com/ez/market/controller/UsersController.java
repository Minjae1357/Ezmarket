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
import org.springframework.web.servlet.ModelAndView;

import com.ez.market.dto.Users;
import com.ez.market.dto.UsersGenderAge;
import com.ez.market.service.EmailService;
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
	private UsersService usersvc;
	@Autowired
	private EmailService emailsvc;
	
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
	public Map<String,Object> sendVerificationEmail(@RequestParam("email")String email,
			HttpSession session)
	{
		boolean userEmailCheck = usersvc.usersEmailCheck(email);
		log.info("유저이메일체크 트루 펄스"+userEmailCheck);
		Map<String,Object> map = new HashMap<>();
		if(userEmailCheck) {
			session.setAttribute("useremail", email);
			String authCode = emailsvc.createRandomStr();
			session.setAttribute("authCode", authCode);
			emailsvc.sendSimpleMessage(email,"이메일 인증","인증 코드는 다음과 같습니다 : " + authCode);
			map.put("sendemail", true);
			return map;
		}else {
			map.put("sendemail", false);
			map.put("msg", "이미 가입된 이메일 입니다.");
			return map;
		}
		
	} 
	
	@ResponseBody
	@PostMapping("/auth")
	public Map<String,Object> emailAuth(@RequestParam("authCode") String returnCode, HttpSession session)
	{
		String authCode = (String) session.getAttribute("authCode");
		String userEmail = (String)session.getAttribute("useremail");
		log.info("useremail:" +userEmail);
		log.info("authcode:" +authCode);
		Map<String,Object> map = new HashMap<>();
		 if (authCode == null || userEmail == null) { // 세션에 해당 정보가 없는 경우
		        map.put("success", false);
		        map.put("msg", "세션이 만료되었거나 유효하지 않습니다.");
		        return map;
		    }
		 
		if(returnCode.equals(authCode)) {
			usersvc.tempEmailSave(userEmail);
			session.removeAttribute(authCode);
			session.removeAttribute("useremail");
			map.put("success",true );
			map.put("msg", "메일인증에 성공");
			return map;
		}else {
			session.removeAttribute(authCode);
			session.removeAttribute("useremail");
			map.put("success", false);
			map.put("msg", "메일인증에 실패");
			return map;
		}
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
			return map;
		}
		boolean userEmailCheck = usersvc.tempEmailCheck(user.getEmail());
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
	
	@PostMapping("/clearSessionMessage")
	public String clearSessionMessage(HttpSession session){
		log.info("이거실행엄준식"+session.getAttribute("message"));
		session.removeAttribute("message");
		session.removeAttribute("useremail");
		return "redirect:/user/loginForm";
	}
}
