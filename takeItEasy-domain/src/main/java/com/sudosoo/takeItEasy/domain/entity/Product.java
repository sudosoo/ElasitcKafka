package com.sudosoo.takeItEasy.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.Getter;

// 임시 상품 클래스
@Data
@Embeddable
public class Product {
    private String productId;
    private String productName;
    private int quantity;

    protected Product() {
    }

    public Product(String productId, String productName, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
    }
}

