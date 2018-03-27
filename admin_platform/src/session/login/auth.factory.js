window.appModule.factory('authFactory',authFactory);
function authFactory (userSession,$rootScope) {
    var authFactory = {};

    //验证是否有登录
    authFactory.isAuthenticated = function () {
        return !!userSession.user_id;
    };
    //验证是否有权限
    authFactory.isAuthorized = function (authorizedPrivileges) {
        var isAuthPrivileges = false;

        if($rootScope.vsGlobal){
            if($rootScope.vsGlobal.currUser){
                var priviList = $rootScope.vsGlobal.currUser.privileges;
                if(priviList){
                    for(var i = 0;i<priviList.length;i++){
                        if(authorizedPrivileges == priviList[i]){
                            isAuthPrivileges = true;
                            break;
                        }
                    }
                }
            }

        }

        //return (authFactory.isAuthenticated() && isAuthPrivileges);
        return isAuthPrivileges;
    };
    return authFactory;
}