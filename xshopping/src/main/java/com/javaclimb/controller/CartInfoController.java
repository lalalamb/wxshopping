package com.javaclimb.controller;

import com.javaclimb.common.Result;
import com.javaclimb.common.ResultCode;
import com.javaclimb.entity.CartInfo;
import com.javaclimb.entity.GoodsInfo;
import com.javaclimb.exception.CustomException;
import com.javaclimb.service.CartInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 购物车相关
 */
@RestController
@RequestMapping("/cart")
public class CartInfoController {
    private CartInfoService cartInfoService;
    @Autowired(required = false)
    public CartInfoController(CartInfoService cartInfoService) {
        this.cartInfoService = cartInfoService;
    }

    /**
     * 添加商品到购物车
     * @param cartInfo  商品信息
     * @return  添加结果
     */
    @PostMapping
    public Result add(@RequestBody CartInfo cartInfo){
        int add = cartInfoService.add(cartInfo);
        if(add!=1){
            throw new CustomException(ResultCode.ERROR);
        }
        return Result.success();
    }

    /**
     * 根据用户id查询用户购物车信息
     * @param id    用户id
     * @return  查询结果,商品信息,图片等
     */
    @GetMapping("/{id}")
    public Result<List<GoodsInfo>> findByUserId(@PathVariable Long id){
        List<GoodsInfo> carts = cartInfoService.findByUserId(id);
        return Result.success(carts);
    }

    /**
     * 根据商品在购物车中的id删除商品
     * @param id    商品在购物车中的id
     * @return  删除结果
     */
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Long id){
        int delete = cartInfoService.deleteById(id);
        if(delete!=1){
            throw new CustomException(ResultCode.ERROR);
        }
        return Result.success();
    }
}
