package com.sudosoo.takeItEasy.presentation.controller.order

import com.sudosoo.takeItEasy.application.dto.order.CreateOrderRequestDto
import com.sudosoo.takeItEasy.application.dto.order.OrderResponseDto
import com.sudosoo.takeItEasy.application.service.order.OrdersService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.coroutines.RestrictsSuspension

@RestController
@RequestMapping("/api/order")
class OrderController(
    val ordersService: OrdersService
){
    @PostMapping("/create", name = "createOrder")
    fun createOrder(@RequestBody req: CreateOrderRequestDto):ResponseEntity<OrderResponseDto>{
        val result = ordersService.create(req)
        return ResponseEntity.ok().body(result)
    }


}