import {request} from "../../request/index";
import {config} from "../../request/config";
Page({

  /**
   * 页面的初始数据
   */
  data: {
    defaultImageUrl:'../../imgs/default.png',
    list:[]
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onLoad(options) {
    let id=options.id;
    let state=options.state;
    request({url:"/order/"+id}).then(res=>{
      if(res.code==='0'){
        let list=res.data.goodsList;
        list.forEach(item=>{
          let imgSrc=config.baseFileUrl+item.fileName;
          if(!item.fileName){
            imSrc=this.data.defaultImageUrl;
          }
          item.url=imgSrc;
        })
        console.log(list)
        this.setData({
          list:list,
          state:state
        })
      }
    })
  },
  common(e){
    let goodsId=e.currentTarget.dataset.id;
    let sellerId=e.currentTarget.dataset.ids;

    wx.navigateTo({
      url: '/pages/comment/index?goodsId='+goodsId+"&&sellerId="+sellerId,
    })
  }
})