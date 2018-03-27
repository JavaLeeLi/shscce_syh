
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
            { text: '控制台',name:'home'}
            ],
        goods:[
            { text: '商品管理',name:'goods.manage'},
            { text: '分类管理',name:'goods.class'}
        ],
        shop:[
            { text: '店铺管理',name:'shop.manage'},
            { text: '增值服务管理',name:'shop.service'},
            { text: '营销中心',name:'shop.marketing'}
        ],
        deal:[
            { text: '订单管理',name:'deal.order'},
            { text: '仲裁管理',name:'deal.dispute'},
            { text: '支付方式',name:'deal.payment'},
            { text: '交易设置',name:'deal.settings'}
        ],
        customerService:[
            { text: '客服管理',name:'customerService.manage'}
        ],
        vip:[
            { text: '会员管理',name:'vip.manage'},
            { text: '会员标签',name:'vip.tag'},
            { text: '会员积分',name:'vip.integral'},
            { text: '积分规则',name:'vip.rule'}
        ],
        distribution:[
            { text: '商品分销管理',name:'distribution.goods'},
            { text: '商品分销统计',name:'distribution.goodsStats'},
            { text: '会员分销管理',name:'distribution.vip'},
            { text: '层级体系管理',name:'distribution.system'},
            { text: '佣金模板管理',name:'distribution.template'},
            { text: '分销佣金结算',name:'distribution.settlement'},
            { text: '会员分销统计',name:'distribution.vipStats'}
        ],
        finance:[
            { text: '财务概览',name:'finance.overview'},
            { text: '店铺财务管理',name:'finance.shop'},
            { text: '店铺保证金管理',name:'finance.deposit'},
            { text: '账户余额管理',name:'finance.balance'},
            { text: '提现管理',name:'finance.withdrawCash'},
            { text: '财务明细流水',name:'finance.detail'},
            { text: '第三方支付对账',name:'finance.accounts'}
        ],
        service:[
            { text: '鉴评介绍管理',name:'service.appraisal-introduction'},
            { text: '鉴评预约管理',name:'service.appraisal-appointment'},
            { text: '鉴评结果管理',name:'service.appraisal-result'},
            { text: '鉴评标签管理',name:'service.appraisal-tag'},
            { text: '邮币卡管家介绍管理',name:'service.manager-introduction'},
            { text: '邮币卡管家预约管理',name:'service.manager-appointment'}
        ],
        stats:[
            { text: '统计管理',name:'stats.manage'}
        ],
        system:[
            { text: '客户端首页管理',name:'system.homePage'},
            { text: '咨讯公告管理',name:'system.notice'},
            { text: '管理员管理',name:'system.manager'},
            { text: '系统设置',name:'system.settings'}
        ]
    })
;

