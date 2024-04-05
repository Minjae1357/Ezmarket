package com.ez.market.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ez.market.dto.CartPage;
import com.ez.market.dto.OrderInfo;
import com.ez.market.dto.OrderPage;
import com.ez.market.dto.BuyPage;
import com.ez.market.dto.UsersOrder;
import com.ez.market.service.CartService;
import com.ez.market.service.UsersService;

import oracle.jdbc.proxy.annotation.Post;

@Controller
@RequestMapping("/cart")
public class CartController
{
	@Autowired
	CartService cartsvc;
	
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
	
	//리스트 띄우기
	@GetMapping("list")
	public String list(Model model)
	{
		
		List<CartPage> cartList = cartsvc.getCartList();
		model.addAttribute("cartList", cartList);
		
		return "cart/listPage";
	}
	
	// 구매페이지로 이동(장바구니 페이지에서 체크한 요소들만 구매페이지로 넘긴다)
	@GetMapping("buypage")
	public String gobuyPage(Model model, @RequestParam String check) {
		List<BuyPage> buyList = cartsvc.getCheckList(check);
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
	public Map<String,Object> addUO(@RequestBody Map<String,Object> UsersOrderList) {	
		// 전달받은 UserOrderList에는 productId, orderQty, totalPrice 여러 행이 담겨있다 -> List로 변환  
		// + 발주정보 포함
		
		boolean added = cartsvc.addUO(UsersOrderList);
		
		Map<String,Object> map = new HashMap<>();
		map.put("added", added);
		return map;
	}
	
	
	// 주문내역 보기
	@GetMapping("uoList")
	public String uoList(Model model) {
		List<OrderPage> usersOrderList = cartsvc.getUsersOrderList();
		model.addAttribute("usersOrderList", usersOrderList);
		return "cart/usersOrderPage";
	}
	
}


















