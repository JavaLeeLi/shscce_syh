/**
 * Created by liuss on 16/1/27.
 */

window.appModule

  .config(function ($provide, $stateProvider, $urlRouterProvider, $httpProvider, $locationProvider,$compileProvider,localStorageServiceProvider) {
    //路由默认设置
    $urlRouterProvider.when('/', '/home');
    $urlRouterProvider.when('', '/home');
    $urlRouterProvider.otherwise('/404');
    $locationProvider.html5Mode(window.vs_env.HTML5MODE);

    //将ajaxWrap挂载到http拦截器中
    $httpProvider.interceptors.push('ajaxWrap');

    //过滤items－services
    $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|ftp|mailto|itms-services):/);

    //对框架的一些组件进行包装
    $provide.decorator('$exceptionHandler', extendExceptionHandler);
    $provide.decorator('$log', extendLogHandler);

    //配置localStorage
    localStorageServiceProvider.setPrefix('conference');
  });

function extendExceptionHandler($delegate) {
  return function (exception, cause) {
    $delegate(exception, cause);
    //做一些全局异常处理操作
//        console.error('error:' + exception.message, cause);
  };
}

function extendLogHandler($delegate) {

  return {
    log: $delegate.log, info: $delegate.info, warn: $delegate.warn, debug: $delegate.debug, error: errorFn
  }

  function errorFn() {
    $delegate.error.apply($delegate, arguments);
    ///做一些额外的事情
  }
}
