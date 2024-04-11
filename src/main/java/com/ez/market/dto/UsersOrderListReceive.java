package com.ez.market.dto;

import java.util.List;

import org.springframework.lang.NonNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class UsersOrderListReceive {
	
	@Id
    private List<OrderRecive> uoList;
    private OrderInfoRecive oi;

    @Data
    public static class OrderRecive {
        private String productId;
        private String orderQty;
        private String totalPrice;
        private String delcnum;
    }
    
    @Data
    public static class OrderInfoRecive {
        private String resName;
        private String resAddress;
        private String resPhone;
        private String resRequirement;
    }
}