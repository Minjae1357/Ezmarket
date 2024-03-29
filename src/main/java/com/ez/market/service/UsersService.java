package com.ez.market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

import com.ez.market.dto.Authorities;
import com.ez.market.dto.TempTable;
import com.ez.market.dto.Users;
import com.ez.market.dto.UsersGenderAge;
import com.ez.market.repository.AuthoritiesRepository;
import com.ez.market.repository.TempTableRepository;
import com.ez.market.repository.UsersGenderAgeRepository;
import com.ez.market.repository.UsersRepository;

import jakarta.transaction.Transactional;

@Service
public class UsersService  
{
	@Autowired
 	private BCryptPasswordEncoder passwordEncoder; 
	@Autowired
	private Authorities authorities;
	@Autowired
	private TempTable temptable;
	@Autowired
	private UsersRepository userrepo;
	@Autowired
	private TempTableRepository temptablerepository;
	@Autowired
	private UsersGenderAgeRepository usersgenderagereposiroty;
	@Autowired
	private AuthoritiesRepository authoritiesrepository;
	
	
	public boolean tempEmailSave(String userEmail) {
		temptable.setEmail(userEmail);
		return temptablerepository.save(temptable)!=null;
	}
	
	
	
	public boolean idCheck(String userid) {
		Optional<Users> userOptional = userrepo.findById(userid);
		System.out.println(userOptional);
		boolean checkuserid = userOptional.isPresent();
		System.out.println(checkuserid);
		return checkuserid;
	} 
	public boolean usersEmailCheck(String email) {
		return userrepo.findByEmail(email) ==null;
	}
	
	public boolean tempEmailCheck(String email) 
	{
		return temptablerepository.findByEmail(email)!=null;
	}
	@Transactional
	public boolean userSave(Users user) 
	{
		String encryptPassword = passwordEncoder.encode(user.getUserpwd());
		user.setUserpwd(encryptPassword);
		java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis()); //나중에 최신거로바꾸어야함
		user.setRegdate(currentDate);
		user.setEnabled("y");
		return userrepo.save(user) != null;
	}
	@Transactional
	public void userAuthority(String userid,String useremail) 
	{
		authorities.setUserid(userid);
		authorities.setAuthority("ROLE_USER");
		authoritiesrepository.save(authorities);
		temptablerepository.deleteByEmail(useremail);
	}
	public void userGenderAgeSave(UsersGenderAge genderage) {
		usersgenderagereposiroty.save(genderage);
	}
	
}