//同时发送Ajax请求次数
let ajaxTimes=0;
export const request=(params)=>{
  ajaxTimes++;
  wx.showLoading({
    title: '加载中',
    mask:true
  })
  const baseUrl="http://192.168.116.1:8888";
  return new Promise((resolve,reject) => {
    wx.request({
      url: baseUrl+params.url,
      data:params.data,
      method:params.method,
      success:(result)=>{
        resolve(result.data);
      },
      fail:(err)=>{
        reject(err);
      },
      complete:()=>{
        ajaxTimes--;
        if(ajaxTimes===0){
          wx.hideLoading();
        }
      }
    })
  })
}