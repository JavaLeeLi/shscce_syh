/**
 * Created by lily-pc on 2016/6/20.
 */
window.appModule.factory('homeAPI',factoryFn);

function factoryFn($resource) {
    var actions = {
        queryActivityList: {//活动推荐list
            url:    '/recommend/queryRecommend',
            method: 'post',
            // params: {
            //     _ajaxWrapOption: {noResubmit: true}
            // }
        },
    };
    return $resource('', {_ajaxWrapOption: {}},actions);
}


