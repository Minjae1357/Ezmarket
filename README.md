# [EzMarket]

## 🛒 소개
**EzMarket**은 다양한 상품을 편리하게 구매할 수 있는 온라인 쇼핑몰입니다.  

## 📸 데모
![image](https://github.com/user-attachments/assets/e68db682-3ddb-418d-8ab7-efc9f60b3cf8)

---

## 📚 목차
1. [프로젝트 개요](#프로젝트-개요)
2. [기술 스택](#기술-스택)
3. [주요 기능](#주요-기능)
7. [문의](#문의)

---
 ## 프로젝트 개요
- **기간**: 2024.3.8 ~ 2024.5.8
- **목표**: 쇼핑몰을 판매 관리 하는것을 목표로 만들었습니다.
- **역할**: 백엔드/프론트
- **팀 구성**: 3명

---

## 기술 스택
- **프론트엔드**: JavaScript, HTML, CSS, jQuery, JSP, Thymeleaf
- **백엔드**: Java, Spring Boot, JPA, Spring Data JPA, QueryDSL, Spring Security
- **데이터베이스**: Oracle
- **도구/기타**: GitHub, Sourcetree

---

## 주요 기능
---

### 1. **사용자 로그인 페이지**  
![로그인페이지](https://github.com/user-attachments/assets/3ec23d07-a3d4-471e-9b20-367b944a656b)  

이 페이지에서는 사용자가 아이디와 비밀번호로 로그인할 수 있습니다. 로그인 폼은 `action="/doLogin"`과 `method="post"`로 설정되어 있으며, Spring Security의 `loginProcessingUrl("/doLogin")`과 연동됩니다. 사용자가 입력한 아이디와 비밀번호는 서버로 전송되어 인증 처리를 받습니다.

로그인 시 **`master`**라는 이름의 사용자가(Admin 권한을 가진 사용자) 로그인하면 **관리자 페이지**로 이동하며, 일반 사용자는 **상품 메인 페이지**로 리다이렉트됩니다. 이 처리는 **Spring Security의 Custom Success Handler**를 통해 관리됩니다.

#### CustomAuthenticationSuccessHandler 예시:
```java
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
```

### 관리 페이지  
![관리자 데시보드](https://github.com/user-attachments/assets/b2c61aa8-ada6-4f97-a11e-498b731335ab)  

### 일반 사용자 메인 페이지  
![일반메인페이지지](https://github.com/user-attachments/assets/e68db682-3ddb-418d-8ab7-efc9f60b3cf8)  

---
   
 2. **회원가입**  
   EzMarket에서는 두 가지 방식으로 회원가입이 가능합니다:
---

   ### (1). 일반 이메일 회원가입  
   사용자는 이메일과 비밀번호를 입력하여 일반적인 방식으로 회원가입할 수 있습니다. 이 방식은 **이메일 인증**을 통해 보안을 강화하며, 인증된 이메일만 회원가입이 완료됩니다.
   
   ![회원가입페이지](https://github.com/user-attachments/assets/6beeeb5d-8beb-4cbb-bbab-a3fa8013535d)  
   사용자가 이메일을 입력하고 '이메일 확인' 버튼을 클릭하면, 아래와 같이 **코드 입력 필드와 확인 버튼**이 나타납니다.
   
   ![회원가입용이메일넣기](https://github.com/user-attachments/assets/ec2a4239-a3a2-4a92-a8b7-31f896dd59cc)  
   이메일로 발송된 인증 코드를 확인한 후, EzMarket 회원가입 페이지에 돌아와 코드를 입력하면 **이메일 인증이 완료**됩니다.
   
   ![네이버인증코드](https://github.com/user-attachments/assets/21e22912-e2b9-41ae-aae2-2c267cc49d36)  
   사용자가 네이버 등 이메일 서비스로부터 받은 인증 코드를 입력합니다.
   
   ![이메일인증확인](https://github.com/user-attachments/assets/9afd1a27-cf5a-481b-9273-41d5f24b17d9)  
   코드가 올바르게 입력되면, **이메일 인증이 성공적으로 완료**됩니다.
   
   ### (2). 구글 계정 연동  
   사용자는 **구글 OAuth2 인증**을 통해 구글 계정으로 간편하게 회원가입할 수 있습니다. 이를 통해 별도의 비밀번호 설정 없이 빠르고 안전하게 인증할 수 있습니다.
   
   ![로그인폼에서 구글로그인 눌럿을때나타나는곳](https://github.com/user-attachments/assets/9ba354f5-de65-4d5e-85c9-9869f0e302bb)  
   로그인 폼에서 '구글 로그인' 버튼을 클릭하면, **구글 로그인 화면**이 나타나며, 사용자는 구글 계정으로 인증을 진행할 수 있습니다.
   
   ![구글로그인으로 회원가입들어가기](https://github.com/user-attachments/assets/ca2dd108-6184-4124-ab50-df3ccc303c28)  
   구글 계정으로 인증이 완료되면 EzMarket과 연동되어 이메일인증이 완료됩니다.

---


### 3. **쇼핑몰 페이지**
EzMarket은 JPA와 QueryDSL을 사용하여 대부분의 데이터를 불러오고 있습니다. 사용자는 다양한 상품을 확인하고, 쇼핑몰 내에서 여러 기능을 활용할 수 있습니다.

**1. 상품 리스트 및 상세 페이지**  
상품 리스트는 QueryDSL을 통해 검색 및 필터링이 가능하며, 사용자는 원하는 상품을 클릭하여 상세 정보를 확인할 수 있습니다.  
![list](https://github.com/user-attachments/assets/c203bc54-e85d-4eb0-bb12-5d9960a1b5e5)  
![detail](https://github.com/user-attachments/assets/ec44360a-40d4-4602-b43d-6a70a3abb686)

**2. 쇼핑몰 주요 아이콘**  
화면 상단에는 사용자가 빠르게 접근할 수 있는 주요 아이콘이 배치되어 있습니다.  
![아이콘](https://github.com/user-attachments/assets/560ec1a1-5c45-4111-ae76-1e000c6f917e)  
- **장바구니**: 선택한 상품을 장바구니에 추가하여 한 번에 결제할 수 있습니다.  
- **마이페이지**: 사용자 정보와 주문 내역을 확인할 수 있는 페이지입니다.  
- **로그아웃**: 사용자가 시스템에서 안전하게 로그아웃할 수 있습니다.  

**3. 마이페이지**  
마이페이지에서는 사용자 정보와 함께 주문 내역을 관리할 수 있습니다.  
![마이페이지](https://github.com/user-attachments/assets/84d8e3ed-d2fc-4128-9ded-afe3fd854904)  
- **주문 내역**: 사용자는 이전에 주문한 내역을 조회하고, 상태를 확인할 수 있습니다.  
![주문내](https://github.com/user-attachments/assets/ebe49d71-9747-42be-83e0-d6158f9f42f3)

---
  
5. **장바구니**
장바구니는 쇼핑몰메인페이지에서 상품을 선택한후에 장바구니에 담으면됨 이거는 순서가 물품을선택하고 장바구니에담으면 
![스크린샷 2024-11-09 134213](https://github.com/user-attachments/assets/e9b4ddad-598c-48f9-86a9-3fb30f59ba7b)
장바구니 카운트가 1오르고
![스크린샷 2024-11-09 134225](https://github.com/user-attachments/assets/7ecbea43-8953-4f41-83f8-6723771e2694)
여기서 사이즈를 선택하고
![스크린샷 2024-11-09 134230](https://github.com/user-attachments/assets/b023a705-f05b-4aba-af3e-960d82d9035a)
여기서 상세정보를 입력하고
![스크린샷 2024-11-09 134245](https://github.com/user-attachments/assets/597f73cf-f4e8-44d9-8380-dd5afb6b041e)
마이페이지에 주문내역에서확인이가능하다
![스크린샷 2024-11-09 134408](https://github.com/user-attachments/assets/f5ab5163-1d87-45de-b5c5-fa45510cfd39)
여기서는 jpa와 쿼리디에스엘을사용해서 정보를보여주고 받아온정보를 분할해서 데이터베이스에 저장하는방식으로했습니다



6. **주문 관리**  




---

## 문의
- **이름**: [정민재]
- **이메일**: [vovodla74@gmail.com]
- **GitHub**: [https://github.com/Minjae1357]
