package com.sudosoo.takeItEasy.application.commons.specification

import com.sudosoo.takeItEasy.domain.repository.common.BaseRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification

interface JpaSpecificService<MODEL, ID> {
    val jpaSpecRepository: BaseRepository<MODEL, ID>

    fun findAllBy(
        specification: Specification<MODEL>,
        pageable: Pageable,
    ): List<MODEL> {
        val pageableWithSorting = getBasePageableWithSorting(pageable)
        val page = jpaSpecRepository.findAll(specification, pageableWithSorting)
        return page.content
    }

    fun countBy(specification: Specification<MODEL>): Long {
        return jpaSpecRepository.count(specification)
    }

    fun exitsBy(specification: Specification<MODEL>): Boolean {
        return jpaSpecRepository.exists(specification)
    }

    private fun getBasePageableWithSorting(pageable : Pageable): PageRequest {
        return PageRequest.of(
            pageable.pageNumber, pageable.pageSize, Sort.by("createDate").descending()
        )
    }
}