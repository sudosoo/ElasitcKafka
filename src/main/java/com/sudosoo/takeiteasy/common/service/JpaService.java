package com.sudosoo.takeiteasy.common.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

public interface JpaService<MODEL, ID> {
    JpaRepository<MODEL, ID> getJpaRepository();

    default MODEL saveModel(MODEL model) {
        return getJpaRepository().save(model);
    }

    default List<MODEL> saveModels(List<MODEL> models) {
        return getJpaRepository().saveAll(models);
    }

    default MODEL findModelById(ID id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id is null");
        }
        Optional<MODEL> optionalModel = getJpaRepository().findById(id);
        if (optionalModel.isPresent()) {
            return optionalModel.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "data is not found by id: " + id);
        }
    }

    default void deleteById(ID id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id is null");
        }
        getJpaRepository().deleteById(id);
    }
}