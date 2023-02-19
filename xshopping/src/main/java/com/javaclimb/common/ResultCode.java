package com.javaclimb.common;

public enum ResultCode {
    SUCCESS("0","成功"),
    ERROR("1000","系统异常"),
    PARAM_ERROR("1001","参数异常"),
    USER_EXIST_ERROR("2001","用户已存在"),
    USER_ACCOUNT_ERROR("2002","账户或密码错误"),
    USER_NOT_EXIST_ERROR("2003","用户不存在"),
    ADV_NOT_EXIST_ERROR("3001","公告不存在"),
    ORDER_PAY_ERROR("4001","库存不足"),
    ORDER_CARRY_ERROR("4002","订单尚未结束"),
    GOODS_REMOVE_ERROR("4003","商品已下架");

    public String code;
    public String msg;

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
