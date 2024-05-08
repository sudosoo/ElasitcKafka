package com.sudosoo.takeItEasy.application.dto.post.specification

import com.sudosoo.takeItEasy.application.common.specification.BaseSpecification
import com.sudosoo.takeItEasy.application.common.specification.SpecificationDto
import com.sudosoo.takeItEasy.domain.entity.EsPost

object PostSpec: BaseSpecification<EsPost> {
    override val equalColumns: List<SpecificationDto>
        get() =
            listOf(
                SpecificationDto("title"),
                SpecificationDto("content"),
                SpecificationDto("writer")
            )


}