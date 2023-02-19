package com.javaclimb.controller;

import com.github.pagehelper.PageInfo;
import com.javaclimb.common.Result;
import com.javaclimb.common.ResultCode;
import com.javaclimb.entity.TypeInfo;
import com.javaclimb.exception.CustomException;
import com.javaclimb.service.TypeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品类别相关
 */
@RestController
@RequestMapping("/type")
public class TypeInfoController {
    private TypeInfoService typeInfoService;
    @Autowired(required = false)
    public TypeInfoController(TypeInfoService typeInfoService) {
        this.typeInfoService = typeInfoService;
    }

    /**
     * 根据商品类别分页模糊查询
     * @param name 用户名
     * @param pageNum 第几页
     * @param pageSize 每页条数
     * @return 返回查询信息及状态码
     */
    @GetMapping("/{pageNum}/{name}")
    public Result<PageInfo<TypeInfo>> page(@PathVariable String name,
                                           @PathVariable Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<TypeInfo> page = typeInfoService.findPage(pageNum, pageSize, name);
        if(page.getSize()==0){
            throw new CustomException(ResultCode.ADV_NOT_EXIST_ERROR);
        }
        return Result.success(page);
    }

    /**
     * 分页查询所有商品类别
     * @param pageNum   第几页
     * @param pageSize  每页记录
     * @return  查询结果
     */
    @GetMapping("/{pageNum}")
    public Result<PageInfo<TypeInfo>> page(@PathVariable Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<TypeInfo> page = typeInfoService.findPage(pageNum, pageSize, null);
        return Result.success(page);
    }

    /**
     * 查询所有类型
     * @return 商品类型
     */
    @GetMapping
    public Result<List<TypeInfo>> page(){
        List<TypeInfo> type = typeInfoService.findType();
        return Result.success(type);
    }

    /**
     * 添加商品类别
     * @param typeInfo 商品类别信息
     * @return  添加结果状态
     */
    @PostMapping
    public Result save(@RequestBody TypeInfo typeInfo){
        int add = typeInfoService.add(typeInfo);
        if(add!=1){
           throw new CustomException(ResultCode.ERROR);
        }
        return Result.success();
    }

    /**
     * 修改商品类别
     * @param typeInfo 商品类别信息
     * @return 修改结果状态
     */
    @PutMapping
    public Result remove(@RequestBody TypeInfo typeInfo){
        if(typeInfoService.update(typeInfo)!=1){
            throw new CustomException(ResultCode.ERROR);
        }
        return Result.success();
    }

    /**
     * 删除商品类别
     * @param id 商品类别id
     * @return  删除状态
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id){
        if(typeInfoService.delete(id)!=1){
            throw new CustomException(ResultCode.ERROR);
        }
        return Result.success();
    }
}
