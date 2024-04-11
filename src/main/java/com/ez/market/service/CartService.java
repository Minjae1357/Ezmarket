package com.ez.market.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ez.market.dto.OrderInfo;
import com.ez.market.dto.BuyPage;
import com.ez.market.dto.OrderPage;
import com.ez.market.dto.QCart;
import com.ez.market.dto.QImgs;
import com.ez.market.dto.QProduct;
import com.ez.market.dto.QSizes;
import com.ez.market.dto.QUsersOrder;
import com.ez.market.dto.UsersOrder;
import com.ez.market.dto.UsersOrderListReceive;
import com.ez.market.dto.UsersOrderListReceive.OrderRecive;
import com.ez.market.dto.CartPage;
import com.ez.market.repository.CartRepository;
import com.ez.market.repository.OrderInfoRepository;
import com.ez.market.repository.UsersOrderRepository;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder.In;

@Service
public class CartService {

	@Autowired
	CartRepository cartrepo;
	
	@Autowired
	UsersOrderRepository uorepo;
	
	@Autowired
	OrderInfoRepository oirepo;
	
	@PersistenceContext
	EntityManager entityManager;
	
	
	public List<CartPage> getCartList(){
		Authentication id = SecurityContextHolder.getContext().getAuthentication();
		String userid = id.getName();
		
		var query = new JPAQueryFactory(entityManager);
		QCart CART = QCart.cart;
		QProduct PD = QProduct.product;
		QImgs IMG = QImgs.imgs;
		QSizes SIZE = QSizes.sizes;
		
		List<CartPage> cartList = query
				.select(Projections.constructor(CartPage.class,
						CART.cnum,
						PD.productName,
						PD.productPrice,
						SIZE.size,
						IMG.imgSrc
				))
				.from(CART)
				.join(PD).on(CART.productId.eq(PD.productId))
				.join(IMG).on(PD.productId.eq(IMG.productId)
						.and(IMG.imgnum.eq(JPAExpressions
								.select(IMG.imgnum.min())
								.from(IMG)
								.where(IMG.productId.eq(PD.productId)))))
				.join(SIZE).on(PD.sId.eq(SIZE.sId))
				.where(CART.userid.eq(userid))       // 변수로 받게 변경 필요
				.fetch();
		return cartList;
	}
	
	public List<BuyPage> getCheckList(String check){
		// 체크된 값들만 Integer형 리스트(delcnums)로 만들기
		String[] _checkcnums = check.split(",");
		List<Integer> checkcnums = new ArrayList<>();
		for (String del : _checkcnums) {
			checkcnums.add(Integer.parseInt(del));
		}
		
		var query = new JPAQueryFactory(entityManager);
		QCart CART = QCart.cart;
		QProduct PD = QProduct.product;
		QImgs IMG = QImgs.imgs;
		QSizes SIZE = QSizes.sizes;
		List<BuyPage> buyList = query
				.select(Projections.constructor(BuyPage.class,
						CART.cnum, 
						PD.productName,
						PD.productPrice,
						PD.productId, 
						SIZE.size, 
						IMG.imgSrc))
				.from(CART)
				.join(PD).on(CART.productId.eq(PD.productId))
				.join(IMG).on(PD.productId.eq(IMG.productId)
						.and(IMG.imgnum.eq(JPAExpressions
								.select(IMG.imgnum.min())
								.from(IMG)
								.where(IMG.productId.eq(PD.productId)))))
				.join(SIZE).on(PD.sId.eq(SIZE.sId))
				.where(CART.cnum.in(checkcnums))	// 리스트(delcnums)를 조건으로
				.fetch();
		return buyList;
	}
	
	// 장바구니에서 체크삭제
	public boolean delete(String checked) {
		
		// 데이터가 delidx=3&delidx=1&delidx=2 이런식으로 전달됨
		// 숫자만 뽑아서 리스트(delcnums)로 변환
		String[] pairs = checked.split("&");
		List<Integer> delcnums = new ArrayList<>();
		for (String pair : pairs) {
	        String[] keyValue = pair.split("=");
	        if ("check".equals(keyValue[0])) {
	            delcnums.add(Integer.parseInt(keyValue[1]));
	        }
	    }
		for(int i=delcnums.size()-1;i>=0;i--) {
			cartrepo.deleteById(delcnums.get(i));
		}
		return true;
	}
	
	// 장바구니에서 개별삭제
	public boolean deleteone(int delnum) {
		cartrepo.deleteById(delnum);
		return true;
	}
	
