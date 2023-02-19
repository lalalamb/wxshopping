package com.javaclimb.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import com.javaclimb.common.Result;
import com.javaclimb.common.ResultCode;
import com.javaclimb.entity.NxSystemFileInfo;
import com.javaclimb.exception.CustomException;
import com.javaclimb.service.NxSystemFileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

/**
 * 商品相关
 */
@RestController
@RequestMapping("/files")
public class NxSystemFileInfoController {
    //图片基本路径
    private static final String BASE_PATH=System.getProperty("user.dir")+"/src/main/resources/static/files/";
    private NxSystemFileInfoService nxSystemFileInfoService;
    @Autowired(required = false)
    public NxSystemFileInfoController(NxSystemFileInfoService nxSystemFileInfoService) {
        this.nxSystemFileInfoService = nxSystemFileInfoService;
    }

    /**
     * 添加图片
     * @param file  二进制图片
     * @param goodsId   商品id
     * @return  添加结果
     * @throws IOException  文件上传异常
     */
    @PostMapping("/{goodsId}")
    public Result upload(MultipartFile file, @PathVariable Long goodsId) throws IOException {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            return Result.error("1001","文件名不能为空");
        }
        if(!originalFilename.endsWith(".jpg")
                &&!originalFilename.endsWith(".png")
                &&!originalFilename.endsWith(".svg")
                &&!originalFilename.endsWith(".bmp")){
            return Result.error("1002","文件格式错误");
        }
        String fileName= FileUtil.mainName(originalFilename)+System.currentTimeMillis()+"."+FileUtil.extName(originalFilename);
//        //文件上传
        FileUtil.writeBytes(file.getBytes(),BASE_PATH+fileName);
       //保存到数据库
        NxSystemFileInfo nxSystemFileInfo = new NxSystemFileInfo(originalFilename, fileName,goodsId);
        int add = nxSystemFileInfoService.add(nxSystemFileInfo);
        if(add==1){
            return Result.success();
        }
        return Result.error("1003","文件上传失败");
    }

    /**
     * 获取商品所有图片
     * @param id    商品id
     * @return  图片信息
     */
    @GetMapping("/{id}")
    public Result<List<NxSystemFileInfo>> download(@PathVariable Long id) {
        List<NxSystemFileInfo> nxSystemFileInfos = nxSystemFileInfoService.selectByGoodsId(id);
        if (CollectionUtil.isEmpty(nxSystemFileInfos)){
            throw new CustomException("1001","未上传图片");
        }
        return Result.success(nxSystemFileInfos);
    }

    /**
     * 获取轮播图图片
     * @param pageSize  图片个数
     * @return  图片信息
     */
    @GetMapping
    public Result<List<NxSystemFileInfo>> findImg(@RequestParam(defaultValue = "3") Integer pageSize) {
        List<NxSystemFileInfo> nxSystemFileInfos = nxSystemFileInfoService.selectDifferentGoods(pageSize);
        return Result.success(nxSystemFileInfos);
    }
    /**
     * 删除文件
     * @param id 商品id
     * @return  删除状态
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id){
        if(nxSystemFileInfoService.delete(id)!=1){
            throw new CustomException(ResultCode.ERROR);
        }
        return Result.success();
    }
}
