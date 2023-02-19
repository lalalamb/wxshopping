package com.javaclimb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 商品评价
 */
@TableName("comment_info")
public class CommentInfo {
    @TableId(type= IdType.AUTO)
    private Long id;
    private String content;
    private Long goodsId;
    private String userName;
    private String createTime;
    private Long sellerId;
    @TableField(exist = false)
    private Long orderId;

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "CommentInfo{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", goodsId=" + goodsId +
                ", userName=" + userName +
                ", createTime='" + createTime + '\'' +
                '}';
    }

    public CommentInfo(String content, Long goodsId, String userName, String createTime) {
        this.content = content;
        this.goodsId = goodsId;
        this.userName = userName;
        this.createTime = createTime;
    }

    public CommentInfo() {
    }
}
