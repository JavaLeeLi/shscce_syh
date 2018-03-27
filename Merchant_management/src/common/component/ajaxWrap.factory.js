window.appModule.factory('ajaxWrap',factoryFn);

function factoryFn($q,$injector,$cacheFactory){
  var defaultOption = {
    timeout: 70000,
    resubmitInterval: 3000
  };

  //分页数据缓存
  var pageCache = $cacheFactory('pageCache');
  var pageCacheMock = $cacheFactory('pageCacheMock');
  var resubmitCache = {};

  return {
    request: closure(request),
    response : closure(response),
    responseError : closure(response,true)
  }

  /************************************************************/

  function closure(fn,error){
    return function(value){
      return fn(value,error);
    }
  }

  function request(config){
    if(config.method == 'GET' && !config.cache){
      var timeStr = 'time_' + Date.now();
      (config.params || (config.params = {})).t = timeStr;

      config.headers['If-Modified-Since'] = 0;
      config.headers['Cache-Control'] = 'no-cache';
    }

    var ajaxWrapOption = requestAjaxWrap(config);
    if(ajaxWrapOption === null){
      return config;
    }

    if(ajaxWrapOption.noResubmit){
      var nowTime = Date.now();
      var lastTime = resubmitCache[config.url];
      var interval = _.isNumber(ajaxWrapOption.noResubmit) ? ajaxWrapOption.noResubmit : defaultOption.resubmitInterval;

      if(!lastTime){
        lastTime = resubmitCache[config.url] = nowTime - interval - 1000;
      }

      resubmitCache[config.url] = nowTime;
      if(nowTime - lastTime <= interval){
        toastr.warning('您的数据提交请求过于频繁');
        throw new Error('重复提交数据');
      }

    }

    config.url = window.vs_env.APIURL + config.url;

    var timeout = ajaxWrapOption.timeout;
    //如果不等于true则说明没有禁用请求延迟提醒功能
    if(timeout !== false){
      if(angular.isUndefined(timeout)){
        config.timeout = defaultOption.timeout;
      }else{
        config.timeout = timeout;
      }
    }

    //对分页缓存进行处理
    if(ajaxWrapOption.mockPage === true && config.data && config.data.pageInfo){
      var pageData = pageCache.get(config.url);
      var pageInfo = config.data.pageInfo;
      ajaxWrapOption._pageInfo = config.data.pageInfo;
      delete config.data.pageInfo;

      if(pageData){
        var pageStart = (pageInfo.pageNumber - 1) * pageInfo.pageSize;
        var pageEnd = pageStart + pageInfo.pageSize;
        var searchData = searchForPage(pageData,config.data);
        var data = [200,
          {
            totalElements: searchData.length,
            totalPages: Math.ceil(searchData.length / pageInfo.pageSize),
            content: searchData.slice(pageStart,pageEnd)
          }
        ];

        config.method = 'GET';
        pageCacheMock.put(config.url,data);
        config.cache = pageCacheMock;
      }

      ajaxWrapOption._search = config.data;
      config.data = {};
    }

    return config;
  }

  function response(value,error){
    if(!value.config || !_.isObject(value.config._ajaxWrapOption)){
      return error ? $q.reject(value) : value;
    }

    var ajaxWrapOption = value.config._ajaxWrapOption;

    //会话失效
    //if(value.headers('sessionInvalid') || (value.data && value.data.code == '00001')){
    //  if(ajaxWrapOption.alertError !== false){
    //    toastr.warning('会话失效');
    //  }
    //  // $injector.get('$state').go('login');
    //  return $q.reject(value);
    //}

    //全局请求成功，但结果错误处理
    if(value.data && value.data.code && value.data.code != 10000){
      if(value.data.msg){
        toastr.error(value.data.msg);
      }
      value.data._error = true;
      return $q.reject(value);
    }

    //全局请求错误处理
    if(error){
      if(value.status == 0){
        //请求超时
      }else if(value.status >= 400 && value.status < 500){
        //参数错误
      }else if(value.status >= 500 && value.status < 600){
        //服务器错误
      }
      //除非手动禁用，否则所有请求报错都会弹出提示
      if(ajaxWrapOption.alertError !== false){
        toastr.warning('服务器繁忙');
      }
      return $q.reject(value);
    }

    //处理分页缓存
    if(ajaxWrapOption.mockPage === true && ajaxWrapOption._pageInfo && !value.config.cache){
      var pageInfo = ajaxWrapOption._pageInfo;
      pageCache.put(value.config.url,value.data);
      var pageStart = (pageInfo.pageNumber - 1) * pageInfo.pageSize;
      var pageEnd = pageStart + pageInfo.pageSize;
      var searchData = searchForPage(value.data,ajaxWrapOption._search);
      value.data = {
        totalElements: searchData.length,
        totalPages: Math.ceil(searchData.length / pageInfo.pageSize),
        content: searchData.slice(pageStart,pageEnd)
      };
    }

    return value;
  }


  function requestAjaxWrap(config){
    if(config.params && _.isObject(config.params._ajaxWrapOption)) {

      //获取配置信息
      var ajaxWrapOption = config.params._ajaxWrapOption;
      delete config.params._ajaxWrapOption;

      //将配置信息放到新的位置上
      config._ajaxWrapOption = ajaxWrapOption;

      return ajaxWrapOption;
    }

    return null;
  }

  function searchForPage(arr,search){
    for(var s in search){
      if(!search[s] || search[s] == '|') continue;

      var strict = search[s].indexOf('|') == 0;
      search[s] = search[s].replace(/^\|/,'');

      arr = arr.filter(function (item) {
        return !!item[s] && (item[s] == search[s] || (!strict && item[s].indexOf(search[s]) != -1));
      });

      if(arr.length == 0) return [];
    }
    return arr;
  }
}


