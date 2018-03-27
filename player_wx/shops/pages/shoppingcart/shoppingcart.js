Page({
    data: {
        showTopTips: false,
        radioItems: [
            { name: 'cell standard', value: '0' },
            { name: 'cell standard', value: '1', checked: true }
        ],
        checkboxItems: [
            { name: '丁萌', value: '1', selected: false }
        ],
        isAgree: false,
        count: 0,
        number: 0,
        total: '0.00',
        selectedAllStatus: false,
        selectedit: false,
        save: false,
        infoheight:0,
        minusStatuses: ['disabled', 'disabled', 'normal', 'normal', 'disabled'],
        carts: [
            {
                name: '丁萌',
                listitem: [
                    { title: '自营商城', image: '../image/imgError.jpg', num: '1', price: '198.0', selected: false },
                    { title: '自营商城', image: '../image/imgError.jpg', num: '1', price: '100.0', selected: false },
                ]
            },
            {
                name: '丁萌',
                listitem: [
                    { title: '自营商城', image: '../image/imgError.jpg', num: '1', price: '198.0', selected: false },
                    { title: '自营商城', image: '../image/imgError.jpg', num: '1', price: '100.0', selected: false },
                ]
            },
            {
                name: '丁萌',
                listitem: [
                    { title: '自营商城', image: '../image/imgError.jpg', num: '1', price: '198.0', selected: false },
                    { title: '自营商城', image: '../image/imgError.jpg', num: '1', price: '100.0', selected: false },
                ]
            }
        ]
    },
    onLoad: function (e) {
        var that=this;
        this.init();
        var that = this;
        wx.getSystemInfo({
            success: function (res) {
                that.setData({
                    infoheight: res.screenHeight
                })
            }
        })
    },
    init: function (e) {
        for (var i = 0; i < this.data.carts.length; i++) {
            this.data.carts[i].checked = false;
            console.log(this.data.carts[i])
            for (var j = 0; j < this.data.carts[i].listitem.length; j++) {
                this.data.carts[i].listitem[j].checked = false;
            }
        }
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

    //购物车减
    bindMinus: function (e) {
        var ids = parseInt(e.currentTarget.dataset.id);//商品id
        var shopId = parseInt(e.currentTarget.dataset.shopid);//店铺id
        var value = parseInt(e.currentTarget.dataset.value);//店铺id
        // 如果只有1件了，就不允许再减了
        if (value > 1) {
            value--;
        }
        // 只有大于一件的时候，才能normal状态，否则disable状态
        var minusStatus = value <= 1 ? 'disabled' : 'normal';
        // 购物车数据
        var carts = this.data.carts;
        for (var i = 0; i < carts.length; i++) {
            var index = carts[i];
            if (i == shopId) {
                for (var j = 0; j < carts[i].listitem.length; j++) {
                    if (j == ids) {
                        carts[i].listitem[j].num = value;
                        break;
                    }
                }
                break;
            }
        }
        // 按钮可用状态
        var minusStatuses = this.data.minusStatuses;
        minusStatuses[index] = minusStatus;
        // 将数值与状态写回
        this.setData({
            carts: carts,
            minusStatuses: minusStatuses
        });
        this.sum();
    },
    //购物车相加
    bindPlus: function (e) {
        console.log(e)
        var ids = parseInt(e.currentTarget.dataset.id);//商品id
        var shopId = parseInt(e.currentTarget.dataset.shopid);//店铺id
        var value = parseInt(e.currentTarget.dataset.value);//店铺id
        var checked = parseInt(e.currentTarget.dataset.checked);//店铺id

        // console.log(this.data.carts[shopId].listitem[ids]);
        // var num = this.data.carts[shopId].listitem[ids].num;
        // 自增
        value++;
        // 只有大于一件的时候，才能normal状态，否则disable状态
        var minusStatus = value <= 1 ? 'disabled' : 'normal';
        // 购物车数据
        var carts = this.data.carts;
        for (var i = 0; i < carts.length; i++) {
            var index = carts[i];
            if (i == shopId) {
                for (var j = 0; j < carts[i].listitem.length; j++) {
                    if (j == ids) {
                        carts[i].listitem[j].num = value;
                        break;
                    }
                }
                break;
            }
        }

        // 按钮可用状态
        var minusStatuses = this.data.minusStatuses;
        minusStatuses[ids] = minusStatus;
        // 将数值与状态写回
        this.setData({
            carts: carts,
            minusStatuses: minusStatuses
        });
        this.sum();
    },

    bindSelectAll: function (e) {
        // 环境中目前已选状态
        var selectedAllStatus = this.data.selectedAllStatus;
        // 取反操作
        selectedAllStatus = !selectedAllStatus;
        console.log(e);


        var nameshop = e.currentTarget.dataset.nameshop;
        //判断店铺全选
        if (nameshop == 'checkUserList') {
            var index = parseInt(e.currentTarget.dataset.index);
            var ids = parseInt(e.currentTarget.dataset.id);//店铺
            var checked = this.data.carts[ids].checked;
            var carts = this.data.carts;
            for (var i = 0; i < carts.length; i++) {
                if (i == ids) {
                    carts[i].checked = !checked;
                    for (var j = 0; j < carts[i].listitem.length; j++) {
                        carts[i].listitem[j].checked = !checked;;
                    }
                    this.setData({
                        carts: carts
                    });
                    this.sum();
                }
            }
        }
        //判断全选
        var selectAll = e.currentTarget.dataset.name;
        if (selectAll == 'selectAll') {
            var carts = this.data.carts;
            for (var i = 0; i < carts.length; i++) {
                carts[i].checked = selectedAllStatus;
                for (var j = 0; j < carts[i].listitem.length; j++) {
                    carts[i].listitem[j].checked = selectedAllStatus;
                }
            }
            this.setData({
                selectedAllStatus: selectedAllStatus,
                carts: carts
            });
            this.sum();
        }
    }
    , sum: function () {
        var carts = this.data.carts;
        // 计算总金额
        var total = 0;
        for (var i = 0; i < carts.length; i++) {
            if (carts[i].checked) {
                for (var j = 0; j < carts[i].listitem.length; j++) {
                    if (carts[i].listitem[j].checked) {
                        total += carts[i].listitem[j].num * carts[i].listitem[j].price;
                    }
                }
            } else {

                for (var j = 0; j < carts[i].listitem.length; j++) {
                    if (carts[i].listitem[j].checked) {
                        total += carts[i].listitem[j].num * carts[i].listitem[j].price;
                    }
                }

            }
        }
        // 写回经点击修改后的数组
        this.setData({
            carts: carts,
            total: '￥' + total
        });
    },
    bindCheckbox: function (e) {
        /*绑定点击事件，将checkbox样式改变为选中与非选中*/
        //拿到下标值，以在carts作遍历指示用
        var index = parseInt(e.currentTarget.dataset.index);
        var ids = parseInt(e.currentTarget.dataset.id);//店铺
        var checked = e.currentTarget.dataset.checked;//状态
        //原始的icon状态
        var selected = this.data.carts[ids].listitem[index].checked;
        var carts = this.data.carts;
        // 对勾选状态取反
        carts[ids].listitem[index].checked = !selected;
        var sums = 0;
        for (var i = 0; i < carts[ids].listitem.length; i++) {
            if (carts[ids].listitem[i].checked == true) {
                sums++;
                if (sums == carts[ids].listitem.length) {
                    carts[ids].checked = true;
                } else {
                    carts[ids].checked = false;
                }
            }
        }
        // 写回经点击修改后的数组
        this.setData({
            carts: carts
        });
        this.sum();
    },
    checkUserList: function (e) {
        this.bindSelectAll(e);
    },
    shopEdit: function (e) {
        this.setData({
            save: !this.data.save,
            selectedit: !this.data.selectedit
        })
    },

    //判断点击某个是否全选

    isAllcheck: function () {
        var isAll = 0;
        for (var i = 0; i < this.data.carts.length; i++) {
            if (this.data.carts[i].checked == false) {
                isAll++;
                break;
            }

            for (var j = 0; j < this.data.carts[i].listitem.length; j++) {
                if (this.data.carts[i].listitem[j].checked == false) {
                    isAll++;
                    break;
                }
            }
        }
        if (isAll > 0) {
            selectedAllStatus = false;
        } else {
            selectedAllStatus = true;
        }
    },

    //结算
    saveClearing: function (e) {
        var carts = this.data.carts;
        var schech = 0;
        for (var i = 0; i < carts.length;i++) {
            for (var j = 0; j < carts[i].listitem.length; j++) {
                if (carts[i].listitem[j].checked == true) {
                    schech++;
                    break;
                } else {
                    schech = 0;
                }
            }
            if (schech>0){
                break;
            }
        }
        if (schech==0){
            wx.showModal({
                title: '提示',
                content: '请选择要结算的商品',
                showCancel:false,
                success: function (res) {
                    if (res.confirm) {
                        console.log('用户点击确定')
                    } 
                }
            })
        }else{
        wx.navigateTo({
            url: '../shoppingcart/order/order',
        })
        }

    },
    //清空
    emptying: function (e) {
        wx.showModal({
            title: '删除提示',
            content: '确定清空购物车',
            cancelColor: "#0894ec",
            confirmColor: "#0894ec",
            success: function (res) {
                if (res.confirm) {
                    console.log('用户点击确定')
                } else if (res.cancel) {
                    console.log('用户点击取消')
                }
            }
        })
    }
});