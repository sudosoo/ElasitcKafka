package com.sudosoo.takeItEasy.application.service.order

import com.sudosoo.takeItEasy.application.core.commons.jpa.JpaService
import com.sudosoo.takeItEasy.application.dto.order.CreateOrderRequestDto
import com.sudosoo.takeItEasy.application.dto.order.OrderResponseDto
import com.sudosoo.takeItEasy.application.kafka.KafkaProducer
import com.sudosoo.takeItEasy.application.service.event.EventManager
import com.sudosoo.takeItEasy.domain.entity.EventOperation
import com.sudosoo.takeItEasy.domain.entity.KafkaTopics
import com.sudosoo.takeItEasy.domain.entity.Orders
import com.sudosoo.takeItEasy.domain.entity.Post
import com.sudosoo.takeItEasy.domain.repository.orders.OrdersRepository
import com.sudosoo.takeItEasy.domain.repository.common.BaseRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class OrdersService(
    private val repository: OrdersRepository,
    private val kafkaProducer: KafkaProducer,
    private val eventManager: EventManager,
    ) : JpaService<Orders, Long> {
    override var jpaRepository: BaseRepository<Orders, Long> = repository


    @Transactional
    fun create(requestDto: CreateOrderRequestDto): OrderResponseDto {
        val order = Orders(
            requestDto.orderer,
            requestDto.shippingAddr,
            requestDto.shippingMemo
        )
        order.addProducts(requestDto.orderItems)
        save(order)

        val event = eventManager.create(KafkaTopics.ORDER, EventOperation.ORDER_COMPLETED, order)
        kafkaProducer.send(event)

        //결제 시스템 완료 후 주문 완료 이벤트 발행
        return OrderResponseDto(order)
    }

    fun getByNameLegacy(orderer: String): List<Orders>{
        return repository.findByOrderer(orderer)
    }

    fun getByNameCoveringIndex(orderer: String): List<Orders> {
        return repository.coveringIndex(orderer)
    }

    fun createBatchOrders(count: Int): Orders {
        return Orders("Name$count",  "addr$count",  "memo$count")
    }


}

