package com.sudosoo.takeItEasy.application.dto.order

import com.sudosoo.takeItEasy.domain.entity.Orders

class OrderResponseDto(orders: Orders
) {
    val orderId = orders.id

}