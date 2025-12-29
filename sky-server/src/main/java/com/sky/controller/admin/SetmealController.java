package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
@Api("套餐相关接口")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @GetMapping("/page")
    @ApiOperation("分页查询套餐")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO) {
        log.info("分页查询 {}", setmealPageQueryDTO);
        PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping
    @ApiOperation("新增套餐")
    public Result addSetmeal(@RequestBody SetmealDTO setmealDTO) {
        log.info("新增套餐 {}", setmealDTO);
        setmealService.addSetmeal(setmealDTO);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("根据 ids 批量删除套餐")
    public Result deleteByIds(@RequestParam List<Long> ids) {
        log.info("根据 ids 批量删除套餐 {}", ids);
        setmealService.deleteByIds(ids);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("套餐起售,停售")
    public Result setStatus(@PathVariable Integer status, Long id) {
        log.info("套餐 id 为 {}, 状态设为 {}", id, status == 1 ? "起售" : "停售");
        setmealService.setStatus(id, status);
        return Result.success();
    }

}
