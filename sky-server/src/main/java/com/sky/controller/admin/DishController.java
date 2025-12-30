package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 菜品管理
 */
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关接口")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 新增菜品
     * @param dishDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增菜品")
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品: {}", dishDTO);
        dishService.saveWithFlavor(dishDTO);

        // 清除缓存数据
        String key = "dish_" + dishDTO.getCategoryId();
        cleanCache(key);

        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页查询 : {}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 菜品批量删除
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("菜品批量删除")
    public Result delete(@RequestParam List<Long> ids) {
        log.info("菜品批量删除 {}", ids);
        dishService.deleteBatch(ids);

        // 删除所有的 key, ids 可以多个分类

        cleanCache("dish_*");

        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据 id 查询菜品")
    public Result<DishVO> getById(@PathVariable Long id) {
         log.info("根据 id 查询菜品 {}", id);
         DishVO dishVO = dishService.getByIdWithFlavor(id);
         return Result.success(dishVO);
    }

    @PutMapping
    @ApiOperation("修改菜品")
    public Result update(@RequestBody DishDTO dishDTO) {
        log.info("修改菜品 {}", dishDTO);
        dishService.updateWithFlavor(dishDTO);


        cleanCache("dish_*");

        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("根据分类 categoryId 查询菜品")
    public Result<List<Dish>> listByCategoryId(String categoryId) {
        log.info("根据分类 categoryId 查询菜品 {}", categoryId);
        List<Dish> dishs = dishService.listByCategoryId(categoryId);
        return Result.success(dishs);
    }

    @PostMapping("/status/{status}")
    @ApiOperation("菜品起售,停售")
    public Result setStatus(@PathVariable Integer status, Long id) {
        log.info("套餐 id 为 {}, 状态设为 {}", id, status == 1 ? "起售" : "停售");
        dishService.setStatus(id, status);

        cleanCache("dish_*");
        return Result.success();
    }

    /**
     * 清理缓存数据
     * @param pattern
     */
    private void cleanCache(String pattern) {
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);

    }
}
