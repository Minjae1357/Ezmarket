package com.ez.market.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ez.market.dto.OrderInfo;
import com.ez.market.dto.QCart;
import com.ez.market.dto.QImgs;
import com.ez.market.dto.QProduct;
import com.ez.market.dto.QSizes;
import com.ez.market.dto.QUsersOrder;
import com.ez.market.dto.UsersOrder;
import com.ez.market.repository.CartRepository;
import com.ez.market.repository.OrderInfoRepository;
import com.ez.market.repository.UsersOrderRepository;
import com.querydsl.core.Tuple;
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
	
	public List<Map<String,Object>> getList(){
		Authentication id = SecurityContextHolder.getContext().getAuthentication();
		String userid = id.getName();
		var query = new JPAQueryFactory(entityManager);
        QCart CART = QCart.cart;
        QProduct PD = QProduct.product;
        QImgs IMG = QImgs.imgs;
        QSizes SIZE = QSizes.sizes;
        List<Tuple> resultList = query.select(CART.cnum, PD.productName,PD.productPrice, SIZE.size, IMG.imgSrc)
                                .from(CART)
                                .join(PD).on(CART.productId.eq(PD.productId))
                                .join(IMG).on(PD.productId.eq(IMG.productId))
                                .join(SIZE).on(PD.sId.eq(SIZE.sId))
                                .where(CART.userid.eq(userid))    // 변수로 받게 변경 필요
                                .fetch();
        
        List<Map<String, Object>> cuoList = new ArrayList<>();
        resultList.forEach(tuple -> {
        	
        	// 사이즈 변환 (숫자 -> 문자), (0:S, 1:M, 2:L, 3:XL, 4:XXL)
            String size = "";
            switch (tuple.get(SIZE.size)) {
                case 0: size = "S"; break;
                case 1: size = "M"; break;
                case 2: size = "L"; break;
                case 3: size = "XL"; break;
                case 4: size = "XXL"; break;
                default: size = "-"; break;
            }
            
            Map<String, Object> map = new HashMap<>();
            map.put("cnum", tuple.get(CART.cnum));
            map.put("productName", tuple.get(PD.productName));
            map.put("productPrice", tuple.get(PD.productPrice));
            map.put("size", size);
            map.put("imgSrc", tuple.get(IMG.imgSrc));
            
            // 테스트용 출력(삭제예정)
            System.out.println("---test---");
            System.out.println(
            	tuple.get(CART.cnum)+"  "+
            	tuple.get(PD.productName)+"  "+
            	tuple.get(PD.productPrice)+"  "+
            	tuple.get(SIZE.size)+"  "+
            	tuple.get(IMG.imgSrc)
            );
            
            cuoList.add(map);
        });
        
		return cuoList;
	}
	
