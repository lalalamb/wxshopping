import {request} from "../../request/index";
Page({

  /**
   * 页面的初始数据
   */
  data: {
    isLogin:0,//0未登录,1登录
    userInfo:{},//用户信息
    avatarUrl:"",//用户头像
    balance:0//余额
  },

  getUserInfo:function (e) {
    wx.navigateTo({
      url: '/pages/login/index?isTabBar=1&url=/pages/user/index'
    })
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {
    let user=wx.getStorageSync('user');
    let avatarUrl=wx.getStorageSync('avatarUrl');
    if(user){
      this.setData({
        isLogin:1,
        userInfo:user,
        avatarUrl:avatarUrl
      })
      request({url:"/user/someone/"+user.id}).then(res=>{
      if(res.code==='0'){
      this.setData({
        balance:res.data.balance
      });
      }else{
        wx.showToast({
          title: res.msg,
          icon:'error'
        })
      }
    })
    }
    
  },
  /**
   * 转向订单列表
   */
  navigateToOrder(e){
    let user=wx.getStorageSync('user');  
    if(!user){
      wx.showToast({
        title: '请先登录',
        icon:'none'
      })
    }else{
      let state=e.currentTarget.dataset.state;
      wx.navigateTo({
        url: '/pages/order/index?state='+state,
      })
    }
  },
  recharge(){
    let user=wx.getStorageSync('user');
    if(!user){
      wx.showToast({
        title: '请先登录',
      })
    }else{
      wx.navigateTo({
        url: '/pages/pay/index',
      })
    }
  },
  update(){
    let user=wx.getStorageSync('user');
    if(!user){
      wx.showToast({
        title: '请先登录',
      })
    }else{
      wx.navigateTo({
        url: '/pages/address/index',
      })
    }
  }
})