package com.sudosoo.takeItEasy.application.dto.order

import com.sudosoo.takeItEasy.domain.entity.Product

class CreateOrderRequestDto(
    val orderItems: List<Product>,
    val orderer: String,
    val shippingAddr: String,
    val shippingMemo: String
) {

}