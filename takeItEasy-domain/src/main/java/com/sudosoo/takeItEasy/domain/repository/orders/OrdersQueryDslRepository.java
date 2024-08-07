package com.sudosoo.takeItEasy.domain.repository.orders;

import com.sudosoo.takeItEasy.domain.entity.Orders;

import java.util.List;

public interface OrdersQueryDslRepository {
    List<Orders> coveringIndex(String orderer);
}
