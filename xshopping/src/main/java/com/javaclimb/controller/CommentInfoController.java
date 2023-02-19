package com.javaclimb.controller;

import com.github.pagehelper.PageInfo;
import com.javaclimb.common.Result;
import com.javaclimb.common.ResultCode;
import com.javaclimb.entity.CommentInfo;
import com.javaclimb.entity.OrderInfo;
import com.javaclimb.exception.CustomException;
import com.javaclimb.service.CommentInfoService;
import com.javaclimb.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentInfoController {
    private OrderInfoService orderInfoService;
    private CommentInfoService commentInfoService;

    public CommentInfoController(OrderInfoService orderInfoService, CommentInfoService commentInfoService) {
        this.orderInfoService = orderInfoService;
        this.commentInfoService = commentInfoService;
    }

    @Autowired(required = false)

    @GetMapping
    public Result<PageInfo<CommentInfo>> findComment(@RequestParam(required = false) Long goodId
            ,@RequestParam(required = false) String content,@RequestParam(required = false) Long sellerId
            ,@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "1") Integer pageNum){
        PageInfo<CommentInfo> comment = commentInfoService.getCommentByGoodsId(pageNum, pageSize, goodId, content, sellerId);
        return Result.success(comment);
    }
    @PostMapping
    public Result addComment(@RequestBody CommentInfo commentInfo){
        int add = commentInfoService.add(commentInfo);
        if (add!=1){
            throw new CustomException(ResultCode.ERROR);
        }
        orderInfoService.updateOrder(new OrderInfo(commentInfo.getOrderId(),"已评价"));
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result deleteComment(@PathVariable Long id){
        int delete = commentInfoService.deleteCommentById(id);
        if (delete!=1){
            throw new CustomException(ResultCode.ERROR);
        }
        return Result.success();
    }
}
