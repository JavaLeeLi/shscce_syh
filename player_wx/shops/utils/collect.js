function collect(){
    wx.showToast({
        title: '已关注',
        icon: 'success',
        duration: 500
    })
}
function isCollect(){
    wx.showToast({
        title: '已取消',
        icon: 'success',
        duration: 500
    })
}

//得到系统屏幕高度
function getHeight(){
    var heightInfo="";
    wx.getSystemInfo({
        success: function (res) {
            heightInfo=res.screenHeight
        }
    })
    return heightInfo;
}
module.exports = {
    collect: collect,
    isCollect: isCollect,
    getHeight: getHeight
}