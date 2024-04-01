package com.ez.market.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ez.market.dto.QAuthorities;
import com.ez.market.dto.QUsers;
import com.ez.market.dto.UserDetails;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;


@Service
public class AdminService 
{
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<UserDetails> userList() {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QUsers qUsers = QUsers.users;// 자동생성된 q클래스
		QAuthorities qAuthorities = QAuthorities.authorities;
		
		List<UserDetails> user = queryFactory
				.select(Projections.constructor(UserDetails.class,
						qUsers.userid,
						qUsers.username,
						qUsers.email,
						qUsers.phone,
						qUsers.regdate,
						qUsers.enabled,
						qAuthorities.authority.substring(5))) //앞에 5자를뺴고 가져옴 
				.from(qUsers)
				.join(qAuthorities).on(qUsers.userid.eq(qAuthorities.userid))
				.where(qUsers.userid.ne("master")) //master제외
				.fetch();
		return user;
		
	}
	@Transactional
	public boolean updateEnabled(String userid,String enabled) 
	{
		System.out.println("userid" + userid);
		System.out.println(enabled);
		QUsers user = QUsers.users;// 자동생성된 q클래스
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		long executed = queryFactory.update(user)
									.set(user.enabled, enabled)
									.where(user.userid.eq(userid))
									.execute();
		return executed>0;
	}
}
