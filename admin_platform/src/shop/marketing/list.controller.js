window.appModule.controller('ShopMarketingController',controllerFn);

function controllerFn() {
    var vm = this;

    vm.tools = [
        {logo:'券',title:'优惠券',content:'向客户发送商家优惠券'},
        {logo:'满',title:'满减／满送',content:'设置订单金额享有优惠或赠品'},
        {logo:'限',title:'限时折扣',content:'设置商品限时折扣'},
        {logo:'套',title:'套餐搭配',content:'将多种商品组合成套餐销售'},
        {logo:'团',title:'团购',content:'由客户邀请朋友组团购买'}
    ]
}