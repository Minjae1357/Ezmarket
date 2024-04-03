package com.ez.market.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collection;

@Slf4j
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // 사용자의 권한 체크
        Collection<?> authorities = authentication.getAuthorities();
        boolean isAdmin = authorities.stream()
                .anyMatch(grantedAuthority -> grantedAuthority.toString().equals("ROLE_ADMIN"));
        System.out.println(authorities);
        log.info("권한 : " + authorities);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(principal);
        if (isAdmin) {
            // 관리자 권한을 가진 사용자일 경우 관리자 페이지로 리다이렉트
            response.sendRedirect("/admin/dashboard");
        } else {
            // 일반 사용자일 경우 메인 페이지로 리다이렉트
            response.sendRedirect("/main/menu");
        }
    }
}
