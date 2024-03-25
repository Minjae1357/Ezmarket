package com.ez.market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ez.market.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	ProductRepository productRepo;

}
