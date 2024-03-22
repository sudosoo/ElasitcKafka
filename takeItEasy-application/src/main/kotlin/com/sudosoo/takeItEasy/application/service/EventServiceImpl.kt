package com.sudosoo.takeItEasy.application.service

import com.sudosoo.takeItEasy.application.dto.coupon.CouponIssuanceRequestDto
import com.sudosoo.takeItEasy.application.dto.event.CreateEventRequestDto
import com.sudosoo.takeItEasy.application.dto.event.EventResponseDto
import com.sudosoo.takeItEasy.domain.entity.Coupon
import com.sudosoo.takeItEasy.domain.entity.Event
import com.sudosoo.takeItEasy.domain.repository.EventRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.function.Supplier

@Transactional
@Service
class EventServiceImpl (
    val eventRepository: EventRepository,
    val couponService: CouponService
) :EventService {

    override fun createdEvent(requestDto: CreateEventRequestDto): EventResponseDto {
        validateDiscountFields(requestDto)
        val eventDeadLine = LocalDateTime.parse(requestDto.eventDeadline)

        val coupon: Coupon = if (requestDto.discountRate !== 0) {
            //할인율 적용 쿠폰일때
            couponService.rateCouponCreate(requestDto)
        } else {
            //할인가격 적용 쿠폰 일때
            couponService.priceCouponCreate(requestDto)
        }

        val event = Event.of(requestDto.eventName,requestDto.couponQuantity, requestDto.eventDeadline, coupon)
        eventRepository.save<Event>(event)
        return EventResponseDto(event.id, coupon.id)
    }

    @Transactional(timeout = 5)
    override fun couponIssuance(requestDto: CouponIssuanceRequestDto) {
        val event: Event = eventRepository.findByEventIdForUpdate(requestDto.eventId)
            .orElseThrow<IllegalArgumentException>(Supplier { IllegalArgumentException("Event is not found") })
        //TODO MemberSetting
        val memberId: Long = requestDto.memberId
        /* 이벤트 종료 시점은 프론트 or 앞단에서 처리 해 준다.
        if (event.isDeadlineExpired()) {
            System.out.println("이벤트가 종료되었습니다.");
        }
        */
        event.decreaseCouponQuantity()
        event.setMember(memberId)
        eventRepository.save<Event>(event)
    }

    companion object {
        private fun validateDiscountFields(requestDto: CreateEventRequestDto) {
            require(
                !(requestDto.discountRate == 0 && requestDto.discountPrice == 0L) &&
                        !(requestDto.discountRate != 0 && requestDto.discountPrice != 0L)
            ) { "discountRate 또는 discountPrice 중 하나만 존재 해야 합니다." }
        }
    }
}
