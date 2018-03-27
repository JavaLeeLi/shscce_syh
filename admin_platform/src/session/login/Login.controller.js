window.appModule.controller('LoginController',controllerFn);

function controllerFn ( $rootScope,sessionAPI,userSession,$state,AUTH_EVENTS){
    var vm = this;
    //vm.credentials = {
    //    username: '',
    //    password: ''
    //};
    vm.login = loginFn;



    /******************************************************************/
    //登录
    function loginFn(credentials){
        if(vm.user.login_name != undefined || vm.user.password != undefined){
            if(!vm.user.login_name){
                toastr.error('请输入账号！');
                return;
            }
            if(!vm.user.password){
                toastr.error('请输入密码！');
                return;
            }
        }
        sessionAPI.login(credentials,function(res){
            if(res.is_admin == true){
                //获取权限
                sessionAPI.queryPrivileges({id:res.user_id},function(result){
                    vm.priviDetails = result;
                    //用户存储用户信息
                    userSession.create(res.user_id, res.login_name,
                        result.roles,result.privileges);
                    $rootScope.$broadcast(AUTH_EVENTS.loginSuccess);
                    $rootScope.vsGlobal.currUser = {
                        user_id : res.user_id,
                        login_name : res.login_name,
                        person:res.person,
                        roles:result.roles,
                        privileges:result.privileges
                    }
                    $state.go('work.scope');
                });


            }else{
                toastr.info("此用户无管理员权限，请重新输入！");
            }
        })
    }

    ////获取权限
    //function loadData(item){
    //    sessionAPI.queryPrivileges({id:item.user_id},function(res){
    //        vm.priviDetails = res;
    //        console.log(vm.priviDetails);
    //    })
    //}

}