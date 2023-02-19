package com.javaclimb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.List;

/**
 * 订单信息
 */
@TableName("order_info")
public class OrderInfo {
    @TableId(type= IdType.AUTO)
    private Long id;
    private String orderId;
    private Double totalPrice;
    private Long userId;
    private String linkAddress;
    private String linkPhone;
    private String linkMan;
    private String createTime;
    private String state;
    private Long business;
    @TableField(exist = false)
    private List<GoodsInfo> goodsList;
    @TableField(exist = false)
    private Integer count;
    @TableField(exist = false)
    private String fileName;

    @Override
    public String toString() {
        return "OrderInfo{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", totalPrice=" + totalPrice +
                ", userId=" + userId +
                ", linkAddress='" + linkAddress + '\'' +
                ", linkPhone='" + linkPhone + '\'' +
                ", linkMan='" + linkMan + '\'' +
                ", createTime='" + createTime + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    public OrderInfo(Long id,Double totalPrice) {
        this.id=id;
        this.totalPrice = totalPrice;
    }

    public Long getBusiness() {
        return business;
    }

    public void setBusiness(Long business) {
        this.business = business;
    }

    public OrderInfo(Long id, String state) {
        this.id = id;
        this.state = state;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<GoodsInfo> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsInfo> goodsList) {
        this.goodsList = goodsList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public String getLinkAddress() {
        return linkAddress;
    }

    public void setLinkAddress(String linkAddress) {
        this.linkAddress = linkAddress;
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public OrderInfo(Long id, String orderId, Double totalPrice, Long userId,
                    String linkAddress, String linkPhone,
                     String linkMan, String createTime, String state) {
        this.id = id;
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.userId = userId;
        this.linkAddress = linkAddress;
        this.linkPhone = linkPhone;
        this.linkMan = linkMan;
        this.createTime = createTime;
        this.state = state;
    }

    public OrderInfo() {
    }
}
