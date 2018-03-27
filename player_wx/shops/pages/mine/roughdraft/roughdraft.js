var tabs = [
    {
        name: "出售"
    },
    {
        name: "拍卖"
    },
    {
        name: "求购"
    }
];
Page({

    /**
     * 页面的初始数据
     */
    data: {
        tabs: tabs,
        indicatorDots: true,
        autoplay: true,
        interval: 5000,
        duration: 1000,
        userInfo: {},
        imgBannerUrls: [],//首页banner数据
        imgHomeLists: [],//导航er
        hotGoodList: [], //热卖商品
        tabs: tabs,
        slideOffset: 0,//指示器每次移动的距离
        activeIndex: 0,//当前展示的Tab项索引
        sliderWidth: 96,//指示器的宽度,计算得到
        contentHeight: 0,//页面除去头部Tabbar后，内容区的总高度，计算得到  
        current: 0,
        imgheights: [],
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        var that = this;
        //   计算切换高度 
        wx.getSystemInfo({
            success: function (res) {
                that.setData({
                    //计算相关宽度
                    sliderWidth: res.windowWidth / that.data.tabs.length,
                    sliderOffset: res.windowWidth / that.data.tabs.length * that.data.activeIndex,
                    contentHeight: res.windowHeight * 2//后续设置 
                });
            }
        });

    },
    bindChange: function (e) {
        var current = e.detail.current;
        this.setData({
            activeIndex: current,
            sliderOffset: this.data.sliderWidth * current
        });
        console.log("bindChange:" + current);
    },

    navTabClick: function (e) {
        this.setData({
            sliderOffset: e.currentTarget.offsetLeft,
            activeIndex: e.currentTarget.id
        });
        console.log("navTabClick:" + e.currentTarget.id);
    },
    showInput: function () {
        this.setData({
            inputShowed: true
        });
    },
    hideInput: function () {
        this.setData({
            inputVal: "",
            inputShowed: false
        });
    },
    clearInput: function () {
        this.setData({
            inputVal: ""
        });
    },
    inputTyping: function (e) {
        this.setData({
            inputVal: e.detail.value
        });
    },
    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function () {

    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function () {

    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide: function () {

    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload: function () {

    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh: function () {

    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function () {

    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function () {

    }
})