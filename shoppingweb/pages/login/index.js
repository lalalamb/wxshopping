import {request} from "../../request/index";
Page({

  /**
   * 页面的初始数据
   */
  data: {
    url:'',//登录结束后要跳转的地址
    idTabBar:0,//0没有bar 1有bar
    name:'',
    password:""
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    let url=options.url.replace("$","?").replace("-","=");
    let isTabBar=options.isTabBar;
    this.setData({
      url:url,
      isTabBar:isTabBar
    })
  },
  onName(e){
    this.setData({
      name:e.detail.value
    })
  },
  onPassword(e){
    this.setData({
      password:e.detail.value
    })
  },
  getUserInfo:function () {
    var data={
      name:this.data.name,
      password:this.data.password,
    }
    request({url:"/account",method:"POST",data:data}).then(res=>{
      if(res.code==='0'){
        wx.showToast({
          title: '登录成功',
          mask:true
        })
        //获取后存储本地数据
        this.setData({
          isLogin:1,
          userInfo:res.data
        });
      //存到local storage
        wx.setStorageSync('user',res.data)
        if(this.data.isTabBar==="0"){
          wx.navigateTo({
            url: this.data.url,
        })
        }else{
          wx.switchTab({
            url: this.data.url,
          })
        }
      }else{
        wx.showToast({
          title: res.msg,
          mask:true
        })
      }
    })
  },
})