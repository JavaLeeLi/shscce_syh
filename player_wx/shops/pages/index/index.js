//index.js
//获取应用实例
const app = getApp()
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
    }
];
Page({
    data: {
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
        clock: ''
    },
    onLoad: function () {
        var that = this;

        wx.getSystemInfo({
            success: function (res) {
                console.log(res.screenHeight)
            }
        })

        count_down(that);
        this.setData({
            imgBannerUrls: [
                { bannerImgUrl: '../image/imgError.jpg', bannerClickUrl: 'http://123.com', name: '张三', phone: '123455' },
                { bannerImgUrl: '../image/imgError.jpg', bannerClickUrl: 'http://123.com', name: '张三', phone: '123455' },
                { bannerImgUrl: '../image/imgError.jpg', bannerClickUrl: 'http://123.com', name: '张三', phone: '123455' },
                {bannerImgUrl: '../image/imgError.jpg',  bannerClickUrl: 'http://123.com', name: '张三', phone: '123455' }
            ]
        })
        this.setData({
            imgHomeLists: [{
                naviItemUrl: "../image/imgError.jpg", naviItemName: "导航二", money:20
            },
            {
                naviItemUrl: "../image/imgError.jpg",
                naviItemName: "导航二", money: 20
            },
            {
                naviItemUrl: "../image/imgError.jpg",
                naviItemName: "导航二", money: 20
            },
            {
                naviItemUrl: "../image/imgError.jpg",
                naviItemName: "导航二", money: 20
            },
            {
                naviItemUrl: "../image/imgError.jpg",
                naviItemName: "导航3", money: 20
            },
            {
                naviItemUrl: "../image/imgError.jpg",
                naviItemName: "导航3", money: 20
            },
            {
                naviItemUrl: "../image/imgError.jpg",
                naviItemName: "导航3", money: 20
            },
            {
                naviItemUrl: "../image/imgError.jpg",
                naviItemName: "导航3", money: 20
            }
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

        })
        //   计算切换高度 
        wx.getSystemInfo({
            success: function (res) {
                that.setData({
                    //计算相关宽度
                    sliderWidth: res.windowWidth / that.data.tabs.length,
                    sliderOffset: res.windowWidth / that.data.tabs.length * that.data.activeIndex,
                    contentHeight: res.windowHeight//后续设置 
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
    mailJump: function (e) {
        wx.navigateTo({
            url: '../../../notice/notice',
        })
    },

})
var total_micro_second = 36 * 60 * 60 * 1000;

/* 毫秒级倒计时 */
function count_down(that) {
    // 渲染倒计时时钟
    that.setData({
        clock: date_format(total_micro_second)
    });

    if (total_micro_second <= 0) {
        that.setData({
            clock: "已截止"
        });
        // timeout则跳出递归
        return;
    }
    setTimeout(function () {
        // 放在最后--
        total_micro_second -= 10;
        count_down(that);
    }, 10)
}

// 时间格式化输出，如03:25:19 86。每10ms都会调用一次
function date_format(micro_second) {
    // 总秒数
    var second = Math.floor(micro_second / 1000);
    // 天数
    var day = Math.floor(second / 3600 / 24);
    // 小时
    var hr = Math.floor(second / 3600 % 24);
    // 分钟
    var min = Math.floor(second / 60 % 60);
    // 秒
    var sec = Math.floor(second % 60);
    return day + "天" + hr + "小时" + min + "分钟" + sec + "秒";
}

// 位数不足补零
function fill_zero_prefix(num) {
    return num < 10 ? "0" + num : num
}
