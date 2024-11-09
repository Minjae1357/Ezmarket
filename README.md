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
  
장바구니 설명을 아래와 같이 수정해보았습니다:

---

### 5. **장바구니**  
장바구니 기능은 쇼핑몰 메인 페이지에서 사용자가 원하는 상품을 선택한 후, 장바구니에 담는 것으로 시작됩니다.

1. **상품 선택 및 장바구니 추가**  
   사용자가 상품을 선택하고 장바구니에 담으면, 장바구니 카운트가 1 증가하게 됩니다.  
   ![장바구니 추가](https://github.com/user-attachments/assets/e9b4ddad-598c-48f9-86a9-3fb30f59ba7b)

2. **사이즈 선택**  
   장바구니에 담긴 상품에서 사용자는 원하는 사이즈를 선택할 수 있습니다.  
   ![사이즈 선택](https://github.com/user-attachments/assets/7ecbea43-8953-4f41-83f8-6723771e2694)

3. **상세 정보 입력**  
   사용자는 상품의 추가 옵션이나 상세 정보를 입력할 수 있습니다.  
   ![상세 정보 입력](https://github.com/user-attachments/assets/b023a705-f05b-4aba-af3e-960d82d9035a)

4. **마이페이지에서 주문 내역 확인**  
   장바구니에 담긴 상품은 주문이 완료된 후, 마이페이지의 주문 내역에서 확인할 수 있습니다.  
   ![주문 내역 확인](https://github.com/user-attachments/assets/597f73cf-f4e8-44d9-8380-dd5afb6b041e)

장바구니 및 주문 내역 기능은 JPA와 QueryDSL을 사용하여 데이터를 조회 및 처리하며, 필요한 정보를 데이터베이스에서 분할하여 저장하는 방식을 채택하였습니다.

---


여기 내용을 아래와 같이 정리했습니다:

---

### 6. **관리자 페이지 및 주문 관리**  
관리자 페이지에서는 사용자 관리 및 다양한 상품 구성 요소(프로덕트, 컬러, 브랜드 등)를 추가하고 관리할 수 있습니다.

1. **사용자 관리**  
   관리자는 사용자를 관리할 수 있으며, 다음과 같이 구성되어 있습니다:  
   ![사용자 관리 페이지](https://github.com/user-attachments/assets/64bff641-a717-472b-8c63-7f4cb749eff5)  
   
   - **디테일 보기**: 특정 사용자의 디테일을 조회할 수 있으며, 다음과 같은 화면이 제공됩니다:  
     ![사용자 디테일 보기](https://github.com/user-attachments/assets/8d407ac9-0aeb-4958-ba8a-e89197bde7c7)  
   
   - **비활성화 기능**: 관리자가 '비활성화' 버튼을 클릭하면 해당 사용자는 로그인할 수 없게 됩니다. 이 기능은 사용자 테이블의 `ENABLED` 값을 'Y'에서 'N'으로 변경하는 방식으로 구현되었으며, 이벤트 핸들러를 통해 즉각적으로 적용됩니다.

2. **주문 관리**  
   관리자 페이지에서는 사용자 주문 내역에 대한 모든 정보를 확인할 수 있습니다.  
   - **주문 내역 조회**: JPA를 사용하여 사용자 주문 내역을 간단하게 불러옵니다.  
     ![주문 내역](https://github.com/user-attachments/assets/924edc01-b071-4eac-a0f1-2992f2da72d8)  
   
   - **주문 상태 변경**: 관리자는 주문 상태를 변경할 수 있으며, 상품을 발송한 경우 상태를 변경하도록 설정되어 있습니다.  
     ![주문 상태 변경](https://github.com/user-attachments/assets/54640206-2a4c-47e1-974b-e9bc2120886e)  

3. **프로덕트 관리**  
   관리자 페이지에서는 컬러, 브랜드, 카테고리 등의 상품 정보를 추가할 수 있으며, 상품에 대한 상세 정보를 관리할 수 있습니다.  
   - **프로덕트 리스트**: 관리자가 저장한 프로덕트에 대한 정보를 확인할 수 있습니다.  
     ![프로덕트 리스트](https://github.com/user-attachments/assets/ebfd8ab0-cba2-46ab-b85d-c088833cb004)  
   
   - **사이즈 및 수량 조절**: 특정 프로덕트를 클릭하면 해당 상품의 정보를 확인할 수 있으며, 사이즈와 수량을 조절할 수 있습니다.  
     ![사이즈 및 수량 조절](https://github.com/user-attachments/assets/564a6ccd-c2a5-472e-9e66-a64a9e5b4d82)  

   - **상품 추가**: 관리자는 새로운 상품을 추가할 수 있습니다.  
     ![상품 추가](https://github.com/user-attachments/assets/465bbcbf-1fc8-43b7-9cd9-8af99725ce45)  
     입력된 정보는 데이터베이스에 분할 저장되는 과정을 거칩니다.

---

## 문의
- **이름**: [정민재]
- **이메일**: [vovodla74@gmail.com]
- **GitHub**: [https://github.com/Minjae1357]
