package com.javaclimb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 订单商品关系
 */
@TableName("order_goods_rel")
public class OrderGoodsRel {
    @TableId(type= IdType.AUTO)
    private Long id;
    private Long goodsId;
    private Long orderId;
    private Integer count;
    private Double totalPrice;


    @Override
    public String toString() {
        return "OrderGoodsRel{" +
                "id=" + id +
                ", goodsId=" + goodsId +
                ", orderId=" + orderId +
                ", count=" + count +
                '}';
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public OrderGoodsRel(Long goodsId, Long orderId, Integer count) {
        this.goodsId = goodsId;
        this.orderId = orderId;
        this.count = count;
    }

    public OrderGoodsRel() {
    }
}
