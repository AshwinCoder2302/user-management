package com.user.management.util;

import com.user.management.constant.Constant;
import com.user.management.exception.BusinessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class GenericDao {

    public <ID, Entity> Entity findById(ID id, JpaRepository<Entity, ID> repository, String entityName) {
        return repository.findById(id).
                orElseThrow(()-> new BusinessException(HttpStatus.NOT_FOUND, entityName + Constant.NOT_FOUND + id));
    }
}
