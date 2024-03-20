package com.ez.market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import com.ez.market.dto.Users;
import com.ez.market.repository.UsersRepository;

@Service
public class UsersService  
{
	@Autowired
	UsersRepository userrepo;
	
	public boolean IdCheck(String userid) {
		Optional<Users> userOptional = userrepo.findById(userid);
		boolean checkuserid = userOptional.isPresent();
		return checkuserid;
	}
}
