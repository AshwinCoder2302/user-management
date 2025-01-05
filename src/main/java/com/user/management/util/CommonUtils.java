package com.user.management.util;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommonUtils {

    public <T, R> List<R> mapEntityListToDTOList(List<T> entities, Class<R> dtoClass) {
        return entities.stream()
                .map(entity -> mapEntityToDTO(entity, dtoClass))
                .collect(Collectors.toList());
    }

    public <T, R> R mapEntityToDTO(T entity, Class<R> dtoClass) {
        try {
            R dto = dtoClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create DTO instance", e);
        }
    }
}
