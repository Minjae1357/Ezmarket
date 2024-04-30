package com.ez.market.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ez.market.dto.*;
import com.ez.market.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("productboard")
public class ProductBoardController
{
	@Autowired
	ProductBoardService PBSvc;
	@Autowired
	ProductService pSvc;
	@Autowired
	ImgsService imgSvc;
	@Autowired
	SizeService sizeSvc;
	@Autowired
	CategoryService cateSvc;
	@Autowired
	BrandsService brandSvc;
	@Autowired
	CartService cartSvc;
	@Autowired
	CategoryService categorySvc;
	@Autowired
	ComentsService comentsSvc;
	
	@GetMapping("list")
	public String productBoard(Model m) 
	{
		List<ProductBoard> pblist = PBSvc.findTop30ByOrderByPnumDesc();
		System.out.println("pblist" + pblist);
		List<Product> plist = pSvc.findProductsByPnums(pblist);
		List<Imgs> ilist = imgSvc.findImgsByPId(plist);
		m.addAttribute("productBoard",pblist);
		m.addAttribute("product",plist);
		m.addAttribute("img",ilist);
		return "product/productMainList";
	}

	@GetMapping("list/{cKind}")
	public String getSelectList(Model m,@PathVariable("cKind")String ckind)
	{
		int count = cartSvc.cartCount();
		List<Integer> clist = categorySvc.findcNumBycKind(ckind);
		List<Product> plist = pSvc.findBycNum(clist);
		List<ProductBoard>  pblist = PBSvc.findByPnum(plist);
		List<Imgs> ilist = imgSvc.findImgsByPId(plist);
		m.addAttribute("productBoard",pblist);
		m.addAttribute("product",plist);
		m.addAttribute("img",ilist);
		m.addAttribute("count" ,count);
		return "product/productSearchList";
	}
	@GetMapping("listTop")
	public String getTop(Model m) {
		List<ProductBoard> pblist = PBSvc.findTop30ByOrderByPnumDesc();
		List<Product> plist = pSvc.findTopList();
		List<Imgs> ilist = imgSvc.findImgsByPId(plist);
		m.addAttribute("productBoard",pblist);
		m.addAttribute("product",plist);
		m.addAttribute("img",ilist);
		return "product/productMainList";
	}
	
	@GetMapping("detail/{pId}")
	public String productDetail(Model m,@PathVariable("pId") int pid) {
		int count = cartSvc.cartCount();
		Product plist = pSvc.findByProductId(pid);
		List<Imgs> ilist = imgSvc.findAllByProductId(pid);
		List<Sizes> slist = sizeSvc.findByProductId(pid);
		Category cate = cateSvc.findBycNum(plist.getCNum());
		Brands brand = brandSvc.findByBrandId(plist.getBrandId());
		String mainImageSrc = ilist.get(0).getImgSrc();
		List<Coments> conlist =comentsSvc.findByPnum(plist.getPnum());
		m.addAttribute("product",plist);
	    m.addAttribute("mainImageSrc", mainImageSrc);
		m.addAttribute("img",ilist);
		m.addAttribute("Size",slist);
		m.addAttribute("category",cate);
		m.addAttribute("brand",brand);
		m.addAttribute("count" ,count);
		m.addAttribute("coments",conlist);
		return"product/productDetail";
	}
	
	@GetMapping("CartOverlapCheck")
	@ResponseBody
	public Map<String,Boolean> cartOverlapCheck(@RequestParam("productId")int productid){
		
		boolean overlap = cartSvc.CartOverlapCheck(productid);
		Map<String,Boolean> map = new HashMap<>();
		map.put("overlap",overlap);
		return map;
	}
	
	@PostMapping("addCart")
	@ResponseBody
	public Map<String,Boolean> addCart(@RequestParam("productId")int productid){
		
		boolean ox = cartSvc.addCart(productid);
		Map<String,Boolean> map = new HashMap<>();
		System.out.println(ox);
		map.put("result", ox);
		return map;
	}
	
	
	
}
