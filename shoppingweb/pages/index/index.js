import {config} from "../../request/config.js";
import {request} from "../../request/index.js";
Page({
  data:{
    defaultImageUrl:'../../imgs/default.png',
    goodsCarouselList:[],     //轮播图列表
    goodsInfoList:[],     //所有商品列表
    goodsSalesList:[],     //热卖商品列表
    goodsRecommendList:[]   //推荐商品列表
  },
  onLoad:function (){
    this.getGoodsCarouselList();
    this.getGoodsRecommendList();
    this.getGoodsSalesList();
    this.getGoodsInfoList();
  },
  //获取轮播图
  getGoodsCarouselList(){  
    request({url:'/files'})
    .then(res=>{
      if(res.code==='0'){
        let goodsCarouselList=res.data;
        if(!goodsCarouselList||goodsCarouselList.length===0){
          goodsCarouselList.push({"name":"默认1","url":this.data.defaultImageUrl});
          goodsCarouselList.push({"name":"默认2","url":this.data.defaultImageUrl});
          goodsCarouselList.push({"name":"默认3","url":this.data.defaultImageUrl});
        }else if(goodsCarouselList.length>4){
          goodsCarouselList=goodsCarouselList.slice(0,4);
        }
        goodsCarouselList.forEach(item=>{
            item.url=config.baseFileUrl+item.fileName;
        })
        this.setData({
          goodsCarouselList
        })
      }else{
        wx.showToast({
          title: res.msg,
          icon:'none'
        })
      }
    })
  },
    //获取推荐商品
    getGoodsRecommendList(){  
      request({url:'/goods/recommend'})
      .then(res=>{
        if(res.code==='0'){
          let goodsRecommendList=res.data.list;
          if(!goodsRecommendList||goodsRecommendList.length===0){
            goodsRecommendList.push({"name":"默认1","url":this.data.defaultImageUrl});
            goodsRecommendList.push({"name":"默认2","url":this.data.defaultImageUrl});
            goodsRecommendList.push({"name":"默认3","url":this.data.defaultImageUrl});
          }else if(goodsRecommendList.length>4){
            goodsRecommendList=goodsRecommendList.slice(0,4);
          }
          goodsRecommendList.forEach(item=>{
              item.url=config.baseFileUrl+item.fileName;
          })
          this.setData({
            goodsRecommendList
          })
        }else{
          wx.showToast({
            title: res.msg,
            icon:'none'
          })
        }
      })
    },
    // 获取热卖商品列表
    getGoodsSalesList(){  
      request({url:'/goods/sales'})
      .then(res=>{
        if(res.code==='0'){
          let goodsSalesList=res.data.list;
          if(!goodsSalesList||goodsSalesList.length===0){
            goodsSalesList.push({"name":"默认1","url":this.data.defaultImageUrl});
            goodsSalesList.push({"name":"默认2","url":this.data.defaultImageUrl});
            goodsSalesList.push({"name":"默认3","url":this.data.defaultImageUrl});
          }else if(goodsSalesList.length>4){
            goodsSalesList=goodsSalesList.slice(0,4);
          }
          goodsSalesList.forEach(item=>{
              item.url=config.baseFileUrl+item.fileName;
          })
          this.setData({
            goodsSalesList
          })
        }else{
          wx.showToast({
            title: res.msg,
            icon:'none'
          })
        }
      })
    },
    // 获取所有商品列表
    getGoodsInfoList(){  
      request({url:'/goods/1?pageSize=100'})
      .then(res=>{
        if(res.code==='0'){
          let goodsInfoList=res.data.list;
          goodsInfoList.forEach(item=>{
              item.url=config.baseFileUrl+item.fileName;
          })
          this.setData({
            goodsInfoList
          })
        }else{
          wx.showToast({
            title: res.msg,
            icon:'none'
          })
        }
      })
    },
    bindfocus(){
      wx.navigateTo({
        url: '/pages/search/index',
      })
    }
});
