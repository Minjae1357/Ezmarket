package com.ez.market.controller;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
public class SimpleSecurityConfig {
	
	@Autowired
	private DataSource dataSource; //데이터베이스를 관리해줌
	//Enable jdbc authentication
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
	    auth.jdbcAuthentication()
	        .dataSource(dataSource)
	        .usersByUsernameQuery("SELECT userid, userpwd, enabled FROM users WHERE userid=?")
	        .authoritiesByUsernameQuery("SELECT userid, authority FROM authorities WHERE userid=?")
	        .passwordEncoder(new BCryptPasswordEncoder());
	}


	
	 @Autowired
	    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
	
	@Bean
	@Lazy
	BCryptPasswordEncoder passwordEncoder() { 
		
		BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
		//이거는 암호화하는거
		System.out.println("master->" + enc.encode("master"));
		System.out.println("blake->" + enc.encode("blake"));
		System.out.println("jones->" + enc.encode("jones")); 

 
		return enc;
	}

	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (webSecurity) -> webSecurity.ignoring().requestMatchers("/resources/**", "/ignore2");
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    System.out.println("접근제한 설정");
	    
	    http.authorizeHttpRequests((authz) -> authz
	            .requestMatchers("/user/check","/","/register","/auth/{code}","/sec/", "/user/loginForm", "/sec/denied", "/logout", "/sec/menu").permitAll()
	            .requestMatchers("/register").hasAnyRole("USER", "ADMIN", "MASTER")
	            .requestMatchers("/auth/{code}").hasAnyRole("USER", "ADMIN", "MASTER")
	            .requestMatchers("/sec/list").hasAnyRole("USER", "ADMIN","MASTER")
	            .requestMatchers("/sec/detail").hasAnyRole("USER", "ADMIN", "MASTER")
	            .requestMatchers("/sec/addboard").hasAnyRole("USER", "ADMIN", "MASTER")
	            .requestMatchers("/sec/update").hasAnyRole("USER", "ADMIN", "MASTER")
	            .requestMatchers("/sec/delete").hasAnyRole("USER", "ADMIN","MASTER")
	            .requestMatchers("/sec/minoboard").hasAnyRole("ADMIN", "MASTER")
	            .requestMatchers("/items/additems").hasAnyRole("USER","ADMIN","MASTER")
	            .anyRequest().permitAll()
	    )
	    .formLogin(loginConf -> loginConf
	            .loginPage("/user/loginForm")
	            .loginProcessingUrl("/doLogin")
	            .failureHandler(customAuthenticationFailureHandler) 
	            .defaultSuccessUrl("/user/menu", true)
	            .usernameParameter("USERID")
	            .passwordParameter("USERPWD")
	            .permitAll()
	    )
	    .csrf(csrfConf -> csrfConf
	            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
	            .ignoringRequestMatchers("/user/auth")
	            .ignoringRequestMatchers("/user/register")
	            .ignoringRequestMatchers("/sec/auth/{code}")
	            .ignoringRequestMatchers("/login")
	            .ignoringRequestMatchers("/logout")
	            .ignoringRequestMatchers("/user/check")
	    )
	    .logout(logoutConf -> logoutConf
	            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	            .logoutSuccessUrl("/user/loginForm?logout=T")
	            .invalidateHttpSession(true)
	            .deleteCookies("JSESSIONID")
	            .permitAll()
	    )
	    .exceptionHandling(exConf -> exConf
	            .accessDeniedPage("/user/loginForm")
	    );

	    return http.build();
	}

	/*
	 * @Autowired public void configureGlobal(AuthenticationManagerBuilder
	 * authenticationMgr) throws Exception {
	 * authenticationMgr.inMemoryAuthentication() 메모리 기반 인증(Authentication)
	 * .withUser("employee").password(
	 * "$2a$10$MZ2ANCUXIj5mrAVbytojruvzrPv9B3v9CXh8qI9qP13kU8E.mq7yO") // 비밀번호
	 * employee .authorities("ROLE_USER") .and() .withUser("imadmin").password(
	 * "$2a$10$FA8kEOhdRwE7OOxnsJXx0uYQGKaS8nsHzOXuqYCFggtwOSGRCwbcK") // 비밀번호
	 * imadmin .authorities("ROLE_USER", "ROLE_ADMIN") .and()
	 * .withUser("guest").password(
	 * "$2a$10$ABxeHaOiDbdnLaWLPZuAVuPzU3rpZ30fl3IKfNXybkOG2uZM4fCPq") //비밀번호 guest
	 * .authorities("ROLE_GUEST") .and() .withUser("master").password(
	 * "$2a$10$MkWQu2mNglt7qA2M8xEkWOqjhjSICI6i34uzIIErDKH/SMhrjQWh6")
	 * .authorities("ROLE_USER", "ROLE_ADMIN", "ROLE_GUEST","ROLE_MASTER"); }
	 */

}