package com.ez.market.controller;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ez.market.dto.Authorities;
import com.ez.market.dto.TempTable;
import com.ez.market.dto.Users;
import com.ez.market.repository.TempTableRepository;
import com.ez.market.repository.UsersRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("GoogleLoginSuccessHandler")
public class GoogleLoginSuccessHandler implements AuthenticationSuccessHandler 
{
   @Autowired
   private UsersRepository userRepo;
   @Autowired
   private TempTableRepository temptablerepository;
   @Override
   public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
         Authentication authentication) throws IOException, ServletException {
      log.info("구글 로그인 성공");
      String redirectUrl = null;
      if(authentication.getPrincipal() instanceof DefaultOAuth2User) {
         DefaultOAuth2User userDetails = (DefaultOAuth2User) authentication.getPrincipal();
         String email = userDetails.getAttribute("email");
         String username = email !=null ? email : userDetails.getAttribute("login")+"@gmail.com";
         Users existingUser = userRepo.findByEmail(username);
         if(existingUser == null) { // 등록되지 않은 이용자일 경우 DB에 저장
            TempTable temptable = new TempTable();
            temptable.setEmail(email);
            TempTable saved = temptablerepository.save(temptable);
            log.info(saved != null ? "사용자 추가 성공" : "사용자 추가 실패");
            redirectUrl = "/user/googleRegister?useremail=" + email;
         } else {
            // 이미 존재하는 계정인 경우에 대한 처리
            log.info("이미 존재하는 계정입니다.");
            HttpSession session = request.getSession();
            session.setAttribute("message", "이미 존재하는 계정입니다."); // 메시지를 세션에 저장
            redirectUrl = "/user/loginForm"; // 로그인 페이지로 리다이렉트
         }
      }
      new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
   }


}