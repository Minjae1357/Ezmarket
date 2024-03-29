package com.ez.market.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ez.market.dto.Users;
public interface UsersRepository extends JpaRepository<Users,String> 								
{ 
	Users findByEmail(String useremail);
}
  