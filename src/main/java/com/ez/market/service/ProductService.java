package com.ez.market.service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;


import org.hibernate.query.criteria.JpaExpression;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ez.market.dto.Category;
import com.ez.market.dto.Imgs;
import com.ez.market.dto.Product;
import com.ez.market.dto.ProductBoard;
import com.ez.market.dto.QProduct;
import com.ez.market.dto.ProductList;

import com.ez.market.dto.QImgs;
import com.ez.market.dto.QOrderInfo;
import com.ez.market.dto.QSizes;
import com.ez.market.dto.QUsersOrder;
import com.ez.market.dto.UsersOrderList;

import com.ez.market.repository.CategoryRepository;
import com.ez.market.repository.ImgsRepository;
import com.ez.market.repository.ProductRepository;
import com.querydsl.core.types.Projections;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class ProductService {
	@Autowired
	ProductRepository productRepo;
	@Autowired
	ProductList productlist;
	@Autowired
	ImgsRepository imgsrepo;
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private CategoryRepository cateRepo;
	
	@Transactional
	public List<UsersOrderList> getUsersOrderList() {
	    JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
	    QUsersOrder userorder = QUsersOrder.usersOrder;
	    QProduct product = QProduct.product;
	    QImgs imgs = QImgs.imgs; 

	    List<UsersOrderList> list = queryFactory
	            .select(Projections.constructor(UsersOrderList.class,	                 
	                    product.productName,
	                    userorder.userid,
	                    userorder.pdate,
	                    userorder.oNum,
	                    userorder.totalPrice,
	                    userorder.sizeNum,
	                    userorder.orderQty,
	                    userorder.status,
	                    imgs.imgSrc))
	            .from(userorder)
	            .join(product).on(userorder.productId.eq(product.productId))
	            .join(imgs).on(product.productId.eq(imgs.productId))
	            .where(userorder.userid.ne("master")) // 예시 조건
	            .fetch();

	    return list;
	}

	

	public int savePrduct(Product p,int pnum){
		try {
			p.setPnum(pnum);
			Product pro = productRepo.save(p);
			return pro.getProductId();
		}catch(Exception e) {
			e.printStackTrace(); 
			throw new RuntimeException("Failed to save Product");
		}
		
	}

	public List<Product> findProductsByPnums(List<ProductBoard> pbList) {
        List<Integer> pnumList = pbList.stream()
                                       .map(ProductBoard::getPnum)
                                       .collect(Collectors.toList());
        return productRepo.findProductsByPnumIn(pnumList);
    }
	
    
	
	public List<Product> getList(){
		return productRepo.findAll();
	}
	
	public List<ProductList> getProductList(){
	    List<Product> list = getList(); // 상품 목록을 가져오는 메소드 호출
	    List<ProductList> plist = new ArrayList<>(); // 최종 상품 목록을 담을 리스트 생성

	    for(Product p : list) {
	        // 각 상품에 대해 새로운 ProductList 객체를 생성
	        ProductList productlist = new ProductList();
	        
	        // 상품에 해당하는 이미지 정보 가져오기
	        List<Imgs> imgs = imgsrepo.findAllByProductId(p.getProductId());
	        
	        // ProductList 객체에 상품 정보 설정 
	        productlist.setProductName(p.getProductName());
	        productlist.setImgName(imgs.get(0).getImgSrc());
	        productlist.setPrice(p.getProductPrice());
	        
	        // 설정된 ProductList 객체를 리스트에 추가
	        plist.add(productlist);
	    }
	    System.out.println("리스트 불러오는거임" + plist);
	    System.out.println(plist); // 생성된 상품 목록 출력
	    return plist; // 최종 상품 목록 반환
	}
	
	public Product findByProductId(int pid){
		return productRepo.findByProductId(pid);
	}
	public List<Category> getTop(String kind){
		return cateRepo.findBycKind(kind);
	}
	public List<Product> findTopList(){
		List<Product> plist = new ArrayList<>();
		List<Category> clist = getTop("상의");
		for(Category c : clist) {			
			plist = productRepo.findBycNum(c.getCNum());			
		}
		System.out.println("plist" + plist);
		return plist; 
	}

	public List<Product> findBycNum(List<Integer> clist){
		List<Product> plist = new ArrayList<>();
		for(Integer cNum : clist){
			List<Product> searchlist = productRepo.findBycNum(cNum);
			plist.addAll(searchlist);
		}
		return plist;
	}
	public int findProductId(String productName) {
		return productRepo.findByProductName(productName).getProductId();
	}

    public Product findByProductName(String productName) {
		return productRepo.findByProductName(productName);
    }
}
