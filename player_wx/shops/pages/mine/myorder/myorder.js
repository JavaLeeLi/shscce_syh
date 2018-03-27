var tabs = [
    {
        src:'../../image/zhifu.png',
        name: "支付"
    },
    {
        src: '../../image/zhifu.png',
        name: "发货"
    },
    {
        src: '../../image/zhifu.png',
        name: "收货"
    },
    {
        src: '../../image/zhifu.png',
        name: "评价"
    }
];
var tabssale = [
    {
        src: '../../image/zhifu.png',
        name: "支付"
    },
    {
        src: '../../image/zhifu.png',
        name: "配送"
    },
    {
        src: '../../image/zhifu.png',
        name: "签收"
    },
    {
        src: '../../image/zhifu.png',
        name: "评价"
    }
];
Page({

    /**
     * 页面的初始数据
     */
    data: {
        tabs: tabs,
        tabssale: tabssale,
        current: 0,
        imgheights: [],
        appraise:false,//评价
        selected: true,
        selected1: false,

        
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

    },
    showshop:function(e){
        console.log(e)
    },
    selected: function (e) {
        this.setData({
            selected1: false,
            selected: true
        })
    },
    selected1: function (e) {
        this.setData({
            selected: false,
            selected1: true
        })
    }
})