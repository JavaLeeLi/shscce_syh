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
        inputShowed: false,
        inputVal: "",
        imgBannerUrls: [],//首页banner数据
         hotGoodList: [], //热卖商品
        indicatorDots: true,
        autoplay: true,
        interval: 5000,
        duration: 1000,
        tabs: tabs,
        slideOffset: 0,//指示器每次移动的距离
        activeIndex: 0,//当前展示的Tab项索引
        sliderWidth: 96,//指示器的宽度,计算得到
        contentHeight: 0,//页面除去头部Tabbar后，内容区的总高度，计算得到
        item: [
            {
                pic: '../image/imgError.jpg',
                title: '玻璃棧道',
                desc: '22W人去過'
            }, {
                pic: '../image/imgError.jpg',
                title: '玻璃棧道',
                desc: '22W人去過'
            }, {
                pic: '../image/imgError.jpg',
                title: '玻璃棧道',
                desc: '22W人去過'
            }
        ]
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
                { bannerImgUrl: '../image/imgError.jpg', bannerClickUrl: 'http://123.com', name: '张三', phone: '123455' },
                { bannerImgUrl: '../image/imgError.jpg', bannerClickUrl: 'http://123.com', name: '张三', phone: '123455' },
                { bannerImgUrl: '../image/imgError.jpg', bannerClickUrl: 'http://123.com', name: '张三', phone: '123455' },
                { bannerImgUrl: '../image/imgError.jpg', bannerClickUrl: 'http://123.com', name: '张三', phone: '123455' },
                { bannerImgUrl: '../image/imgError.jpg', bannerClickUrl: 'http://123.com', name: '张三', phone: '123455' }
            ]
        })

            this.setData({
                hotGoodList: [
                    {
                        naviItemUrl: 'http://192.168.16.217:81/images/2017-09-14/image/93d99068-f346-483f-8c5a-c6a1e1a38115-9924.jpg',
                        goodsName: "儿童卫衣",
                        tou: 'http://192.168.16.217:81/images/2017-09-14/image/83568d24-69b0-4e57-ad0b-c4430d40ab89-4343-min.jpg',
                        paice: '3.98',
                        jibie: 'sad3',
                        dengji: 'Lv2',
                        renzhegn: '未认证'
                    },
                    {
                        naviItemUrl: 'http://192.168.16.217:81/images/2017-09-14/image/93d99068-f346-483f-8c5a-c6a1e1a38115-9924.jpg',
                        goodsName: "儿童卫衣",
                        tou: 'http://192.168.16.217:81/images/2017-09-14/image/83568d24-69b0-4e57-ad0b-c4430d40ab89-4343-min.jpg',
                        paice: '3.98',
                        jibie: 'sad3',
                        dengji: 'Lv2',
                        renzhegn: '未认证'
                    },
                    {
                        naviItemUrl: 'http://192.168.16.217:81/images/2017-09-14/image/93d99068-f346-483f-8c5a-c6a1e1a38115-9924.jpg',
                        goodsName: "儿童卫衣",
                        tou: 'http://192.168.16.217:81/images/2017-09-14/image/83568d24-69b0-4e57-ad0b-c4430d40ab89-4343-min.jpg',
                        paice: '3.98',
                        jibie: 'sad3',
                        dengji: 'Lv2',
                        renzhegn: '未认证'
                    },
                    {
                        naviItemUrl: 'http://192.168.16.217:81/images/2017-09-14/image/93d99068-f346-483f-8c5a-c6a1e1a38115-9924.jpg',
                        goodsName: "儿童卫衣",
                        tou: 'http://192.168.16.217:81/images/2017-09-14/image/83568d24-69b0-4e57-ad0b-c4430d40ab89-4343-min.jpg',
                        paice: '3.98',
                        jibie: 'sad3',
                        dengji: 'Lv2',
                        renzhegn: '未认证'
                    }
                ]

            }),
        //   计算切换高度 
        wx.getSystemInfo({
            success: function (res) {
                that.setData({
                    //计算相关宽度
                    sliderWidth: res.windowWidth / that.data.tabs.length,
                    sliderOffset: res.windowWidth / that.data.tabs.length * that.data.activeIndex,
                    contentHeight:250
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
    }
})