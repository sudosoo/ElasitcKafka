package com.sudosoo.takeItEasy.application.common.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.sudosoo.takeItEasy.application.common.service.CommonService.checkNotNullData;

public interface JpaService<MODEL, ID> {
    JpaRepository<MODEL, ID> getJpaRepository();

    default <T extends MODEL> T save(T entity) {
        return getJpaRepository().save(entity);
    }

    default List<MODEL> saveModels(List<MODEL> models) {
        return getJpaRepository().saveAll(models);
    }

    default MODEL findById(ID id) {
        checkNotNullData(id, "id is null");
        return getJpaRepository().findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "data is not found by id: " + id));
    }


    default void deleteById(ID id) {
        checkNotNullData(id , "id is null");
        getJpaRepository().deleteById(id);
    }

    default Page<MODEL> findAllPagination(PageRequest pageRequest) {
        checkNotNullData(pageRequest, "pageRequest is null");

        Page<MODEL> pageModel = getJpaRepository().findAll(pageRequest);
        if (pageModel.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data Is Empty.");
        } else {
            return pageModel;
        }
    }


}