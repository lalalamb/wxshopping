import {request} from "../../request/index";
import {config} from "../../request/config";
Page({

  /**
   * 页面的初始数据
   */
  data: {
    defaultImageUrl:'../../imgs/default.png',
    cart:[],
    totalPrice:0,
    totalNum:0,
    login:0
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {
    this.getCartInfo();
  },
  getCartInfo(){
    let user=wx.getStorageSync('user');
    if(!user){
      wx.setData({
        login:0
      })
    }else{
      this.setData({
        login:1
      })
    }
    request({url:'/cart/'+user.id}).then(res=>{
      if(res.code==='0'){
        let cartList=res.data;
        let totalPrice=0;
        let totalNum=0;
        cartList.forEach(item=>{
          totalNum+=item.number;
          totalPrice+=item.number*item.price*item.discount;
          let imgSrc=config.baseFileUrl+item.fileName;
          if(!item.fileName){
            imSrc=this.data.defaultImageUrl;
          }
          item.url=imgSrc;
        })
        this.setData({
          cart:cartList,
          totalNum:totalNum,
          totalPrice:totalPrice.toFixed(2)
        })
      }
    })
  },
  //商品数量编辑功能
  handleItemNumEdit(e){
    //获取传来的参数
    const {operation,id}=e.currentTarget.dataset;
    //获取到购物车数组
    let cart=this.data.cart;
    //获取到需要修改的商品的索引
    const index=cart.findIndex(v=>v.cartId===id);
    //判断是否删除
    if(cart[index].number===1&&operation===-1){
      //弹出提示
      wx.showModal({
        content: '您是否要删除该商品',
        complete: (res) => {
          if (res.confirm) {
            request({url:'/cart/'+id,method:'DELETE'}).then(res=>{
              if(res.code==='0'){
                let cart=this.data.cart;
                cart.splice(index,1);
                let totalPrice=0;
    let totalNum=0;
    cart.forEach(item=>{
      totalNum+=item.number;
      totalPrice+=item.number*item.price*item.discount;
    })
    this.setData({
      cart:cart,
      totalNum:totalNum,
      totalPrice:totalPrice.toFixed(2)
    })
              }else{
                wx.showToast({
                  title: res.msg,
                  icon:'error'
                })
              }
            })
          }
        }
      })
    }else{
      //修改数量
      let cart=this.data.cart;
      cart[index].number+=operation;
       //重新计算总价和总数量
      let totalPrice=0;
      let totalNum=0;
      cart.forEach(item=>{
      totalNum+=item.number;
      totalPrice+=item.number*item.price*item.discount;
      })
      this.setData({
        cart:cart,
        totalNum:totalNum,
        totalPrice:totalPrice.toFixed(2)
      })
    }
  },
  handlePay(){
    if(this.data.cart.length==0){
      wx.showToast({
        title: '购物车空空如也',
        icon:'none'
      })
      return;
    }
    let user=wx.getStorageSync('user');
    let data={userId:user.id,goodsList:this.data.cart};
    request({url:'/order',data:data,method:"POST"}).then(res=>{
      if(res.code==='0'){
        wx.showToast({
          title: '订单提交成功',
        })
        wx.navigateTo({
          url: '/pages/order/index?state='+"待付款",
        })
      }else{
        wx.showToast({
          title: res.msg,
          icon:'none'
        })
      }
    })
  }
})