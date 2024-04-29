package com.ez.market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ez.market.dto.Buyresult;
import com.ez.market.repository.BuyresultRepository;

@Service
public class BuyResultService 
{
	@Autowired
	Buyresult buyresult;
	@Autowired
	BuyresultRepository buyresultrepo;
	
	public boolean saveBuyResult(String userid,int productid) {
		buyresult.setProductId(productid);
		buyresult.setUserid(userid);
		return buyresultrepo.save(buyresult) !=null;
	}

}
