package com.javaclimb.controller;

import com.javaclimb.common.Result;
import com.javaclimb.entity.OrderInfo;
import com.javaclimb.service.CommentInfoService;
import com.javaclimb.service.GoodsInfoService;
import com.javaclimb.service.OrderInfoService;
import com.javaclimb.vo.EchartsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/index")
public class IndexController {
    private CommentInfoService commentInfoService;
    private OrderInfoService orderInfoService;
    private GoodsInfoService goodsInfoService;
    @Autowired(required = false)
    public IndexController(CommentInfoService commentInfoService, OrderInfoService orderInfoService,
                           GoodsInfoService goodsInfoService) {
        this.commentInfoService = commentInfoService;
        this.orderInfoService = orderInfoService;
        this.goodsInfoService = goodsInfoService;
    }

    /**
     * 获取首页顶部数据
     * @return onlineCount 在线用户数量 commentNum 评论总数 totalPrice 总销售额 salesAll 总销量
     */
    @GetMapping("/{userId}/{level}")
    public Result<Map<String,String>> getHeader(HttpServletRequest request, @PathVariable Long userId,
                                                @PathVariable Integer level){
        Map<String, String> map = new HashMap<>();
        HttpSession session = request.getSession();
        Integer onlineCount = (Integer)session.getServletContext().getAttribute("onlineCount");
        map.put("onlineCount",onlineCount+"");
        int commentNum = commentInfoService.getCommentNum(userId, level);
        map.put("commentNum",commentNum+"");
        Double totalPrice = orderInfoService.getTotalPrice(userId, level);
        map.put("totalPrice",totalPrice+"");
        int salesAll = goodsInfoService.getSalesAll(userId, level);
        map.put("salesAll",salesAll+"");
        return Result.success(map);
    }
    @GetMapping("/{userId}/{level}/{typeId}")
    private Result<List<EchartsData>> getEchartsData(@PathVariable Long typeId , @PathVariable Long userId,
                                               @PathVariable Integer level){
        List<EchartsData> salesByType = goodsInfoService.getSalesByType(userId, level, typeId);
        return Result.success(salesByType);
    }
    @GetMapping("/type/{userId}/{level}")
    private Result<List<EchartsData>> getEchartsData(@PathVariable Long userId, @PathVariable Integer level){
        List<EchartsData> salesByType = goodsInfoService.getSales(userId, level);
        return Result.success(salesByType);
    }
}
