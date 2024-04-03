package com.ez.market.controller;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	 @Override
	    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
	            AuthenticationException exception) throws IOException, ServletException {
	        // 로그인 실패 처리를 여기에 구현합니다.
	        // 예를 들어, 실패 이유를 로그로 기록하고 실패한 URL로 리다이렉트할 수 있습니다.
	        System.out.println("로그인 실패: " + exception.getMessage());
	        request.setAttribute("errorReason", exception.getMessage());
	        response.sendRedirect("/user/loginForm?error=T");
	    }
}

	