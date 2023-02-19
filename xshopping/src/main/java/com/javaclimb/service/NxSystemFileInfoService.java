package com.javaclimb.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javaclimb.entity.GoodsInfo;
import com.javaclimb.entity.NxSystemFileInfo;
import com.javaclimb.mapper.GoodsInfoMapper;
import com.javaclimb.mapper.NxSystemFileInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *文件相关
 */
@Service
public class NxSystemFileInfoService {
    private final NxSystemFileInfoMapper nxSystemFileInfoMapper;
    private GoodsInfoMapper goodsInfoMapper;
    @Autowired(required = false)
    public NxSystemFileInfoService(NxSystemFileInfoMapper nxSystemFileInfoMapper, GoodsInfoMapper goodsInfoMapper) {
        this.nxSystemFileInfoMapper = nxSystemFileInfoMapper;
        this.goodsInfoMapper = goodsInfoMapper;
    }

    /**
     * 添加文件
     * @param nxSystemFileInfo 文件信息
     * @return  数据库改变条数
     */
    public int add(NxSystemFileInfo nxSystemFileInfo){
        return nxSystemFileInfoMapper.insert(nxSystemFileInfo);
    }

    /**
     * 删除商品类别
     * @param id    商品类别id
     * @return  数据库修改条数
     */
    public int delete(Long id){
        return nxSystemFileInfoMapper.deleteById(id);
    }

    /**
     * 获取文件信息
     * @param id    商品id
     * @return  文件信息
     */
    public List<NxSystemFileInfo> selectByGoodsId (Long id){
        LambdaQueryWrapper<NxSystemFileInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(NxSystemFileInfo::getGoodsId,id);
        return nxSystemFileInfoMapper.selectList(lambdaQueryWrapper);
    }

    /**
     * 查询商品图片,每个商品返回一个图片
     * @param pageSize  商品个数
     * @return  图片信息
     */
    public List<NxSystemFileInfo> selectDifferentGoods(Integer pageSize){
       QueryWrapper<NxSystemFileInfo> queryWrapper =new QueryWrapper<>();
        queryWrapper.groupBy("goods_id");
        queryWrapper.last("limit "+pageSize);
        queryWrapper.isNotNull("file_name");
        return nxSystemFileInfoMapper.selectList(queryWrapper);
    }
}
