package com.sudosoo.takeItEasy.domain.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// 임시 상품 클래스
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

