
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
;

