package com.javaclimb.entity;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.List;

/**
 * 商品详情
 */
@TableName("goods_info")
public class GoodsInfo {
    @TableId(type= IdType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Double discount;
    private Integer sales;
    private Integer hot;
    private String recommend;
    private Integer count;      //库存
    private Long typeId;
    private Long userId;
    @TableField(exist = false)
    private String typeName;
    @TableField(exist = false)
    private String fileName;
    @TableField(exist = false)
    private String descriptionView;
    @TableField(exist = false)
    private Long cartId;
    @TableField(exist = false)
    private Integer number;      //购物车中商品的数量

    @Override
    public String toString() {
        return "GoodsInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", sales=" + sales +
                ", hot=" + hot +
                ", recommend='" + recommend + '\'' +
                ", count=" + count +
                ", typeId=" + typeId +
                ", userId=" + userId +
                ", typeName='" + typeName + '\'' +
                '}';
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getDescriptionView() {
        this.descriptionView=this.description;
        if(descriptionView!=null&&descriptionView.length()>30){
            return descriptionView.substring(0,30)+"...";
        }
        return descriptionView;
    }

    public void setDescriptionView(String descriptionView) {
        this.descriptionView = descriptionView;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public GoodsInfo(Long id, String name, String description, Double price, Double discount,
                     Integer sales, Integer hot, String recommend, Integer count, Long typeId,
                     Long userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.sales = sales;
        this.hot = hot;
        this.recommend = recommend;
        this.count = count;
        this.typeId = typeId;
        this.userId = userId;
    }

    public GoodsInfo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Integer getHot() {
        return hot;
    }

    public void setHot(Integer hot) {
        this.hot = hot;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
