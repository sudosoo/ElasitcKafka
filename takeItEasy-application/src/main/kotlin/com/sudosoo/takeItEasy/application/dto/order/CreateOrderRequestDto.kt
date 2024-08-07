package com.sudosoo.takeItEasy.application.dto.order

import com.sudosoo.takeItEasy.domain.entity.Product

data class CreateOrderRequestDto(
    val orderer: String,
    val shippingAddr: String,
    val shippingMemo: String,
    val orderItems: List<Product>,
)