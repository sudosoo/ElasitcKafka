package com.sudosoo.takeItEasy.application.service.order

import com.sudosoo.takeItEasy.application.common.jpa.JpaService
import com.sudosoo.takeItEasy.application.dto.order.CreateOrderRequestDto
import com.sudosoo.takeItEasy.application.dto.order.OrderResponseDto
import com.sudosoo.takeItEasy.application.kafka.KafkaProducer
import com.sudosoo.takeItEasy.application.service.event.EventManager
import com.sudosoo.takeItEasy.domain.entity.EventOperation
import com.sudosoo.takeItEasy.domain.entity.KafkaTopics
import com.sudosoo.takeItEasy.domain.entity.Fulfillment
import com.sudosoo.takeItEasy.domain.repository.OrderRepository
import com.sudosoo.takeItEasy.domain.repository.common.BaseRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val repository: OrderRepository,
    private val kafkaProducer: KafkaProducer,
    private val eventManager: EventManager,
    ) :JpaService<Fulfillment,Long>{
    override var jpaRepository: BaseRepository<Fulfillment, Long> = repository


    @Transactional
    fun create(requestDto: CreateOrderRequestDto): OrderResponseDto {
        val order = Fulfillment(
            requestDto.orderer,
            requestDto.shippingAddr,
            requestDto.shippingMemo
        )
        order.addProducts(requestDto.orderItems)
        save(order)
        val event = eventManager.create(KafkaTopics.ORDER, EventOperation.ORDER_COMPLETED, order)
        //결제 시스템 완료 후 주문 완료 이벤트 발행
        kafkaProducer.send(event)

        return OrderResponseDto(order.id)
    }


}

