import {request} from "../../request/index";
Page({

  /**
   * 页面的初始数据
   */
  data: {
    linkAddress:"",
    linkPhone:"",
    linkMan:""
  },

  
  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {
    let user=wx.getStorageSync('user');  
    console.log(user)
    if(!user){
      wx.showToast({
        title: '请先登录',
        icon:'none'
      })
    }else{
    this.setData({
      linkAddress:user.address,
      linkMan:user.nickname,
      linkPhone:user.phone
    })
  }
  },
  onAddress(e){
    this.setData({
      linkAddress:e.detail.value
    })
  },
  onMan(e){
    this.setData({
      linkMan:e.detail.value
    })
  },
  onPhone(e){
    this.setData({
      linkPhone:e.detail.value
    })
  },
  update(e){
    let user=wx.getStorageSync('user'); 
    user.phone=this.data.linkPhone;
    user.nickName=this.data.linkMan;
    user.address=this.data.linkAddress;
    console.log(user,this.data)
    request({url:"/user",data:user,method:"PUT"}).then(res=>{
      if(res.code==='0'){
        wx.setStorageSync('user', user)
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