	// 구매버튼 누를시 UsersOrder 테이블에 추가
	@Transactional
	public boolean addUO(UsersOrderListReceive uoRecive) {
		Authentication id = SecurityContextHolder.getContext().getAuthentication();
		String userid = id.getName();
		// users order 테이블에 추가
		List<UsersOrder> uoList = new ArrayList<>();
		for (OrderRecive _uo : uoRecive.getUoList()) {
			UsersOrder uo = new UsersOrder();
			
			// 값 가져오기
			int productId = Integer.parseInt(_uo.getProductId());
	        int orderQty = Integer.parseInt(_uo.getOrderQty());
	        int totalPrice = Integer.parseInt(_uo.getTotalPrice());
			
			uo.setStatus("주문완료");		// api로 받아오게 수정
			uo.setPdate(Date.valueOf(LocalDate.now()));
			uo.setTotalPrice(totalPrice);
			uo.setProductId(productId);
			uo.setOrderQty(orderQty);
			uo.setOrderResult(0);
			uo.setUserid(userid);
			uoList.add(uo);
			
			// 카트에서 삭제
			int delcnum = Integer.parseInt((String)_uo.getDelcnum());
			cartrepo.deleteById(delcnum);
		}
		List<UsersOrder> savedUoList = uorepo.saveAll(uoList);

		// order info 테이블에 추가
		List<OrderInfo> oiList = new ArrayList<>();
		
		String resName = uoRecive.getOi().getResName();
		String resAddress = uoRecive.getOi().getResAddress();
		int resPhone = Integer.parseInt(uoRecive.getOi().getResPhone());
		String resRequirement = uoRecive.getOi().getResRequirement();
		
		for (UsersOrder uo : savedUoList) {
			OrderInfo oi = new OrderInfo();
			
			oi.setResName(resName);
			oi.setResAddress(resAddress);
			oi.setResPhone(resPhone);
			oi.setResRequirement(resRequirement);
			oi.setOrderdate(null);		// 공급업체 확인시 완료처리한 시간	기록하게 수정 필요
			oi.setONum(uo.getONum());
			oi.setSuppliers("company123");	// 공급업체측 이름 입력하게 수정 필요
			
			oiList.add(oi);
		}
		oirepo.saveAll(oiList);
		return true;
	}
	
	// 주문내역 보기
	public List<OrderPage> getUsersOrderList(){
		Authentication id = SecurityContextHolder.getContext().getAuthentication();
		String userid = id.getName();
		
		var query = new JPAQueryFactory(entityManager);
        QUsersOrder UO = QUsersOrder.usersOrder;
        QProduct PD = QProduct.product;
        QImgs IMG = QImgs.imgs;
        QSizes SIZE = QSizes.sizes;
        List<OrderPage> orderList = query
				.select(Projections.constructor(OrderPage.class,
						UO.oNum, UO.status, UO.totalPrice, UO.orderQty,
						UO.pdate, UO.orderResult, PD.productName,
						PD.productPrice, SIZE.size, IMG.imgSrc))
				.from(UO)
				.join(PD).on(UO.productId.eq(PD.productId))
				.join(IMG).on(PD.productId.eq(IMG.productId)
						.and(IMG.imgnum.eq(JPAExpressions
								.select(IMG.imgnum.min())
								.from(IMG)
								.where(IMG.productId.eq(PD.productId)))))
				.join(SIZE).on(PD.sId.eq(SIZE.sId))
				.where(UO.userid.eq(userid))
				.fetch();
		return orderList;
	}
	
	// 카트 갯수 가져오기
	public int cartCount() {
		Authentication id = SecurityContextHolder.getContext().getAuthentication();
		String userid = id.getName();
		int count = cartrepo.countByUserid(userid);
		
		return count;
	}
	
	// 
	public OrderInfo getOrderInfo(int oNum) {
		OrderInfo orderInfo = oirepo.findByoNum(oNum);
		return orderInfo;
	}
	
	//
	public boolean update(OrderInfo _oi) {
		OrderInfo oi = oirepo.findById(_oi.getOiNum()).get();	// DB에서 원본 데이터 가져와서
		// 새 데이터로 교체 후
		oi.setResName(_oi.getResName());
		oi.setResAddress(_oi.getResAddress());
		oi.setResPhone(_oi.getResPhone());
		oi.setResRequirement(_oi.getResRequirement());
		// DB에 저장
		oirepo.save(oi);
		return true;
	}
}
















