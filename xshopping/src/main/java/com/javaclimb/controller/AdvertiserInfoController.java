package com.javaclimb.controller;

import com.github.pagehelper.PageInfo;
import com.javaclimb.common.Result;
import com.javaclimb.common.ResultCode;
import com.javaclimb.entity.AdvertiserInfo;
import com.javaclimb.exception.CustomException;
import com.javaclimb.service.AdvertiserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 公告相关
 */
@RestController
@RequestMapping("/advertiser")
public class AdvertiserInfoController {
    private AdvertiserInfoService advertiserInfoService;
    @Autowired(required = false)
    public AdvertiserInfoController(AdvertiserInfoService advertiserInfoService) {
        this.advertiserInfoService = advertiserInfoService;
    }

    /**
     * 根据公告名分页模糊查询
     * @param name 用户名
     * @param pageNum 第几页
     * @param pageSize 每页条数
     * @return 返回查询信息及状态码
     */
    @GetMapping("/{pageNum}/{name}")
    public Result<PageInfo<AdvertiserInfo>> page(@PathVariable String name,
                                           @PathVariable Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<AdvertiserInfo> page = advertiserInfoService.findPage(pageNum, pageSize, name);
        if(page.getSize()==0){
            throw new CustomException(ResultCode.ADV_NOT_EXIST_ERROR);
        }
        return Result.success(page);
    }

    /**
     * 分页查询所有公告
     * @param pageNum   第几页
     * @param pageSize  每页记录
     * @return  查询结果
     */
    @GetMapping("/{pageNum}")
    public Result<PageInfo<AdvertiserInfo>> page(@PathVariable Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<AdvertiserInfo> page = advertiserInfoService.findPage(pageNum, pageSize, null);
        return Result.success(page);
    }

    /**
     * 根据id获取公告内容
     * @param id    公告id
     * @return  公告内容
     */
    @GetMapping("/content/{id}")
    public Result<String> content(@PathVariable Long id){
        String content = advertiserInfoService.getContent(id);
        return Result.success(content);
    }
    /**
     * 添加公告
     * @param advertiserInfo 公告信息
     * @return  添加结果状态
     */
    @PostMapping
    public Result save(@RequestBody AdvertiserInfo advertiserInfo){
        int add = advertiserInfoService.add(advertiserInfo);
        if(add!=1){
           throw new CustomException(ResultCode.ERROR);
        }
        return Result.success();
    }

    /**
     * 修改公告
     * @param advertiserInfo 公告信息
     * @return 修改结果状态
     */
    @PutMapping
    public Result remove(@RequestBody AdvertiserInfo advertiserInfo){
        if(advertiserInfoService.update(advertiserInfo)!=1){
            throw new CustomException(ResultCode.ERROR);
        }
        return Result.success();
    }

    /**
     * 删除公告
     * @param id 公告id
     * @return  删除状态
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id){
        if(advertiserInfoService.delete(id)!=1){
            throw new CustomException(ResultCode.ERROR);
        }
        return Result.success();
    }
}
