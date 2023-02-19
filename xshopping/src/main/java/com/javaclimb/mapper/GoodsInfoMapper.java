package com.javaclimb.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.javaclimb.entity.GoodsInfo;
import com.javaclimb.vo.EchartsData;
import org.apache.ibatis.annotations.Param;

import java.util.List;
public interface GoodsInfoMapper extends BaseMapper<GoodsInfo> {
    <T> List<GoodsInfo> selectTypeName(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
    //查询推荐商品id
    List<GoodsInfo> selectRecommend();
    //查询热卖商品
    <T> List<GoodsInfo> selectFileName(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
    <T> List<EchartsData> getSalesJoinType(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
    <T> List<EchartsData> getSales(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);


}
