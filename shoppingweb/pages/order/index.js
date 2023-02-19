import {request} from "../../request/index";
import {config} from "../../request/config";
Page({

  /**
   * 页面的初始数据
   */
  data: {
    state:"",//订单状态
    dataList:[]//订单列表
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    const state=options.state;
    this.setData({
      state
    })
    this.getOrderData(state);
  },

  getOrderData(state){
    let user=wx.getStorageSync('user');
    let url='/order/1/'+user.id+"?pageSize=100";
    if( state!="all"){
      url=url+"&state="+state;
    }
    request({url:url}).then(res=>{
      if(res.code==='0'){
        let list=res.data.list;
        list.forEach((item,index)=>{
          item.url=config.baseFileUrl+item.fileName;
        })
        this.setData({
          dataList:list
        })
      }else{
        wx.showToast({
          title: res.msg,
          icon:'none'
        })
      }
    })
  },
  payGoods(e){
    let user=wx.getStorageSync('user');
    let id=e.currentTarget.dataset.id;
    let userId=user.id;
    let linkAddress=user.address;
    let linkPhone=user.phone;
    let linkMan=user.nickname;
    let state=e.currentTarget.dataset.state;
    let totalPrice=e.currentTarget.dataset.ids;
    let data={id,state,totalPrice,userId,linkAddress,linkPhone,linkMan};
    console.log(data)
    let url="/order/";
    request({url:url,data:data,method:"PUT"}).then(res=>{
      if(res.code==='0'){
        wx.showToast({
          title: '操作成功',
        })
        this.getOrderData(this.data.state);
      }else{
        wx.showToast({
          title: res.msg,
          icon:'error'
        })
      }
    })
  },
  deleteOrder(e){
    let id=e.currentTarget.dataset.id;
    let state=e.currentTarget.dataset.state;
    request({url:"/order/"+id+"?state="+state,method:"DELETE"}).then(res=>{
      if(res.code==='0'){
        wx.showToast({
          title: '操作成功',
        })
        this.getOrderData(this.data.state);
      }else{
        wx.showToast({
          title: res.msg,
        })
      }
    })
  }
})