import {config} from "../../request/config.js";
import {request} from "../../request/index.js";
Page({
  data:{
    defaultImageUrl:'../../imgs/default.png',
    goodsCarouselList:[],     //轮播图列表
    leftMenuList:[],     //商品类别
    rightContentList:[],     //商品
    currentIndex:4   //被点击的左侧菜单
  },
  onLoad:function (){
    this.getGoodsCarouselList();
    this.getMenuList();
    this.getGoodsList(this.data.currentIndex);
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
  getMenuList(){
    request({url:"/type"}).then(res=>{
      if(res.code==="0"){
        let list=res.data;
        let id=list[0].id;
        this.setData({
          currentIndex:id,
          leftMenuList:list
        })
        console.log(this.data.leftMenuList)
      }else{
        wx.showToast({
          title: res.msg,
          icon:'none'
        })
      }
    })
  },
  getGoodsList(typeId){
    request({url:"/goods/type/"+typeId}).then(res=>{
      if(res.code==='0'){
        let goodsList=res.data;
        goodsList.forEach(item=>{
          if(item.fileName==null){
            item.url=this.data.defaultImageUrl;
          }else{
            item.url=config.baseFileUrl+item.fileName;
          }
        })
        this.setData({
          rightContentList: goodsList
        })
      }else{
        wx.showToast({
          title: res.msg,
          icon:'none'
        })
      }
    })
  },
  handleItemTap(e){
    let id=e.currentTarget.dataset.id;
    this.getGoodsList(id);
    this.setData({
      currentIndex:id,
    })
  }
});
