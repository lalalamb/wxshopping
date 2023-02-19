package com.javaclimb.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.javaclimb.entity.OrderGoodsRel;
import com.javaclimb.entity.OrderInfo;
import com.javaclimb.mapper.OrderGoodsRelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单商品关联
 */
@Service
public class OrderGoodsRelService {
    private OrderGoodsRelMapper orderGoodsRelMapper;
    @Autowired(required = false)
    public OrderGoodsRelService(OrderGoodsRelMapper orderGoodsRelMapper) {
        this.orderGoodsRelMapper = orderGoodsRelMapper;
    }

    /**
     * 获取订单商品映射
     * @param orderId   商品id
     * @return  商品id与订单id关系列表
     */
    public List<OrderGoodsRel> getRelByOrderId(Long orderId){
        LambdaQueryWrapper<OrderGoodsRel> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderGoodsRel::getOrderId,orderId);
        return orderGoodsRelMapper.selectList(wrapper);
    }

    /**
     * 删除某订单与该订单中商品的所有映射关系
     * @param orderId   订单id
     */
    public void deleteRelByOrderId(Long orderId){
        LambdaQueryWrapper<OrderGoodsRel> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderGoodsRel::getOrderId,orderId);
        orderGoodsRelMapper.delete(wrapper);
    }

    /**
     * 添加订单与商品的映射关系
     * @param orderGoodsRel 商品订单关系映射信息
     */
    public void addRel(OrderGoodsRel orderGoodsRel){
        orderGoodsRelMapper.insert(orderGoodsRel);
    }
//    public OrderGoodsRel getPrice(Long orderId){
//        QueryWrapper<OrderGoodsRel> wrapper = new QueryWrapper<>();
//        wrapper.eq("r.order_id",orderId);
//        wrapper.groupBy("g.type_id");
//        return null;
//    }
}
