
window.appModule
  .run(function ($cacheFactory,$rootScope,$log,localStorageService,RESOURCE,AUTH_EVENTS) {
    try{
      window.document.querySelector("body > .spinner").style.display = 'none';
    }catch(e){$log.error('隐藏loading失败 ' + JSON.stringify(e))}

    $rootScope.RESOURCE = RESOURCE;
    $rootScope.ENV = $rootScope.vs_env = window.vs_env;
    window.app_data = {};
    _.assign($rootScope,window.vs_env);

    //对路由错误做统一处理
    $rootScope.$on('$stateChangeError',routeErrorFn);
    $rootScope.$on('$stateNotFound',routeErrorFn);

    //绑定之后，$rootScope的vsGlobal上的变量都会自动存入localStorage中
    localStorageService.bind($rootScope,'vsGlobal',{});

    if($rootScope.vsGlobal && $rootScope.vsGlobal.currUser){
      ga('set', 'userId', $rootScope.vsGlobal.currUser.id);    //没懂这句的意思，暂时留着
    }

      /********监听用户登录状态********/
      $rootScope.$on(AUTH_EVENTS.loginSuccess,function(){
        console.log(AUTH_EVENTS.loginSuccess);
      });
      $rootScope.$on(AUTH_EVENTS.logoutSuccess,function(){
          console.log(AUTH_EVENTS.logoutSuccess);
      });
      $rootScope.$on(AUTH_EVENTS.sessionTimeout,function(){
          console.log(AUTH_EVENTS.sessionTimeout);
      });

    function routeErrorFn(e){
      //做一些日志处理
      $log.error('router error ');
    }
  });


