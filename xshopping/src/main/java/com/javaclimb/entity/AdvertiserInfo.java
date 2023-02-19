package com.javaclimb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 公告
 */
@TableName("advertiser_info")
public class AdvertiserInfo {
    @TableId(type= IdType.AUTO)
    private Long id;
    private String name;
    private String content;
    private String createTime;

    @Override
    public String toString() {
        return "AdvertiserInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public AdvertiserInfo( String name, String content, String createTime) {
        this.name = name;
        this.content = content;
        this.createTime = createTime;
    }

    public AdvertiserInfo() {
    }
}
