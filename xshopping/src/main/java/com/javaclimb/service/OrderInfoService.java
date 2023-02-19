package com.javaclimb.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javaclimb.common.ResultCode;
import com.javaclimb.entity.GoodsInfo;
import com.javaclimb.entity.NxSystemFileInfo;
import com.javaclimb.entity.OrderGoodsRel;
import com.javaclimb.entity.OrderInfo;
import com.javaclimb.exception.CustomException;
import com.javaclimb.mapper.NxSystemFileInfoMapper;
import com.javaclimb.mapper.OrderInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderInfoService {
    private OrderInfoMapper orderInfoMapper;
    private GoodsInfoService goodsInfoService;
    private UserInfoService userInfoService;
    private OrderGoodsRelService orderGoodsRelService;
    private CartInfoService cartInfoService;
    private NxSystemFileInfoMapper nxSystemFileInfoMapper;
    @Autowired(required = false)
    public OrderInfoService(OrderInfoMapper orderInfoMapper, GoodsInfoService goodsInfoService,
                            OrderGoodsRelService orderGoodsRelService, CartInfoService cartInfoService,
                            NxSystemFileInfoMapper nxSystemFileInfoMapper,UserInfoService userInfoService) {
        this.orderInfoMapper = orderInfoMapper;
        this.goodsInfoService = goodsInfoService;
        this.orderGoodsRelService = orderGoodsRelService;
        this.cartInfoService = cartInfoService;
        this.nxSystemFileInfoMapper = nxSystemFileInfoMapper;
        this.userInfoService=userInfoService;
    }

    /**
     * 按照商家id分离商品
     * @param goodsList 商品列表
     * @return  商家id为key值商品信息列表为value的map集合
     */
    public Map<Long,List<GoodsInfo>> dividedGoods(List<GoodsInfo> goodsList){
        Map<Long,List<GoodsInfo>> map=new HashMap<>();
        List<GoodsInfo> list=new ArrayList<>();
        list.add(goodsList.get(0));
        map.put(goodsList.get(0).getUserId(),list);
        for (int i=1;i<goodsList.size();i++){
            List<GoodsInfo> goods = map.get(goodsList.get(i).getUserId());
            if(goods!=null){
                goods.add(goodsList.get(i));
            }else {
                List<GoodsInfo> list1=new ArrayList<>();
                list.add(goodsList.get(i));
                map.put(goodsList.get(i).getUserId(),list1);
            }
        }
        return map;
    }
    /**
     * 添加订单
     * @param orderInfo 订单信息
     */
    public void insert(OrderInfo orderInfo){
        Long userId = orderInfo.getUserId();
        List<GoodsInfo> goodsInfos = orderInfo.getGoodsList();
        Map<Long, List<GoodsInfo>> map = dividedGoods(goodsInfos);
        for (Map.Entry<Long,List<GoodsInfo>> entry : map.entrySet()) {
            List<GoodsInfo> goodsList = entry.getValue();
        //生成订单号   计算订单总价
        String time = DateUtil.format(new Date(), "yyyyMMddHHmm");
        String orderId=userId%1000+ time + RandomUtil.randomNumbers(4);
        orderInfo.setOrderId(orderId);
        orderInfo.setCreateTime(time);
        orderInfo.setBusiness(entry.getKey());
        //保存订单
        orderInfoMapper.insert(orderInfo);
        //遍历数组
        double pay=0;
        for (GoodsInfo goods:goodsList) {
            Long id = goods.getId();
            GoodsInfo goodsInfo = goodsInfoService.selectById(id);
            if(goodsInfo==null||goodsInfo.getCount()==0){
                throw new CustomException(ResultCode.GOODS_REMOVE_ERROR);
            }
            Integer count = goodsInfo.getCount();
            Integer number = goods.getNumber();//购买数量
            double price = goods.getPrice() * number * goods.getDiscount();
            pay+=price;
            if(count< number){
                throw new CustomException(ResultCode.ORDER_PAY_ERROR);
            }
//            扣库存
            goodsInfo.setCount(count- number);
            //增加销量
            int sales=goodsInfo.getSales()==null?0:goodsInfo.getSales();
            goodsInfo.setSales(sales+number);
            int update = goodsInfoService.update(goodsInfo);
            System.out.println(update);
            //商品订单关联
            OrderGoodsRel orderGoodsRel = new OrderGoodsRel(goods.getId(), orderInfo.getId(), goods.getNumber());
            orderGoodsRelService.addRel(orderGoodsRel);
        }
        orderInfoMapper.updateById(new OrderInfo(orderInfo.getId(),pay));

        }
        //清空购物车
        cartInfoService.deleteByUserId(userId);
    }

    /**
     * 根据用户id及订单状态获取订单信息
     * @param userId    用户id
     * @param state 订单状态
     * @return  订单信息列表
     */
    public PageInfo<OrderInfo> getOrders(Long userId,String state,Integer pageSize, Integer pageNum){
        PageHelper.startPage(pageNum,pageSize);
        LambdaQueryWrapper<OrderInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderInfo::getUserId,userId);
        if(StrUtil.isNotBlank(state)){
            wrapper.eq(OrderInfo::getState,state);
        }
        List<OrderInfo> orderInfos = orderInfoMapper.selectList(wrapper);
        for (OrderInfo orderInfo :
                orderInfos) {
            findInfo(orderInfo);
        }
        return PageInfo.of(orderInfos);
    }

    /**
     * 包装订单图片,商品数量
     * @param orderInfo 订单信息
     */
    public void findInfo(OrderInfo orderInfo){
        List<OrderGoodsRel> relList = orderGoodsRelService.getRelByOrderId(orderInfo.getId());
        if(CollectionUtil.isEmpty(relList)){
            throw new CustomException(ResultCode.ERROR);
        }
        int count=0;
        for (OrderGoodsRel rel :
                relList) {
            count += rel.getCount();
        }
        orderInfo.setCount(count);
        Long goodsId = relList.get(0).getGoodsId();
        LambdaQueryWrapper<NxSystemFileInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(NxSystemFileInfo::getGoodsId,goodsId);
        List<NxSystemFileInfo> files = nxSystemFileInfoMapper.selectList(queryWrapper);
        if(CollectionUtil.isEmpty(files)){
            throw new CustomException(ResultCode.ERROR);
        }
        orderInfo.setFileName(files.get(0).getFileName());
    }

    /**
     * 更新订单状态
     * @param orderInfo 订单信息
     * @return  被更改的订单数量
     */
    public int updateOrder(OrderInfo orderInfo){
        switch (orderInfo.getState()){
            case "待发货":
                //扣钱
                userInfoService.setBalance(orderInfo.getUserId(),orderInfo.getTotalPrice());
                break;
            case "完成":
                //退钱
                userInfoService.setBalance(orderInfo.getUserId(),orderInfo.getTotalPrice()*(-1));
                break;
            case "已取消":
                //还原库存
                cancelOrder(orderInfo);
                break;
        }
        return orderInfoMapper.updateById(orderInfo);
    }
    /**
     * 取消订单,增加库存减少销量
     * @param orderInfo    订单信息
     */
    public void cancelOrder(OrderInfo orderInfo){

        List<OrderGoodsRel> orderGoodsRels = orderGoodsRelService.getRelByOrderId(orderInfo.getId());
        for (OrderGoodsRel orderGoodsRel :
                orderGoodsRels) {
            GoodsInfo goodsInfo = goodsInfoService.selectById(orderGoodsRel.getGoodsId());
            goodsInfo.setCount(goodsInfo.getCount()+orderGoodsRel.getCount());
            goodsInfo.setSales(goodsInfo.getSales()+orderGoodsRel.getCount());
            goodsInfoService.update(goodsInfo);
        }
    }

    /**
     * 根据商家查询订单
     * @param businessId    商家id
     * @param level 等级 1为管理员 2为商家
     * @param pageSize  每页数据数量
     * @param pageNum   第几页
     */
    public PageInfo<OrderInfo> getOrdersByBusiness(Long businessId,Integer level,Integer pageSize, Integer pageNum){
        PageHelper.startPage(pageNum,pageSize);
        if(level>=3){
            throw new CustomException(ResultCode.PARAM_ERROR);
        }
        LambdaQueryWrapper<OrderInfo> wrapper = new LambdaQueryWrapper<>();
        if(level==2){
            wrapper.eq(OrderInfo::getBusiness,businessId);
        }
        List<OrderInfo> orderInfos = orderInfoMapper.selectList(wrapper);
        return PageInfo.of(orderInfos);
    }

    /**
     * 删除订单
     * @param orderId   订单id
     */
    public void deleteOrder (Long orderId){
        orderInfoMapper.deleteById(orderId);
        orderGoodsRelService.deleteRelByOrderId(orderId);
    }

    /**
     * 获取订单详情
     * @param orderId   订单id
     * @return  订单信息
     */
    public OrderInfo getOrderDetail(Long orderId){
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        List<OrderGoodsRel> relByOrderId = orderGoodsRelService.getRelByOrderId(orderId);
        List<GoodsInfo> list=new ArrayList<>();
        for (OrderGoodsRel o :
                relByOrderId) {
            GoodsInfo goodsDetail = goodsInfoService.getGoodsDetail(o.getGoodsId());
            goodsDetail.setNumber(o.getCount());
            list.add(goodsDetail);
        }
        orderInfo.setGoodsList(list);
        return orderInfo;
    }

    /**
     * 获取销售总价
     * @param userId    商家id
     * @param level 用户等级
     * @return  销售总价,普通商家获得自家总价,管理获取所有销售总价
     */
    public Double getTotalPrice(Long userId,Integer level){
        QueryWrapper<OrderInfo> wrapper = new QueryWrapper<>();
        if(level==2){
            wrapper.eq("business",userId);
        }
        wrapper.eq("state","已签收");
        return orderInfoMapper.selectTotalPrice(wrapper);
    }
}