public List<Map<String,Object>> getCheckList(String check){
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
        List<Tuple> resultList = query.select(CART.cnum, PD.productName,PD.productPrice,PD.productId, SIZE.size, IMG.imgSrc)
                                .from(CART)
                                .join(PD).on(CART.productId.eq(PD.productId))
                                .join(IMG).on(PD.productId.eq(IMG.productId))
                                .join(SIZE).on(PD.sId.eq(SIZE.sId))
                                .where(CART.cnum.in(checkcnums))	// 리스트(delcnums)를 조건으로
                                .fetch();
        
        List<Map<String, Object>> cuoList = new ArrayList<>();

        resultList.forEach(tuple -> {
        	// 사이즈 변환 (숫자 -> 문자), (0:S, 1:M, 2:L, 3:XL, 4:XXL)
            String size = "";
            switch (tuple.get(SIZE.size)) {
                case 0: size = "S"; break;
                case 1: size = "M"; break;
                case 2: size = "L"; break;
                case 3: size = "XL"; break;
                case 4: size = "XXL"; break;
                default: size = "-"; break;
            }
            
            Map<String, Object> map = new HashMap<>();
            map.put("cnum", tuple.get(CART.cnum));
            map.put("productName", tuple.get(PD.productName));
            map.put("productPrice", tuple.get(PD.productPrice));
            map.put("productId", tuple.get(PD.productId));
            map.put("size", size);
            map.put("imgSrc", tuple.get(IMG.imgSrc));
            
            // 테스트용 출력(삭제예정)
            System.out.println("---test---");
            System.out.println(
            	tuple.get(CART.cnum)+"  "+
            	tuple.get(PD.productName)+"  "+
            	tuple.get(PD.productPrice)+"  "+
            	tuple.get(PD.productId)+"  "+
            	tuple.get(SIZE.size)+"  "+
            	tuple.get(IMG.imgSrc)
            );
            
            cuoList.add(map);
        });
        
		return cuoList;
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
	public boolean addUO(Map<String,Object> UsersOrderList) {
		Authentication id = SecurityContextHolder.getContext().getAuthentication();
		String userid = id.getName();
		// users order 테이블에 추가
		List<UsersOrder> uoList = new ArrayList<>();
		List<Map<String, Object>> _uoList = (List<Map<String, Object>>) UsersOrderList.get("uoList");	// UserOrderList를 Map 형태의 List로 담는다
		for (Map<String, Object> _uo : _uoList) {
			UsersOrder uo = new UsersOrder();
			
			// 값 가져오기
			int productId = Integer.parseInt((String)_uo.get("productId"));
	        int orderQty = Integer.parseInt((String)_uo.get("orderQty"));
	        int totalPrice = Integer.parseInt((String)_uo.get("totalPrice"));
	        System.out.println(orderQty+totalPrice);
			
			uo.setStatus("주문완료");		// api로 받아오게 수정
			uo.setPdate(Date.valueOf(LocalDate.now()));
			uo.setTotalPrice(totalPrice);
			uo.setProductId(productId);
			uo.setOrderQty(orderQty);
			uo.setOrderResult(0);
			uo.setUserid(userid);
			uoList.add(uo);
			
			// 카트에서 삭제
			int delcnum = Integer.parseInt((String)_uo.get("delcnum"));
			cartrepo.deleteById(delcnum);
			
		}
		List<UsersOrder> savedUoList = uorepo.saveAll(uoList);
		
		// order info 테이블에 추가
		Map<String, Object> _oi = (Map<String, Object>) UsersOrderList.get("oi");
		List<OrderInfo> oiList = new ArrayList<>();
		
		String resName = (String)_oi.get("resName");
		String resAddress = (String)_oi.get("resAddress");
		int resPhone = Integer.parseInt((String)_oi.get("resPhone"));
		String resRequirement = (String)_oi.get("resRequirement");
		
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
	public List<Map<String,Object>> getUoList(){
		Authentication id = SecurityContextHolder.getContext().getAuthentication();
		String userid = id.getName();
		var query = new JPAQueryFactory(entityManager);
        QUsersOrder UO = QUsersOrder.usersOrder;
        QProduct PD = QProduct.product;
        QImgs IMG = QImgs.imgs;
        QSizes SIZE = QSizes.sizes;
        List<Tuple> resultList = query.select(UO.status, UO.totalPrice, UO.orderQty,
								        		UO.pdate, UO.orderResult, PD.productName,
								        		PD.productPrice, SIZE.size, IMG.imgSrc)
                                .from(UO)
                                .join(PD).on(UO.productId.eq(PD.productId))
                                .join(IMG).on(PD.productId.eq(IMG.productId))
                                .join(SIZE).on(PD.sId.eq(SIZE.sId))
                                .where(UO.userid.eq(userid))
                                .fetch();
        
        List<Map<String, Object>> uoList = new ArrayList<>();
        resultList.forEach(tuple -> {
        	
        	// 사이즈 변환 (숫자 -> 문자), (0:S, 1:M, 2:L, 3:XL, 4:XXL)
            String size = "";
            switch (tuple.get(SIZE.size)) {
                case 0: size = "S"; break;
                case 1: size = "M"; break;
                case 2: size = "L"; break;
                case 3: size = "XL"; break;
                case 4: size = "XXL"; break;
                default: size = "-"; break;
            }
            
            Map<String, Object> map = new HashMap<>();
            map.put("status", tuple.get(UO.status));
            map.put("totalPrice", tuple.get(UO.totalPrice));
            map.put("orderQty", tuple.get(UO.orderQty));
            map.put("pdate", tuple.get(UO.pdate));
            map.put("orderResult", tuple.get(UO.orderResult));
            map.put("productName", tuple.get(PD.productName));
            map.put("productPrice", tuple.get(PD.productPrice));
            map.put("size", size);
            map.put("imgSrc", tuple.get(IMG.imgSrc));
            
            
            uoList.add(map);
        });
        
		return uoList;
	}
}
















