package com.ez.market.controller;


import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ez.market.dto.CartPage;
import com.ez.market.dto.OrderInfo;
import com.ez.market.dto.OrderPage;
import com.ez.market.dto.UsersOrderListReceive;
import com.ez.market.dto.UsersOrderListReceive.OrderRecive;
import com.ez.market.dto.BuyPage;

import com.ez.market.repository.ImgsRepository;
import com.ez.market.repository.ProductRepository;
import com.ez.market.service.BuyResultService;
import com.ez.market.service.CartService;
import com.ez.market.service.ProductService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/cart")
public class CartController
{
	@Autowired
	CartService cartsvc;
	@Autowired
	ProductService productsvc;
	@Autowired
	BuyResultService buyresultsvc;
	// 테스트용
	@Autowired
	ProductRepository pdrepo;
	@Autowired
	ImgsRepository imgrepo;
	@PersistenceContext
	EntityManager entityManager;
	
	// 테스트용 데이터 생성을 위한 코드(삭제할 것)
	@GetMapping("pwd")
	@ResponseBody
	public String pwd()
	{
		BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
		System.out.println("aaa->" + enc.encode("aaa"));
		System.out.println("bbb->" + enc.encode("bbb"));
		System.out.println("ccc->" + enc.encode("ccc"));
		System.out.println("ddd->" + enc.encode("ddd"));
		return "hi pwd"; 
	}
	
	// 장바구니 리스트 띄우기
	@GetMapping("list")
	public String list(Model model) {
		System.out.println("엄준식");
		List<CartPage> cartList = cartsvc.getCartList();
		//System.out.println("#################"+cartList.get(1).getSize());
		System.out.println("엄준식" + cartList);
		model.addAttribute("cartList", cartList); 
		model.addAttribute("searchtext", "");
		return "cart/listPage";
	}
	
	// 구매페이지로 이동(장바구니 페이지에서 체크한 요소들만 구매페이지로 넘긴다) 
	@GetMapping("buypage")
	public String gobuyPage(Model model, @RequestParam String check)
	{ 
		List<BuyPage> buyList = cartsvc.getCheckList(check);
		System.out.println(check);
		model.addAttribute("buyList", buyList);
		return "cart/buyPage";
	}
	
	// 체크삭제
	@DeleteMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(@RequestParam String checked) {
		boolean deleted = cartsvc.delete(checked);
		Map<String,Object> map = new HashMap<>();
		map.put("deleted", deleted);
		return map;
	}
	// 개별삭제
	@DeleteMapping("deleteone")
	@ResponseBody
	public Map<String,Object> deleteone(@RequestParam int delnum) {
		boolean deleted = cartsvc.deleteone(delnum);
		Map<String,Object> map = new HashMap<>();
		map.put("deleted", deleted);
		return map;
	}
	
	// 주문정보, 발주정보 테이블에 저장,(usersOrder, orderInfo 에 저장하기)
	@PostMapping("addUO")
	@ResponseBody
	public Map<String,Object> addUO(@RequestBody UsersOrderListReceive uoRecive) {	
		// UsersOrderListRecive dto 사용, 이 클래스는 (productId, orderQty, totalPrice, cnum)리스트와 orderinfo 객체 하나를 담는 dto
		boolean added = cartsvc.addUO(uoRecive);
		Map<String,Object> map = new HashMap<>();
		map.put("added", added);
		return map;
	}
	
	// 주문내역 보기
	@GetMapping("uoList")
	public String uoList(Model model) {
		List<OrderPage> usersOrderList = cartsvc.getUsersOrderList();
		model.addAttribute("usersOrderList", usersOrderList);
		model.addAttribute("searchdate1", "");
		model.addAttribute("searchdate2", "");
		return "cart/usersOrderPage";
	}
	
	//배송지 조회/수정
	@Transactional
	@GetMapping("orderInfo/{oNum}")
	public String orderInfo(@PathVariable int oNum, Model model) {
		OrderInfo orderInfo = cartsvc.getOrderInfo(oNum);
		model.addAttribute("orderInfo", orderInfo);
		int orderResult = cartsvc.getOrderRes(oNum);
		model.addAttribute("orderResult", orderResult);	// 배송상태를 체크해서 배송정보를 수정가능/불가 기능 추가하기 위해 전송
		return "cart/orderInfoPage";
	}
	
	// 배송지 수정
	@PostMapping("updateOrderInfo")
	@ResponseBody
	public Map<String,Object> orderInfo(@ModelAttribute OrderInfo oi) {
		System.out.println(oi.getResName());
		boolean updated = cartsvc.update(oi);
		Map<String,Object> map = new HashMap<>();
		map.put("updated", updated);
		return map;
	}
	
	// 물품 수령
	@PostMapping("receipt")
	@ResponseBody
	@Transactional
	public Map<String,Object> receipt(@RequestParam int oNum,@RequestParam("productName")String productName) {
		System.out.println("프로덕트네임"+ productName);
		int productid = productsvc.findProductId(productName);
		buyresultsvc.saveBuyResult(productName, productid);
		boolean receipted = cartsvc.receipt(oNum);
		Map<String,Object> map = new HashMap<>();
		map.put("receipted", receipted); 
		return map;
	}
	
	// 장바구니 검색
	@GetMapping("search")
	public String search(@RequestParam String searchtext, Model model) {
		List<CartPage> cartSearchList = cartsvc.getCartSearchList(searchtext);
		model.addAttribute("cartList", cartSearchList); 
		model.addAttribute("searchtext", searchtext);
		return "cart/listPage";
	}
	
	
	// 주문내역 검색
	@GetMapping("searchdate")
	public String searchdate(@RequestParam Date searchdate1, @RequestParam Date searchdate2, Model model) {
		List<OrderPage> usersOrderSearchList = cartsvc.getUsersOrderSearchDateList(searchdate1, searchdate2);
		model.addAttribute("usersOrderList", usersOrderSearchList);
		model.addAttribute("searchdate1", searchdate1);
		model.addAttribute("searchdate2", searchdate2);
		return "cart/usersOrderPage";
	}
	
}


















