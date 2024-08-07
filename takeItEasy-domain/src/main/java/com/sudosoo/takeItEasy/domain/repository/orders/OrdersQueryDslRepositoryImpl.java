package com.sudosoo.takeItEasy.domain.repository.orders;


import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sudosoo.takeItEasy.domain.entity.Orders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.sudosoo.takeItEasy.domain.entity.QOrders.orders;

@Repository
@RequiredArgsConstructor
public class OrdersQueryDslRepositoryImpl implements OrdersQueryDslRepository {
    private final JPAQueryFactory queryFactory;

    public List<Orders> coveringIndex(String name) {
        List<Long> ids = queryFactory
                .select(orders.id)
                .from(orders)
                .where(orders.orderer.like(name + "%"))
                .orderBy(orders.id.desc())
                .limit(10)
                .offset(10)
                .fetch();

        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }

        return queryFactory
                .select(Projections.constructor(Orders.class,
                        orders.id,
                        orders.orderer,
                        orders.shippingAddress,
                        orders.products))
                .from(orders)
                .where(orders.id.in(ids))
                .orderBy(orders.id.desc())
                .fetch();
    }
}
