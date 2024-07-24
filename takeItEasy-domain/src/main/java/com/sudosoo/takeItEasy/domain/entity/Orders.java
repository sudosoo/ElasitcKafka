package com.sudosoo.takeItEasy.domain.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name="orders")
public class Orders {
    @Id
    private Long id;
    private String orderer;
    private String shippingAddress;
    private String shippingMemo;
    @ElementCollection(targetClass = Product.class)
    @CollectionTable(name="order_products", joinColumns = @JoinColumn(name="order_id"))
    private List<Product> products = new ArrayList<>();

    protected Orders() {
    }

    public Orders(String orderer, String shippingAddress, String shippingMemo) {
        this.orderer = orderer;
        this.shippingAddress = shippingAddress;
        this.shippingMemo = shippingMemo;
    }

    public Orders of(String orderer, String shippingAddress, String shippingMemo){
        return new Orders(orderer, shippingAddress, shippingMemo);
    }

    public void addProducts(List<Product> products) {
        this.products.addAll(products);
    }

    public Long getId() {
        return id;
    }
}
