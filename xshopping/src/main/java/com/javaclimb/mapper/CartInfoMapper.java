package com.javaclimb.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.javaclimb.entity.CartInfo;
import com.javaclimb.entity.GoodsInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartInfoMapper extends BaseMapper<CartInfo> {
    //文件表,商品,购物车联表查询
    List<GoodsInfo> selectCartInfo(Long id);
}
