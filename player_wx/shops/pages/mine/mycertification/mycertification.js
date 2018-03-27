//index.js
//获取应用实例
var tcity = require("../../../utils/citys.js");

var app = getApp()
Page({
    data: {
        provinces: [],
        province: "",
        citys: [],
        city: "",
        countys: [],
        county: '',
        value: [0, 0, 0],
        values: [0, 0, 0],
        condition: false,
        showModalStatus: false,
        files: []
    },
    bindChange: function (e) {
        //console.log(e);
        var val = e.detail.value
        var t = this.data.values;
        var cityData = this.data.cityData;

        if (val[0] != t[0]) {
            console.log('province no ');
            const citys = [];
            const countys = [];

            for (let i = 0; i < cityData[val[0]].sub.length; i++) {
                citys.push(cityData[val[0]].sub[i].name)
            }
            for (let i = 0; i < cityData[val[0]].sub[0].sub.length; i++) {
                countys.push(cityData[val[0]].sub[0].sub[i].name)
            }

            this.setData({
                province: this.data.provinces[val[0]],
                city: cityData[val[0]].sub[0].name,
                citys: citys,
                county: cityData[val[0]].sub[0].sub[0].name,
                countys: countys,
                values: val,
                value: [val[0], 0, 0]
            })

            return;
        }
        if (val[1] != t[1]) {
            console.log('city no');
            const countys = [];

            for (let i = 0; i < cityData[val[0]].sub[val[1]].sub.length; i++) {
                countys.push(cityData[val[0]].sub[val[1]].sub[i].name)
            }

            this.setData({
                city: this.data.citys[val[1]],
                county: cityData[val[0]].sub[val[1]].sub[0].name,
                countys: countys,
                values: val,
                value: [val[0], val[1], 0]
            })
            return;
        }
        if (val[2] != t[2]) {
            console.log('county no');
            this.setData({
                county: this.data.countys[val[2]],
                values: val
            })
            return;
        }


    },
    open: function () {
        this.setData({
            condition: !this.data.condition
        })
    },
    onLoad: function () {
        console.log("onLoad");
        var that = this;

        tcity.init(that);

        var cityData = that.data.cityData;


        const provinces = [];
        const citys = [];
        const countys = [];

        for (let i = 0; i < cityData.length; i++) {
            provinces.push(cityData[i].name);
        }
        console.log('省份完成');
        for (let i = 0; i < cityData[0].sub.length; i++) {
            citys.push(cityData[0].sub[i].name)
        }
        console.log('city完成');
        for (let i = 0; i < cityData[0].sub[0].sub.length; i++) {
            countys.push(cityData[0].sub[0].sub[i].name)
        }

        that.setData({
            'provinces': provinces,
            'citys': citys,
            'countys': countys,
            'province': cityData[0].name,
            'city': cityData[0].sub[0].name,
            'county': cityData[0].sub[0].sub[0].name
        })
        console.log('初始化完成');


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
    chooseImage: function (e) {
        var that = this;
        wx.chooseImage({
            sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
            sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
            success: function (res) {
                // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
                that.setData({
                    files: that.data.files.concat(res.tempFilePaths)
                });
            }
        })
    },
    previewImage: function (e) {
        wx.previewImage({
            current: e.currentTarget.id, // 当前显示图片的http链接
            urls: this.data.files // 需要预览的图片http链接列表
        })
    }
})
