package com.javaclimb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 文件信息
 */
@TableName("nx_system_file_info")
public class NxSystemFileInfo {
    @TableId(type= IdType.AUTO)
    private Long id;
    private String originName;
    private String fileName;
    private Long goodsId;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    @Override
    public String toString() {
        return "NxSystemFileInfo{" +
                "id=" + id +
                ", originName='" + originName + '\'' +
                ", fileName='" + fileName + '\'' +
                ", goodsId=" + goodsId +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public NxSystemFileInfo(String originName, String fileName, Long goodsId) {
        this.originName = originName;
        this.fileName = fileName;
        this.goodsId = goodsId;
    }

    public NxSystemFileInfo() {
    }
}
