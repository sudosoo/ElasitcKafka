package com.sudosoo.takeItEasy.domain.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class Product {
    private String productId;
    private int quantity;

    protected Product() {
    }

    public Product(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}

