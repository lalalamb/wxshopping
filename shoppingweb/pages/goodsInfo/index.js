import {request} from "../../request/index";
import {config} from "../../request/config";
Page({

  /**
   * 页面的初始数据
   */
  data: {
    goodsId:0,//商品id
    swiperList:[],//商品图片
    obj:{},//商品信息
    commentList:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    const id=options.id;
    this.setData({
      goodsId:id
    })
    this.getDetail(id);
    this.getComment(id);
  },
  getComment(goodsId){
    request({url:"/comment?goodsId="+goodsId+"&pageSize=100"}).then(res=>{
      if(res.code==='0'){
        let commentList =res.data.list;
        this.setData({
          commentList
        })
      }else{
        wx.showToast({
          title: res.msg,
          icon:'none'
        })
      }
    })
  },
  getDetail(id){
    request({url:"/goods?id="+id}).then(res=>{
      if(res.code==='0'){
        let obj=res.data;
        request({url:"/files/"+id}).then(res=>{
          if(res.code==='0'){
            let swiperList=[];
            let list=res.data;
            if(list){
              list.forEach(item=>{
                let imgObj={};
                imgObj.imgSrc=config.baseFileUrl+item.fileName
                swiperList.push(imgObj);
              })
            }
            if(swiperList.length===0){
              swiperList.push({imgSrc:"../../imgs/default.png"});
              swiperList.push({imgSrc:"../../imgs/default.png"});
            }
            this.setData({
              swiperList
            })
          }
        })
        this.setData({
          obj
        })
      }else{
        wx.showToast({
          title: res.msg,
          icon:'none'
        })
      }
    })
  },
  //放入购物车
  handleartAdd(){
    let user=wx.getStorageSync('user');
    if(!user){
      wx.navigateTo({
        url: '/pages/login/index?isTabBar=0&url=/pages/goodsInfo/index$id-'+this.data.goodsId,
      })
      return;
    }
    let data={userId:user.id,level:user.level,goodsId:this.data.goodsId,count:1};
    request({url:"/cart",data:data,method:"POST"}).then(res=>{
      if(res.code==="0"){
        wx.showToast({
          title: '加入购物车成功',
        })
      }else{
        wx.showToast({
          title: res.msg,
          icon:"error"
        })
      }
    })
  }
})