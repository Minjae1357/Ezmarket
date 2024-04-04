package com.ez.market.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ez.market.dto.Colors;
import com.ez.market.repository.ColorsRepository;

@Service
public class ColorsService {
	@Autowired
	ColorsRepository colorRepo;
	
	public List<Colors> getAllColors() {
        return colorRepo.findAll();
    }

}
