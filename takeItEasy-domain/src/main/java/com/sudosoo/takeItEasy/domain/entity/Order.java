package com.sudosoo.takeItEasy.domain.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity

public class Order {
    @Id
    private Long id;
    private String orderOwner;
    private String shippingAddress;
    private String shippingMemo;
    @ElementCollection(targetClass = Product.class)
    @CollectionTable(name="order_products", joinColumns = @JoinColumn(name="order_id"))
    private List<Product> products;

    protected Order() {
    }

    public Order(String orderOwner, String shippingAddress, String shippingMemo) {
        this.orderOwner = orderOwner;
        this.shippingAddress = shippingAddress;
        this.shippingMemo = shippingMemo;
    }

    public Order of(String orderOwner, String shippingAddress, String shippingMemo){
        return new Order(orderOwner, shippingAddress, shippingMemo);
    }

    public void addProducts(List<Product> products) {
        this.products.addAll(products);
    }

    public Long getId() {
        return id;
    }
}
