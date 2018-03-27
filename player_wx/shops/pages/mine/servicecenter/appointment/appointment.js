var tabs = [
    {
        name: "鉴评"
    },
    {
        name: "管家"
    },
    {
        name: "仓储"
    }
];
Page({
    data: {
        showTopTips: false,
        date: '2016-09-01',
        radioItems: [
            { name: 'cell standard', value: '0' },
            { name: 'cell standard', value: '1', checked: true }
        ],
        checkboxItems: [
            { name: 'standard is dealt for u.', value: '0', checked: true },
            { name: 'standard is dealicient for u.', value: '1' }
        ],
        address: "北京市 东城区   详细地址",
        date: "2016-09-01",
        time: "12:01",

        countryCodes: ["+86", "+80", "+84", "+87"],
        countryCodeIndex: 0,

        countries: ["快递", "自提"],
        countryIndex: 0,

        accounts: ["快递", "自提"],
        accountIndex: 0,

        isAgree: false,
        showModalStatus: false,
        tabs: tabs,
        slideOffset: 0,//指示器每次移动的距离
        activeIndex: 0,//当前展示的Tab项索引
        sliderWidth: 96,//指示器的宽度,计算得到
        contentHeight: 0,//页面除去头部Tabbar后，内容区的总高度，计算得到
        showValue: ''
    },
    showTopTips: function () {
        var that = this;
        this.setData({
            showTopTips: true
        });
        setTimeout(function () {
            that.setData({
                showTopTips: false
            });
        }, 3000);
    },
    radioChange: function (e) {
        console.log('radio发生change事件，携带value值为：', e.detail.value);

        var radioItems = this.data.radioItems;
        for (var i = 0, len = radioItems.length; i < len; ++i) {
            radioItems[i].checked = radioItems[i].value == e.detail.value;
        }

        this.setData({
            radioItems: radioItems
        });
    },
    checkboxChange: function (e) {
        console.log('checkbox发生change事件，携带value值为：', e.detail.value);

        var checkboxItems = this.data.checkboxItems, values = e.detail.value;
        for (var i = 0, lenI = checkboxItems.length; i < lenI; ++i) {
            checkboxItems[i].checked = false;

            for (var j = 0, lenJ = values.length; j < lenJ; ++j) {
                if (checkboxItems[i].value == values[j]) {
                    checkboxItems[i].checked = true;
                    break;
                }
            }
        }

        this.setData({
            checkboxItems: checkboxItems
        });
    },
    bindDateChange: function (e) {
        this.setData({
            date: e.detail.value
        })
    },
    bindTimeChange: function (e) {
        this.setData({
            time: e.detail.value
        })
    },
    bindCountryCodeChange: function (e) {
        console.log('picker country code 发生选择改变，携带值为', e.detail.value);

        this.setData({
            countryCodeIndex: e.detail.value
        })
    },
    bindCountryChange: function (e) {
        console.log('picker country 发生选择改变，携带值为', e.detail.value);

        this.setData({
            countryIndex: e.detail.value
        })
    },
    bindAccountChange: function (e) {
        console.log('picker account 发生选择改变，携带值为', e.detail.value);

        this.setData({
            accountIndex: e.detail.value
        })
    },
    bindAgreeChange: function (e) {
        this.setData({
            isAgree: !!e.detail.value.length
        });
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
                    contentHeight: 500
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
    showValue: function (e) {
        this.setData({
            showValue: e.currentTarget.dataset.name,

        });
        var currentStatu = e.currentTarget.dataset.statu;
        this.util(currentStatu)

    }
});