import {request} from "../../request/index";
Page({

  /**
   * 页面的初始数据
   */
  data: {
    goodsId:0,
    sellerId:0,
    content:""
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onLoad(options) {
    this.setData({
      sellerId:options.sellerId,
      goodsId:options.goodsId
    })
  },
  onComment(e){
    this.setData({
      content:e.detail.value
    })
  },
  // 提交评价
  comment(){
    let user=wx.getStorageSync('user');
    let data={userName:user.name,sellerId:this.data.sellerId,goodsId:this.data.goodsId,content:this.data.content}
    console.log(data)
    request({url:"/comment/",data:data,method:"POST"}).then(res=>{
      if(res.code==='0'){
        wx.showToast({
          title: '评价成功',
        })
        wx.switchTab({
          url: '/pages/user/index',
        })
      }else{
        wx.showToast({
          title: res.msg,
          icon:'error'
        })
      }
    })
  }
})