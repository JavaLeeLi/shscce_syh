
window.appModule
  .run(function ($cacheFactory,$rootScope,$log,confirmationPopoverDefaults,textAngular,localStorageService,RESOURCE,AUTH_EVENTS) {
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

    //配置bootstrap-confirm
    //confirmationPopoverDefaults.confirmText = '确定';
    //confirmationPopoverDefaults.cancelText = '取消';
    //confirmationPopoverDefaults.confirmButtonType = 'danger';
    //confirmationPopoverDefaults.confirmButtonType = 'sm btn-danger';
    //confirmationPopoverDefaults.cancelButtonType = 'sm';
    //confirmationPopoverDefaults.templateUrl = 'common/component/bootstrap-confirm/bootstrap-confirm';

    //绑定之后，$rootScope的vsGlobal上的变量都会自动存入localStorage中
    localStorageService.bind($rootScope,'vsGlobal',{});

    if($rootScope.vsGlobal && $rootScope.vsGlobal.currUser){
      ga('set', 'userId', $rootScope.vsGlobal.currUser.id);    //没懂这句的意思，暂时留着
    }

      /********监听用户登录状态********/
      $rootScope.$on(AUTH_EVENTS.loginSuccess,function(){
        toastr.success(AUTH_EVENTS.loginSuccess);
      });
      $rootScope.$on(AUTH_EVENTS.logoutSuccess,function(){
        toastr.success(AUTH_EVENTS.logoutSuccess);
      });
      $rootScope.$on(AUTH_EVENTS.sessionTimeout,function(){
        toastr.info(AUTH_EVENTS.sessionTimeout);
      });

    //富文本默认配置
    textAngular.init();

    function routeErrorFn(e){
      //做一些日志处理
      $log.error('router error ');
    }
  });


