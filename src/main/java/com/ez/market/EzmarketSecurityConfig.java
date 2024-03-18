package com.ez.market;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
public class EzmarketSecurityConfig
{
	@Autowired
	private DataSource dataSource;		// 자바에서 지원하는 dataSource 클래스. 이 dataSource로 아래에서 인증
	
	//Enable jdbc authentication
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
       System.out.println("데이터소스 설정");
        auth.jdbcAuthentication().dataSource(dataSource);		// 데이터베이스 정보를 연결?
    }
    
   @Bean
   BCryptPasswordEncoder  passwordEncoder()
   {   /* 매번 다른 인코딩값을 생성하며, 생성된 인코딩 값은 내부에서 결국 hashpw()가 리턴한 
      값의 비교에 최종적으로 패스워드 일치 여부가 결정된다 */ 
      BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
      
      System.out.println("guest->" + enc.encode("guest"));
      System.out.println("user->" + enc.encode("user"));
      System.out.println("admin->" + enc.encode("admin"));
      System.out.println("master->" + enc.encode("master"));
      
       return enc;
   } 

    @Bean
    WebSecurityCustomizer webSecurityCustomizer() 
    {
       return (webSecurity) -> webSecurity.ignoring().requestMatchers("/resources/**", "/ignore2");
    }

    
    @Bean
   SecurityFilterChain filterChain(HttpSecurity http) throws Exception 
    {
       System.out.println("접근제한 설정");
       //HttpSecurity의 설정 메소드들은 HttpSecurity의 참조를 리턴하므로 각 항목 설정시 Chain Action을 사용할 수 있다
       //각 설정 메소드 내에서도 Chain Action을 사용하는 구조로 되어 있다
      http.authorizeHttpRequests(authz -> authz
                .requestMatchers("/", "/users/", "/users/loginForm", "/logout").permitAll()


                //.anyRequest().authenticated()  // 위의 설정 이외의 모든 요청은 인증을 거쳐야 한다
                //anyReqeust().denyAll();        // 위의 설정 이외의 모든 요청은 거부한다
                .anyRequest().permitAll()       // 위의 설정 이외의 모든 요청은 인증 요구하지 않음
         ).csrf( csrfConf -> csrfConf.disable()
        		 //csrfConf -> csrfConf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            ).formLogin(loginConf -> loginConf.loginPage("/users/loginForm")   // 안되면 이동
                .loginProcessingUrl("/doLogin")            // 컨트롤러 메소드 불필요(내부에서 알아서 처리), ★★★★★ 폼 action과 일치해야 함 ★★★★★
                .failureUrl("/users/loginForm")      // 로그인 실패시 이동 경로(컨트롤러 메소드 필요함), (에러 알림?)
                //.failureForwardUrl("/login?error=Y")  //실패시 다른 곳으로 forward
                .defaultSuccessUrl("/users/info", true)	// 로그인 성공시 이동
                .usernameParameter("id")  // 로그인 폼에서 이용자 ID 필드 이름, 디폴트는 username
                .passwordParameter("pw")  // 로그인 폼에서 이용자 암호 필트 이름, 디폴트는 password
                .permitAll()
            ).logout(logoutConf -> logoutConf.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //로그아웃 요청시 URL(내부에서 처리)
                //.logoutSuccessUrl("/sec/loginForm?logout=T")	// 해당 기능 컨트롤러에 등록 필요
            	.logoutSuccessUrl("/boardsec/list")	// 해당 기능 컨트롤러에 등록 필요
                .invalidateHttpSession(true)	// 세션 무효화
                .deleteCookies("JSESSIONID")	// 쿠키도 지운다
                .permitAll()					// 로그아웃 하느건 모두 허용
            ).exceptionHandling(exConf -> exConf.accessDeniedPage("/boardsec/list"));		// 에러난 페이지 보여주면 안된다

         return http.build();
    }


    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception 
    {
        authenticationMgr.inMemoryAuthentication() // 메모리 기반 인증(Authentication) 
        // 아래 내용은 DB에 들어갈 예정, 메모리에 이사람 아이디와 패스워드를 ..., (아이디 : employee, 암호 ..., 로그인 정보)
        .withUser("guest").password("$2a$10$Xl7UFgrDtalkyNYnOjeSNelrhp6YzJEN5nbIIs0vdD7Drk6WG25YK")	// employee 문자열을 암호화 한 것
            .authorities("ROLE_GUEST")
        .and()
        .withUser("user").password("$2a$10$TKxl0N1iPxsrLMmeregqgOG353hlFaKGq5lniqSw2ZlEr9LeRbrW2")
            .authorities("ROLE_USER","ROLE_GUEST")
        .and()
        .withUser("admin").password("$2a$10$GxRn1pzWP/RV3qJAUMsYrOK/285f4mcljH9gNDoEHa04s.x78VWV2")
            .authorities("ROLE_ADMIN","ROLE_USER","ROLE_GUEST")
        .and()
        .withUser("master").password("$2a$10$a1E.cuvTL3PpE1NnA.fSbOrBw.9YlGiKKZv1m3.G7n3tmyXlybB36")
            .authorities("ROLE_MASTER","ROLE_ADMIN","ROLE_USER","ROLE_GUEST");
    }
    
}










