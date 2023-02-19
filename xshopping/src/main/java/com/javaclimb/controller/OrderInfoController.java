package com.javaclimb.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.javaclimb.common.Result;
import com.javaclimb.common.ResultCode;
import com.javaclimb.entity.GoodsInfo;
import com.javaclimb.entity.OrderInfo;
import com.javaclimb.exception.CustomException;
import com.javaclimb.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderInfoController {
    private OrderInfoService orderInfoService;
    @Autowired(required = false)
    public OrderInfoController(OrderInfoService orderInfoService) {
        this.orderInfoService = orderInfoService;
    }

    /**
     * 保存订单
     * @param orderInfo 订单信息
     * @return  保存结果
     */
    @PostMapping
    private Result add(@RequestBody OrderInfo orderInfo){
        List<GoodsInfo> goodsList = orderInfo.getGoodsList();
        if(CollectionUtil.isEmpty(goodsList)){
            throw new CustomException(ResultCode.PARAM_ERROR);
        }
        orderInfo.setState("待付款");
        orderInfoService.insert(orderInfo);
        return Result.success();
    }

    /**
     * 获取订单信息
     * @param userId    用户id
     * @param state 订单状态
     * @return  订单信息列表
     */
    @GetMapping("/{pageNum}/{userId}")
    private Result<PageInfo<OrderInfo>> findOrders(@PathVariable Long userId,
                                                   @PathVariable Integer pageNum,
                                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                                   @RequestParam String state){
        PageInfo<OrderInfo> orders = orderInfoService.getOrders(userId, state,pageSize,pageNum);
        return Result.success(orders);
    }

    /**
     * 商家及管理员获取订单列表
     * @param userId    商家或管理员id
     * @param level 用户等级 1为管理员 2为商家
     * @return  订单信息列表
     */
    @GetMapping("/{pageNum}/{userId}/{level}")
    private Result<PageInfo<OrderInfo>> findOrdersByBusiness(@PathVariable Long userId,
                                                   @PathVariable Integer pageNum,
                                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                                   @PathVariable Integer level){
        PageInfo<OrderInfo> orders = orderInfoService.getOrdersByBusiness(userId, level,pageSize,pageNum);
        return Result.success(orders);
    }

    /**
     * 通过订单id获取订单详细信息
     * @param id    订单id
     * @return  订单详情
     */
    @GetMapping("/{id}")
    private Result<OrderInfo> findOrderById(@PathVariable Long id){
        OrderInfo orderDetail = orderInfoService.getOrderDetail(id);
        return Result.success(orderDetail);
    }
    /**
     * 改变订单状态
     * @param orderInfo 订单信息
     */
    @PutMapping
    private Result changeState(@RequestBody OrderInfo orderInfo){
        int i = orderInfoService.updateOrder(orderInfo);
        if(i!=1){
            throw new CustomException(ResultCode.ERROR);
        }
        return Result.success();
    }

    /**
     * 删除已结束的订单
     * @param orderId   订单id
     * @param state 订单状态
     * @return  删除结果
     */
    @DeleteMapping("/{orderId}")
    private Result removeOrder(@PathVariable Long orderId,@RequestParam(required = true) String state){
        if(StrUtil.equals(state,"已签收")||StrUtil.equals(state,"已取消")||StrUtil.equals(state,"已退货")){
            orderInfoService.deleteOrder(orderId);
        }else {
            throw new CustomException(ResultCode.ORDER_CARRY_ERROR);
        }
        return Result.success();
    }
}
