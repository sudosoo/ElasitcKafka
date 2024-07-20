package com.sudosoo.takeItEasy.application.service.order

import com.sudosoo.takeItEasy.application.common.jpa.JpaService
import com.sudosoo.takeItEasy.application.dto.order.CreateOrderRequestDto
import com.sudosoo.takeItEasy.application.dto.order.OrderResponseDto
import com.sudosoo.takeItEasy.application.service.event.EventService
import com.sudosoo.takeItEasy.domain.entity.EventOperation
import com.sudosoo.takeItEasy.domain.entity.Order
import com.sudosoo.takeItEasy.domain.repository.OrderRepository
import com.sudosoo.takeItEasy.domain.repository.common.BaseRepository
import jakarta.transaction.Transactional
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
@Transactional
class OrderService(
    private val repository: OrderRepository,
    private val eventService: EventService
    ) :JpaService<Order,Long>{
    override var jpaRepository: BaseRepository<Order, Long> = repository


    @Transactional
    fun create(requestDto: CreateOrderRequestDto): OrderResponseDto {

        val order = Order(requestDto.orderer,requestDto.shippingAddr,requestDto.shippingMemo)
        order.addProducts(requestDto.orderItems)
        save(order)
        //결제 시스템이 있다고 가정하고 이벤트를 발생시킴
        val event = eventService.publish(EventOperation.ORDER_COMPLETED, order)
        return OrderResponseDto(order.id, event.eventId)
    }

}

