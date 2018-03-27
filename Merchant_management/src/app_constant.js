
window.appModule

    .constant('APPNAME', window.vs_env.APPNAME)

    .constant('RESOURCE', {
        articleThumb: 'assets/img/thumb.jpeg'
    })

    .constant('AUTH_EVENTS', {
        loginSuccess: '登录成功！',
        loginFailed: '登录失败！',
        logoutSuccess: '退出成功！',
        sessionTimeout: '登录超时，请重新登录！',
        notAuthenticated: '您没有登录！',
        notAuthorized: '您没有权限！'
    })
    .constant('USER_ROLES', {
        all:         '*',
        admin:       'admin',
        company:     'company',
        facilitator: 'facilitator'
    })

    .constant('SUB_MENUS',{
        home:[
            { text: 'DashBoard',name:'home'}
        ],
        commodity:[
            { text: '商品管理',name:'commodity'}
        ],
        transaction:[
            { text: '订单管理',name:'transaction.order'},
            { text: '退货处理',name:'transaction.return'},
             { text: '退款处理',name:'transaction.refunds'}
        ],
        logistics:[
            { text: '运费模板管理',name:'logistics'}
        ],
        marketing:[
            { text: '优惠券',name:'marketing.coupon'},
            { text: '限时折扣',name:'marketing.discount'},
            /*{ text: '退款处理',name:'transaction.refunds'}*/
        ]
    })
;

