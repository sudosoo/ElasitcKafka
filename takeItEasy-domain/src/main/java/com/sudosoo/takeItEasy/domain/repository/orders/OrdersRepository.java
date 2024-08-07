package com.sudosoo.takeItEasy.domain.repository.orders;


import com.sudosoo.takeItEasy.domain.entity.Orders;
import com.sudosoo.takeItEasy.domain.repository.common.BaseRepository;

import java.util.List;

public interface OrdersRepository extends BaseRepository<Orders, Long>, OrdersQueryDslRepository {
    List<Orders> findByOrderer(String orderer);
}