package com.ez.market.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ez.market.dto.OrderInfo;
import com.ez.market.dto.BuyPage;
import com.ez.market.dto.Cart;
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
import com.ez.market.repository.SizesRepository;
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
	Cart cart;
	@Autowired
	CartRepository cartrepo;
	@Autowired
	UsersOrderRepository uorepo;
	@Autowired
	OrderInfoRepository oirepo;
	@Autowired
	SizesRepository sizerepo;
	@PersistenceContext
	EntityManager entityManager;
	
	// 장바구니 화면 리스트 가져오기
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
						IMG.imgSrc))
				.from(CART)
				.join(PD).on(CART.productId.eq(PD.productId))
				.join(IMG).on(PD.productId.eq(IMG.productId)
						.and(IMG.imgnum.eq(JPAExpressions			// 이미지 하나만 가져오기 위한 쿼리
								.select(IMG.imgnum.min())
								.from(IMG)
								.where(IMG.productId.eq(PD.productId)))))
				.where(CART.userid.eq(userid))
				.fetch();
		return cartList;
	}
	
	// 구매 페이지로 넘어갈 때 체크한 항목들의 리스트만 가져오기
									//카트번호 = check
	public List<BuyPage> getCheckList(String check, String size) {
	    String[] cartNumbers = check.split(",");
	    String[] sizes = size.split(",");
	    List<Integer> selectedCartNumbers = new ArrayList<>();
	    List<Integer> selectedSizes = new ArrayList<>();

	    for (String number : cartNumbers) {
	        try {
	            selectedCartNumbers.add(Integer.parseInt(number));
	        } catch (NumberFormatException e) {
	            // 로깅 처리 또는 사용자에게 알림
	        }
	    }

	    for (String s : sizes) {
	        try {
	            selectedSizes.add(Integer.parseInt(s));
	        } catch (NumberFormatException e) {
	            // 로깅 처리 또는 사용자에게 알림
	        }
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
	                SIZE.inventory,
	                IMG.imgSrc))
	        .from(CART)
	        .join(PD).on(CART.productId.eq(PD.productId))
	        .join(IMG).on(PD.productId.eq(IMG.productId)
	                .and(IMG.imgnum.eq(JPAExpressions
	                        .select(IMG.imgnum.min())
	                        .from(IMG)
	                        .where(IMG.productId.eq(PD.productId)))))
	        .join(SIZE).on(PD.productId.eq(SIZE.productId))
	        .where(CART.cnum.in(selectedCartNumbers), SIZE.size.in(selectedSizes))
	        .fetch();

	    return buyList;
	}

	
	// 장바구니에서 체크 다중삭제
	@Transactional
	public boolean delete(String checked) {
		
		// 데이터가 delidx=3&delidx=1&delidx=2 이런식으로 전달됨  (프론트 -> 서버 리펙토링 필요)
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
	
	// 장바구니에서 삭제버튼으로 하나씩 삭제
	@Transactional
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

	        // 재고보다 주문량이 더 많으면 실패처리
	        int inventory = getinventory(productId);
	        if(inventory<orderQty) {
	        	return false;
	        }
	        
			uo.setStatus("배송준비중");		// api로 받아오게 수정
			uo.setPdate(Date.valueOf(LocalDate.now()));
			uo.setTotalPrice(totalPrice);
			uo.setProductId(productId);
			uo.setOrderQty(orderQty);
			uo.setOrderResult(1);
			uo.setUserid(userid);
			uoList.add(uo);
			
			// 카트에서 삭제
			int delcnum = Integer.parseInt((String)_uo.getDelcnum());
			cartrepo.deleteById(delcnum);
		}
		// users_order 테이블에 저장
		List<UsersOrder> savedUoList = uorepo.saveAll(uoList);
		
		
		// order info 데이터 정리 ~ 저장
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
		// order_info 테이블에 저장
		oirepo.saveAll(oiList);
		return true;
	}
	// 상품 갯수 반환 메소드
	public int getinventory(int productId) {
		var query = new JPAQueryFactory(entityManager);
		QProduct PD = QProduct.product;
		QSizes SIZE = QSizes.sizes;
		List<Integer> qtyList = query
				.select(SIZE.inventory)
				.from(PD)
				.join(SIZE).on(PD.productId.eq(SIZE.productId))
				.where(PD.productId.eq(productId))	// 리스트(delcnums)를 조건으로
				.fetch();
		return qtyList.get(0);
	}
	
	// 주문내역 페이지 리스트 가져오기
	public List<OrderPage> getUsersOrderList(){
		
		Authentication id = SecurityContextHolder.getContext().getAuthentication();
		String userid = id.getName();
		System.out.println(userid);
		var query = new JPAQueryFactory(entityManager);
        QUsersOrder UO = QUsersOrder.usersOrder;
        QProduct PD = QProduct.product;
        QImgs IMG = QImgs.imgs;
        QSizes SIZE = QSizes.sizes;
        List<OrderPage> orderList = query
				.select(Projections.constructor(OrderPage.class, 
						UO.oNum, UO.status, UO.totalPrice, UO.orderQty,
						UO.pdate, UO.orderResult, PD.productName,
						PD.productPrice,UO.sizeNum, IMG.imgSrc))
				.from(UO)
				.join(PD).on(UO.productId.eq(PD.productId))
				.join(IMG).on(PD.productId.eq(IMG.productId)
						.and(IMG.imgnum.eq(JPAExpressions
								.select(IMG.imgnum.min())
								.from(IMG)
								.where(IMG.productId.eq(PD.productId)))))
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
	
	// 배송정보(받는자,주소,전화번호,요청사항) 가져오기
	public OrderInfo getOrderInfo(int oNum) {
		OrderInfo orderInfo = oirepo.findByoNum(oNum);
		System.out.println(orderInfo);
		return orderInfo;
	}
	
	// 주문 상태 체크용
	public int getOrderRes(int oNum) {
		return uorepo.findById(oNum).get().getOrderResult();
	}
	
	// 배송정보(받는자,주소,전화번호,요청사항) 수정
	@Transactional
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
	
	// 수령완료 버튼 누르면 수령 완료 상태로 변경
	@Transactional
	public boolean receipt(int oNum) {
		Optional<UsersOrder> _uo =  uorepo.findById(oNum);
		UsersOrder uo = _uo.get();
		if(uo.getOrderResult() != 3) {
			uo.setOrderResult(3);
			uorepo.save(uo);
			return true;
		}
		return false;
	}
	
	public boolean addCart(int productid) 
	{
		Authentication id = SecurityContextHolder.getContext().getAuthentication();
		String userid = id.getName();
		
		var query = new JPAQueryFactory(entityManager);
		QCart CART = QCart.cart;
		List<Integer> cartProductIdList = query
				.select(CART.productId)
				.from(CART)
				.where(CART.userid.eq(userid))
				.fetch();
		for(int pid : cartProductIdList) {
			if(pid==productid) {
				return false;
			}
		}
		
		Cart cart = new Cart();
		cart.setProductId(productid);
		cart.setUserid(userid);
		return cartrepo.save(cart) != null;
	}
	
	// 장바구니 검색 리스트 가져오기
	public List<CartPage> getCartSearchList(String searchtext){
		
		Authentication id = SecurityContextHolder.getContext().getAuthentication();
		String userid = id.getName();
		
		var query = new JPAQueryFactory(entityManager);
		QCart CART = QCart.cart;
		QProduct PD = QProduct.product;
		QImgs IMG = QImgs.imgs;
		QSizes SIZE = QSizes.sizes;
		List<CartPage> cartSearchList = query
				.select(Projections.constructor(CartPage.class,
						CART.cnum,
						PD.productName,
						PD.productPrice,
						IMG.imgSrc))
				.from(CART)
				.join(PD).on(CART.productId.eq(PD.productId))
				.join(IMG).on(PD.productId.eq(IMG.productId)
						.and(IMG.imgnum.eq(JPAExpressions			// 이미지 하나만 가져오기 위한 쿼리
								.select(IMG.imgnum.min())
								.from(IMG)
								.where(IMG.productId.eq(PD.productId)))))
				.where(CART.userid.eq(userid)
					.and(PD.productName.contains(searchtext)))
				.fetch();
		return cartSearchList;
	}
	
	// 장바구니 담을 때 중복인지 체크
	public Boolean CartOverlapCheck(int productId) {
		Authentication id = SecurityContextHolder.getContext().getAuthentication();
		String userid = id.getName();
		
		var query = new JPAQueryFactory(entityManager);
		QCart CART = QCart.cart;
		List<Integer> cartProductIdList = query
				.select(CART.productId)
				.from(CART)
				.where(CART.userid.eq(userid))
				.fetch();
		for(int pid : cartProductIdList) {
			if(pid==productId) {
				return true;	// 중복이면 바로 (중복 : true) 리턴
			}
		}
		return false;
	}
}
















