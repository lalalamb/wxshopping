package com.javaclimb.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.javaclimb.entity.CartInfo;
import com.javaclimb.entity.GoodsInfo;
import com.javaclimb.mapper.CartInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 购物车相关
 */
@Service
public class CartInfoService {
    private CartInfoMapper cartInfoMapper;
    @Autowired(required = false)
    public CartInfoService(CartInfoMapper cartInfoMapper) {
        this.cartInfoMapper = cartInfoMapper;
    }

    /**
     * 查询用户购物车中是否有该商品
     * @param user  用户id
     * @param goods 商品id
     * @return  如果有返回购物车信息,没有返回空
     */
    public CartInfo isExist(Long user, Long goods){
        LambdaQueryWrapper<CartInfo> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(CartInfo::getUserId,user);
        queryWrapper.eq(CartInfo::getGoodsId,goods);
        List<CartInfo> cartInfos = cartInfoMapper.selectList(queryWrapper);
        if(CollectionUtil.isEmpty(cartInfos)){
            return null;
        }
        return cartInfos.get(0);
    }

    /**
     * 添加商品到购物车
     * @param cartInfo  要加入的商品信息
     * @return  数据库中被修改的条数
     */
    public int add(CartInfo cartInfo){
        //设置加入购物车时间
        cartInfo.setCreateTime(DateUtil.format(new Date(),"yyyyMMddHHmm"));
        CartInfo exist = isExist(cartInfo.getUserId(), cartInfo.getGoodsId());
        if(exist !=null){
            exist.setCount(exist.getCount()+1);
            return cartInfoMapper.updateById(exist);
        }
        return cartInfoMapper.insert(cartInfo);
    }

    /**
     * 查询购物车中商品信息
     * @param id    用户id
     * @return  商品信息列表
     */
    public List<GoodsInfo> findByUserId(Long id){
       return cartInfoMapper.selectCartInfo(id);
    }

    /**
     * 根据购物车id删除购物车
     * @param id    商品在购物车中的id
     * @return  删除记录条数
     */
    public int deleteById(Long id){
        return cartInfoMapper.deleteById(id);
    }

    /**
     * 清空购物车
     * @param userId    用户id
     */
    public int deleteByUserId(Long userId){
        LambdaQueryWrapper<CartInfo> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(CartInfo::getUserId,userId);
        return cartInfoMapper.delete(queryWrapper);
    }
}
