package com.javaclimb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 购物车信息
 */
@TableName("cart_info")
public class CartInfo {
    @TableId(type= IdType.AUTO)
    private Long id;
    private Long goodsId;
    private Integer count;
    private Long userId;

    private String createTime;

    @Override
    public String toString() {
        return "CartInfo{" +
                "id=" + id +
                ", goodsId=" + goodsId +
                ", count=" + count +
                ", userId=" + userId +
                ", createTime='" + createTime + '\'' +
                '}';
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public CartInfo(Long goodsId, Integer count, Long userId,  String createTime) {
        this.goodsId = goodsId;
        this.count = count;
        this.userId = userId;
        this.createTime = createTime;
    }

    public CartInfo() {
    }
}
