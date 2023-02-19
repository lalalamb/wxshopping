import {request} from "../../request/index";
import {config} from "../../request/config";
Page({

  /**
   * 页面的初始数据
   */
  data: {
    defaultImageUrl:'../../imgs/default.png',
    goodsInfoList:[],     //所有商品列表
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad() {
    this.getGoodsInfoList('')
  },
  getGoodsInfoList(name){  
    request({url:'/goods/1/'+name+'?pageSize=30'})
    .then(res=>{
      if(res.code==='0'){
        let goodsInfoList=res.data.list;
        goodsInfoList.forEach(item=>{
          if(item.fileName==null){
            item.url=this.data.defaultImageUrl;
          }else{
            item.url=config.baseFileUrl+item.fileName;
          }
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
  search:function(e){
    var name=e.detail.value;
    console.log(name);
      
    this.getGoodsInfoList(name);
  }
})