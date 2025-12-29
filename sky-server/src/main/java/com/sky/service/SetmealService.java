package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;

import java.util.List;

public interface SetmealService {
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    void addSetmeal(SetmealDTO setmealDTO);

    void deleteByIds(List<Long> ids);

    void setStatus(Long id, Integer status);
}
