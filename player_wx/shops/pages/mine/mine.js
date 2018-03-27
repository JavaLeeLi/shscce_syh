var app = getApp()
Page({
    data: {
        userInfo: {},
        userListInfo: [{
            icon: '',
            text: '我的订单',
            isunread: true,
            unreadNum: 2
        }, {
            icon: '',
            text: '我的代金券',
            isunread: false,
            unreadNum: 2
        }, {
            icon: '',
            text: '我的拼团',
            isunread: true,
            unreadNum: 1
        }, {
            icon: '',
            text: '收货地址管理'
        }, {
            icon: '',
            text: '联系客服'
        }, {
            icon: '',
            text: '常见问题'
        }],
        showModal: false,//弹窗认证
    },

    onLoad: function () {
        var that = this
        //调用应用实例的方法获取全局数据
        app.getUserInfo(function (userInfo) {
            //更新数据
            that.setData({
                userInfo: userInfo
            })
        })
    }
    ,
    // 认证
    tapPersonal: function (e) {
        wx.showModal({
            title: '提示',
            content: '这是一个模态弹窗',
            success: function (res) {
                if (res.confirm) {
                    console.log('用户点击确定')
                } else if (res.cancel) {
                    console.log('用户点击取消')
                }
            }
        })
    },
    /**
   * 弹窗
   */
    showDialogBtn: function () {
        this.setData({
            showModal: true
        })
    },
    /**
  * 弹出框蒙层截断touchmove事件
  */
    preventTouchMove: function () {
    },
    /**
     * 隐藏模态对话框
     */
    hideModal: function () {
        this.setData({
            showModal: false
        });
    },
    /**
  * 对话框取消按钮点击事件
  */
    onCancel: function () {
        this.hideModal();
        wx:wx.navigateTo({
            url: '../mine/mycertification/mycertification',
            success: function(res) {},
            fail: function(res) {},
            complete: function(res) {},
        })
    },
    /**
     * 对话框确认按钮点击事件
     */
    onConfirm: function () {
        this.hideModal();
        wx.navigateTo({
            url: '../mine/certification/certification',
        })
    }
    ,
    // 收藏
    showCollect:function(e){
        wx.navigateTo({
            url: '../mine/collect/collect',
        })
    },
    //关注
    showPayto:function(e){
        wx.navigateTo({
            url: '../mine/focuson/focuson',
        })
    }
    ,
    //关注
    showIncome:function(e){
        wx.navigateTo({
            url: '../mine/focuson/focuson',
        })
    },
    /**
    优惠券
     */
    showCoupon:function(e){
        wx.navigateTo({
            url: '../mine/coupons/coupons',
        })
    },
    //钱包
    showPurse:function(e){
        wx.navigateTo({
            url: '../mine/servicecenter/purse/purse',
        })
    },
    //草稿
    showRoughdraft:function(e){
        wx.navigateTo({
            url: '../mine/roughdraft/roughdraft',
        })
    },
    //地址
    showAddress:function(e){
        wx.navigateTo({
            url: '../mine/address/address',
        })
    },
    //服务
    showService:function(e){
        wx.navigateTo({
            url: '../mine/servicecenter/servicecenter',
        })
    },
    showSetting:function(){
        wx.navigateTo({
            url: '../../../setting/setting',
        })
    }

})