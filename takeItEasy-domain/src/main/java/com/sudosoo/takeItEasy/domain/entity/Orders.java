package com.sudosoo.takeItEasy.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.validator.constraints.UUID;

import java.util.ArrayList;
import java.util.List;

@Entity(name="orders")
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Orders {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private String orderId;
    private String orderer;
    private String shippingAddress;
    private String shippingMemo;
    @ElementCollection(targetClass = Product.class)
    @CollectionTable(name="order_products", joinColumns = @JoinColumn(name="order_id"))
    private List<Product> products = new ArrayList<>();


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

}
