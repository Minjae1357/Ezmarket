package com.ez.market.controller;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
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

	@Qualifier("GoogleLoginSuccessHandler")
	@Autowired
	AuthenticationSuccessHandler googleLoginSuccessHandler;

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
		log.info("접근제한 설정");
		log.info("customSuccessHandler:" + googleLoginSuccessHandler);
	    http.authorizeHttpRequests((authz) -> authz
	            .requestMatchers("/login/oauth2/code/google","/user/login","/user/check","/","/register","/auth/{code}","/sec/", "/user/loginForm", "/sec/denied", "/logout", "/sec/menu").permitAll()
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
	            .ignoringRequestMatchers("/product/addBrand")
	    )
	    .oauth2Login(oauth2Config -> oauth2Config.loginPage("/user/login")
	    		.successHandler(googleLoginSuccessHandler)
	    		.failureUrl("/login?error=T")
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



}