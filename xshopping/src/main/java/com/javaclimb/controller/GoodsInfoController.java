package com.javaclimb.controller;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.javaclimb.common.Result;
import com.javaclimb.common.ResultCode;
import com.javaclimb.entity.GoodsInfo;
import com.javaclimb.exception.CustomException;
import com.javaclimb.service.GoodsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品相关
 */
@RestController
@RequestMapping("/goods")
public class GoodsInfoController {
    private GoodsInfoService goodsInfoService;
    @Autowired(required = false)
    public GoodsInfoController(GoodsInfoService goodsInfoService) {
        this.goodsInfoService = goodsInfoService;
    }

    /**
     * 根据商品名称分页模糊查询(获取图片)
     * @param name 用户名
     * @param pageNum 第几页
     * @param pageSize 每页条数
     * @return 返回查询信息及状态码
     */
    @GetMapping("/{pageNum}/{name}")
    public Result<PageInfo<GoodsInfo>> page(@PathVariable String name,
                                           @PathVariable Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize){
        String trim = name.trim();
        if(StrUtil.isEmpty(trim)){
            throw new CustomException(ResultCode.PARAM_ERROR);
        }
        PageInfo<GoodsInfo> page = goodsInfoService.findPage(pageNum, pageSize, trim);
        return Result.success(page);
    }

    /**
     * 分页查询所有商品
     * @param pageNum   第几页
     * @param pageSize  每页记录
     * @return  查询结果
     */
    @GetMapping("/{pageNum}")
    public Result<PageInfo<GoodsInfo>> pageAll(@PathVariable Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<GoodsInfo> page = goodsInfoService.findPage(pageNum, pageSize, null);
        return Result.success(page);
    }
    /**
     * 根据商品名称分页模糊查询(获取类别)
     * @param name 用户名
     * @param pageNum 第几页
     * @param pageSize 每页条数
     * @return 返回查询信息及状态码
     */
    @GetMapping("/and/{pageNum}/{name}")
    public Result<PageInfo<GoodsInfo>> findGoodsAndType(@PathVariable String name,
                                            @PathVariable Integer pageNum,
                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                                        @RequestParam Integer level,
                                                        @RequestParam Long user){
        String trim = name.trim();
        if(StrUtil.isEmpty(trim)){
            throw new CustomException(ResultCode.PARAM_ERROR);
        }
        PageInfo<GoodsInfo> page = goodsInfoService.findGoodsAndType(pageNum, pageSize,trim,level,user);
        return Result.success(page);
    }

    /**
     * 分页查询所有商品
     * @param pageNum   第几页
     * @param pageSize  每页记录
     * @return  查询结果
     */
    @GetMapping("/and/{pageNum}")
    public Result<PageInfo<GoodsInfo>> pageGoodsAndTypeAll(@PathVariable Integer pageNum,
                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                                           @RequestParam Integer level,
                                                           @RequestParam Long user){
        PageInfo<GoodsInfo> page = goodsInfoService.findGoodsAndType(pageNum, pageSize, null,level,user);
        return Result.success(page);
    }
    @GetMapping("/types/{userId}")
    public Result<List<GoodsInfo>> findType(@PathVariable Long userId){
        List<GoodsInfo> type = goodsInfoService.getType(userId);
        return Result.success(type);
    }
    /**
     * 查询推荐商品信息
     * @param pageNum   查询页数
     * @param pageSize  每页数据条数
     * @return  推荐商品信息
     */
    @GetMapping("/recommend")
    public Result<PageInfo<GoodsInfo>> findRecommend(@RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "100") Integer pageSize){
        PageInfo<GoodsInfo> page = goodsInfoService.findRecommendGoods(pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 查询热卖商品信息
     * @param pageNum   查询页数
     * @param pageSize  每页数据条数
     * @return  热卖商品信息
     */
    @GetMapping("/sales")
    public Result<PageInfo<GoodsInfo>> findSales(@RequestParam(defaultValue = "1") Integer pageNum,
                                                     @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<GoodsInfo> page = goodsInfoService.findSales(pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 添加商品
     * @param goodsInfo 商品信息
     * @return  添加结果状态
     */
    @PostMapping
    public Result save(@RequestBody GoodsInfo goodsInfo){
        int add = goodsInfoService.add(goodsInfo);
        if(add!=1){
           throw new CustomException(ResultCode.ERROR);
        }
        return Result.success();
    }

    /**
     * 修改商品
     * @param goodsInfo 商品信息
     * @return 修改结果状态
     */
    @PutMapping
    public Result remove(@RequestBody GoodsInfo goodsInfo){
        if(goodsInfoService.update(goodsInfo)!=1){
            throw new CustomException(ResultCode.ERROR);
        }
        return Result.success();
    }

    /**
     * 删除商品
     * @param id 商品id
     * @return  删除状态
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id){
        if(goodsInfoService.delete(id)!=1){
            throw new CustomException(ResultCode.ERROR);
        }
        return Result.success();
    }

    /**
     * 根据商品id查询商品信息
     * @param id    商品id
     * @return  商品信息
     */
    @GetMapping
    public Result<GoodsInfo> findGoods(@RequestParam Long id){
        GoodsInfo goodsInfo = goodsInfoService.selectById(id);
        return Result.success(goodsInfo);
    }

    /**
     * 根据商品类型查询商品信息
     * @param typeId    商品类型id
     * @return  商品信息查询结果
     */
    @GetMapping("/type/{typeId}")
    public Result<List<GoodsInfo>> findGoodsByType(@PathVariable Long typeId){
        List<GoodsInfo> goodsByType = goodsInfoService.getGoodsByType(typeId);
        return Result.success(goodsByType);
    }
}
