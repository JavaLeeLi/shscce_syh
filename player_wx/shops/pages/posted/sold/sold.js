var tabs = [
    {
        name: "全部"
    },
    {
        name: "出售"
    },
    {
        name: "拍卖"
    },
    {
        name: "求购"
    },
    {
        name: "拍卖"
    },
    {
        name: "求购"
    }
];
Page({
    data: {
        showModalStatus: false,
        tabs: tabs,
        slideOffset: 0,//指示器每次移动的距离
        activeIndex: 0,//当前展示的Tab项索引
        sliderWidth: 96,//指示器的宽度,计算得到
        contentHeight: 0,//页面除去头部Tabbar后，内容区的总高度，计算得到
        showValue:''
    },
    powerDrawer: function (e) {
        var currentStatu = e.currentTarget.dataset.statu;
        this.util(currentStatu)
    },
    util: function (currentStatu) {
        /* 动画部分 */
        // 第1步：创建动画实例   
        var animation = wx.createAnimation({
            duration: 200,  //动画时长  
            timingFunction: "linear", //线性  
            delay: 0  //0则不延迟  
        });

        // 第2步：这个动画实例赋给当前的动画实例  
        this.animation = animation;

        // 第3步：执行第一组动画  
        animation.opacity(0).rotateX(-100).step();

        // 第4步：导出动画对象赋给数据对象储存  
        this.setData({
            animationData: animation.export()
        })

        // 第5步：设置定时器到指定时候后，执行第二组动画  
        setTimeout(function () {
            // 执行第二组动画  
            animation.opacity(1).rotateX(0).step();
            // 给数据对象储存的第一组动画，更替为执行完第二组动画的动画对象  
            this.setData({
                animationData: animation
            })

            //关闭  
            if (currentStatu == "close") {
                this.setData(
                    {
                        showModalStatus: false
                    }
                );
            }
        }.bind(this), 200)

        // 显示  
        if (currentStatu == "open") {
            this.setData(
                {
                    showModalStatus: true
                }
            );
        }
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
    onLoad: function (options) {
        var that = this;
        this.setData({
            imgBannerUrls: [
                { bannerImgUrl: 'http://192.168.16.217:81/images/2017-09-17/image/165388f7-5d2a-44ce-b40f-680269c6be1f-9561.png', bannerClickUrl: 'http://123.com', name: '张三', phone: '123455' },
                { bannerImgUrl: 'http://192.168.16.217:81/images/2017-09-17/image/165388f7-5d2a-44ce-b40f-680269c6be1f-9561.png', bannerClickUrl: 'http://123.com', name: '张三', phone: '123455' },
                { bannerImgUrl: 'http://192.168.16.217:81/images/2017-09-17/image/165388f7-5d2a-44ce-b40f-680269c6be1f-9561.png', bannerClickUrl: 'http://123.com', name: '张三', phone: '123455' },
                { bannerImgUrl: 'http://192.168.16.217:81/images/2017-09-17/image/165388f7-5d2a-44ce-b40f-680269c6be1f-9561.png', bannerClickUrl: 'http://123.com', name: '张三', phone: '123455' }
            ]
        })
        //   计算切换高度 
        wx.getSystemInfo({
            success: function (res) {
                that.setData({
                    //计算相关宽度
                    sliderWidth: res.windowWidth / that.data.tabs.length,
                    sliderOffset: res.windowWidth / that.data.tabs.length * that.data.activeIndex,
                    contentHeight: 250
                    // res.windowHeight - res.windowWidth / 750 * 68//计算内容区高度，rpx -> px计算
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
    showValue:function(e){
        this.setData({
            showValue: e.currentTarget.dataset.name,
         
        });
        var currentStatu = e.currentTarget.dataset.statu;
        this.util(currentStatu)
  
    }
})