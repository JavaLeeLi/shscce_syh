var app = getApp()
Page({
    data: {
        movies: [
            { url: 'http://img04.tooopen.com/images/20130712/tooopen_17270713.jpg' }
        ],
        hotGoodList: [], //热卖商品
        showModalStatus: false,  //弹窗
        showModalJion: false,
        num: 1
    },
    onLoad: function () {
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
    },
    powerDrawer: function (e) {
        var currentStatu = e.currentTarget.dataset.statu;
        this.util(currentStatu)
    },
    powerDrawernavTo: function (e) {
        var currentStatu = e.currentTarget.dataset.statu;
        this.util(currentStatu)
        wx.navigateTo({
            url: '../../shoppingcart/order/order'
        })
    },
    powerDrawerJion: function (e) {
        var currentStatu = e.currentTarget.dataset.statu;
        this.util(currentStatu);
        if (currentStatu == 'open') {
            this.setData({
                showModalJion: true
            });
        }
        if (currentStatu == 'close') {
            this.setData({
                showModalJion: false
            })
        }
    },
    powerDrawerJionOpen: function (e) {
        var currentStatu = e.currentTarget.dataset.statu;
        this.util(currentStatu);
        if (currentStatu == 'close') {
            this.setData({
                showModalJion: false
            })
        }
        this.openToast();
    },
    openToast: function () {
        wx.showToast({
            title: '加入成功',
            icon: 'success',
            duration: 3000
        });
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


    // 增加数量
    addCount: function (e) {
        console.info(e);
        const index = e.currentTarget.dataset.index;
        let num = this.data.num;
        num = num + 1;
        this.setData({
            num: num
        });
    },
    // 减少数量
    minusCount: function (e) {
        console.info(e);
        var index = e.currentTarget.dataset.index;
        console.info(index);
        let num = this.data.num

        if (num <= 1) {
            return false;
        }
        num = num - 1;
        this.setData({
            num: num
        });
    }


}) 