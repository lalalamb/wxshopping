package com.javaclimb.service;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javaclimb.entity.GoodsInfo;
import com.javaclimb.mapper.GoodsInfoMapper;
import com.javaclimb.vo.EchartsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *商品类别相关
 */
@Service
public class GoodsInfoService {
    private final GoodsInfoMapper goodsInfoMapper;
    private final TypeInfoService typeInfoService;
    @Autowired(required = false)
    public GoodsInfoService(GoodsInfoMapper goodsInfoMapper, TypeInfoService typeInfoService) {
        this.goodsInfoMapper = goodsInfoMapper;
        this.typeInfoService = typeInfoService;
    }

    /**
     * 分页模糊查询商品+图片
     * @param pageNum   第几页
     * @param pageSize  每页数据数
     * @param name  商品类别名称
     * @return  查询到的所有商品信息
     */
    public PageInfo<GoodsInfo> findPage(Integer pageNum,Integer pageSize,String name){
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<GoodsInfo> queryWrapper=new QueryWrapper<>();
        if(name!=null){
            queryWrapper.like("name",name);
        }
        queryWrapper.groupBy("goods_id");
        List<GoodsInfo> advertisers = goodsInfoMapper.selectFileName(queryWrapper);
        return PageInfo.of(advertisers);
    }

    /**
     * 分页模糊查询商品+类别
     * @param pageNum   第几页
     * @param pageSize  每页数据数
     * @param name  商品类别名称
     * @return  查询到的所有商品信息
     */
    public PageInfo<GoodsInfo> findGoodsAndType(Integer pageNum,Integer pageSize,String name,Integer level,Long userId){
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<GoodsInfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.select("g.* ,t.name as type_name");
        if(name!=null){
            queryWrapper.like("g.name",name);
        }
        if(level==2){
            queryWrapper.eq("user_id",userId);
        }
        List<GoodsInfo> advertisers = goodsInfoMapper.selectTypeName(queryWrapper);
        return PageInfo.of(advertisers);
    }

    /**
     * 获取某家商品的所有类别
     * @param userId    商家id
     * @return 类别名称和id
     */
    public List<GoodsInfo> getType(Long userId){
        QueryWrapper<GoodsInfo> wrapper = new QueryWrapper<>();
        wrapper.select("g.type_id,t.name as type_name").eq("g.user_id",userId).groupBy("g.type_id");
        return goodsInfoMapper.selectTypeName(wrapper);
    }
    /**
     * 通过商品id查询商品信息
     * @param id    商品id
     * @return  商品信息
     */
    public GoodsInfo selectById(Long id){
        return goodsInfoMapper.selectById(id);
    }
    /**
     * 通过商品id查询商品信息+商品图片
     * @param id    商品id
     * @return  商品信息
     */
    public GoodsInfo getGoodsDetail(Long id){
        QueryWrapper<GoodsInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("g.id",id);
        List<GoodsInfo> list = goodsInfoMapper.selectFileName(queryWrapper);
        if(CollectionUtil.isEmpty(list)){
            return null;
        }
        return list.get(0);
    }

    /**
     * 通过商品类别查询商品信息
     * @param id    商品类别id
     * @return  商品信息列表
     */
    public List<GoodsInfo> getGoodsByType(Long id){
        QueryWrapper<GoodsInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("g.type_id",id);
        return goodsInfoMapper.selectFileName(queryWrapper);
    }
    /**
     * 添加商品类别
     * @param goodsInfo 商品类别信息
     * @return  数据库改变条数
     */
    public int add(GoodsInfo goodsInfo){
        return goodsInfoMapper.insert(goodsInfo);
    }

    /**
     * 删除商品类别
     * @param id    商品类别id
     * @return  数据库修改条数
     */
    public int delete(Long id){
        return goodsInfoMapper.deleteById(id);
    }

    /**
     * 更新商品类别
     * @param goodsInfo    商品类别信息
     * @return  修改条数
     */
    public int update (GoodsInfo goodsInfo){
        return goodsInfoMapper.updateById(goodsInfo);
    }

    /**
     * 查询推荐商品
     * @param pageNum   第几页
     * @param pageSize  每页条数
     * @return  商品信息
     */
    public PageInfo<GoodsInfo> findRecommendGoods(Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<GoodsInfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("recommend","是");
        queryWrapper.groupBy("goods_id");
        List<GoodsInfo> advertisers = goodsInfoMapper.selectFileName(queryWrapper);
        return PageInfo.of(advertisers);
    }

    /**
     * 查询热卖商品
     * @param pageNum   第几页
     * @param pageSize  每页条数
     * @return  热卖商品信息
     */
    public PageInfo<GoodsInfo> findSales(Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<GoodsInfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("sales");
        queryWrapper.groupBy("goods_id");
        List<GoodsInfo> advertisers = goodsInfoMapper.selectFileName(queryWrapper);
        return PageInfo.of(advertisers);
    }

    /**
     * 获取该用户商品的总销量
     * @param userId    用户id
     * @return  总销量
     */
    public int getSalesAll(Long userId,Integer level){
        QueryWrapper<GoodsInfo> wrapper = new QueryWrapper<>();
        if(level==2){
            wrapper.eq("user_id",userId);
        }
        List<EchartsData> salesNum = goodsInfoMapper.getSales(wrapper);
        return  salesNum.get(0).getValue();
    }

    /**
     *获取每类商品的销量
     */
    public List<EchartsData> getSales(Long userId,Integer level){
        QueryWrapper<GoodsInfo> wrapper = new QueryWrapper<>();
        if(level==2){
            wrapper.eq("user_id",userId);
        }
        wrapper.groupBy("type_id");
        return goodsInfoMapper.getSalesJoinType(wrapper);
    }
    /**
     * 获取该类商品中最多10个销量最多的产品销量
     */
    public List<EchartsData> getSalesByType(Long userId,Integer level,Long typeId){
        QueryWrapper<GoodsInfo> wrapper = new QueryWrapper<>();
        if(level==2){
            wrapper.eq("user_id",userId);
        }
        wrapper.select(",name")
                .eq("type_id",typeId).orderByAsc("sales")
                .last("limit 10");
        return goodsInfoMapper.getSales(wrapper);
    }
}
