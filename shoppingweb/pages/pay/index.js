import {request} from "../../request/index";
Page({

  /**
   * 页面的初始数据
   */
  data: {
    recharge:"",
    balance:0
  },

  
  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {
    let user=wx.getStorageSync('user');  
    if(!user){
      wx.showToast({
        title: '请先登录',
        icon:'none'
      })
    }else{
      request({url:"/user/someone/"+user.id}).then(res=>{
        if(res.code==='0'){
        this.setData({
          balance:res.data.balance
        });
      }
    })
  }
  },
  onPrice(e){
    this.setData({
      recharge:e.detail.value
    })
  },
  recharge(e){
    let user=wx.getStorageSync('user'); 
    let data={id:user.id,balance:parseFloat(this.data.recharge)+parseFloat(this.data.balance)};
    console.log(data)
    request({url:"/user",data:data,method:"PUT"}).then(res=>{
      if(res.code==='0'){
        wx.showToast({
          title: '充值成功',
          mask:true
        })
        wx.switchTab({
          url: '/pages/user/index',
        })
      }else{
        wx.showToast({
          title: res.msg,
          mask:true
        })
      }
    })
    }
})