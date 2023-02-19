package com.javaclimb.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javaclimb.entity.CommentInfo;
import com.javaclimb.mapper.CommentInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentInfoService {
    private CommentInfoMapper commentInfoMapper;
    @Autowired(required = false)
    public CommentInfoService(CommentInfoMapper commentInfoMapper) {
        this.commentInfoMapper = commentInfoMapper;
    }

    /**
     * 添加评论
     * @param commentInfo   评论信息
     */
    public int add(CommentInfo commentInfo){
        String time = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm");
        commentInfo.setCreateTime(time);
        return commentInfoMapper.insert(commentInfo);
    }

    /**
     * 查询某商品评论信息
     * @param goodsId   商品id
     * @return  评论列表
     */
    public PageInfo<CommentInfo> getCommentByGoodsId(Integer pageNum,Integer pageSize,Long goodsId,String content,Long sellerId){
        PageHelper.startPage(pageNum,pageSize);
        LambdaQueryWrapper<CommentInfo> wrapper = new LambdaQueryWrapper<>();

        if(goodsId!=null){
            wrapper.eq(CommentInfo::getGoodsId,goodsId);
        }
        if(sellerId!=null){
            wrapper.eq(CommentInfo::getSellerId,sellerId);
        }
        if(StrUtil.isNotEmpty(content)){
            wrapper.like(CommentInfo::getContent,content);
            wrapper.orderByAsc(CommentInfo::getGoodsId);
        }
        List<CommentInfo> commentInfos = commentInfoMapper.selectList(wrapper);
        return PageInfo.of(commentInfos);
    }

    /**
     * 跟据id删除评论
     * @param id    评论id
     */
    public int deleteCommentById(Long id){
        return commentInfoMapper.deleteById(id);
    }

    /**
     * 获取评论条数
     * @param userId    用户id
     * @return  评论条数
     */
    public int getCommentNum(Long userId,Integer level){
        LambdaQueryWrapper<CommentInfo> wrapper = new LambdaQueryWrapper<>();
        if(level==2){
            wrapper.eq(CommentInfo::getSellerId,userId);
        }
        return commentInfoMapper.selectCount(wrapper);
    }
}
