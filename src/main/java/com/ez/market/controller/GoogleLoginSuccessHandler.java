package com.ez.market.controller;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ez.market.dto.Authorities;
import com.ez.market.dto.Users;
import com.ez.market.repository.UsersRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("GoogleLoginSuccessHandler")
public class GoogleLoginSuccessHandler implements AuthenticationSuccessHandler 
{
   @Autowired
   private UsersRepository userRepo; 
   @Override
   public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
         Authentication authentication) throws IOException, ServletException {
      log.info("구글 로그인 성공");
      String redirectUrl = null;
      if(authentication.getPrincipal() instanceof DefaultOAuth2User) {
         DefaultOAuth2User userDetails = (DefaultOAuth2User) authentication.getPrincipal();
         String email = userDetails.getAttribute("email");
         String username = email !=null ? email : userDetails.getAttribute("login")+"@gmail.com";
         if(userRepo.findByEmail(username)==null) { //등록되지 않은 이용자일 경우 DB에 저장
            Users u = new Users();
            Authorities au = new Authorities();
            u.setEmail(email);
            String role = email.equals("특정 이메일 입력") ? "ROLE_ADMIN" : "ROLE_USER";
            au.setAuthority(role);
            Users saved = userRepo.save(u);
            log.info(saved!=null ? "사용자 추가 성공":"사용자 추가 실패");
         }
      }
      redirectUrl = "/user/loginForm";
      new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
   }

}