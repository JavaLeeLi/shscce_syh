window.appModule.factory('keyMap', serviceFn);

function serviceFn($http) {
  var propertyReg = /(\w+)(:?)(.?)/;
  var map = {};
  var loadMapUrl = 'dict/findByType/';

  return {
    getMap: getMapFn,
    convert: convertFn
  };

  /************************************************************************************************************************/


  function getMapFn(mapName) {
    var result = [];
    if(!map[mapName]){
      $http.get(window.vs_env.APIURL + loadMapUrl + mapName).success(function (response) {
        map[mapName] = response.data;

        map[mapName].each(function (item) {
          result.push(item);
        });
      });
      return result;
    }else{
      return map[mapName];
    }
  }

  function convertFn(mapName, source, srcProperty, destProperty) {
    if(!map[mapName]){
      $http.get(window.vs_env.APIURL + loadMapUrl + mapName).success(function (response) {
        map[mapName] = response.data;
        _convertFn(mapName, source, srcProperty, destProperty);
      });
    }else{
      _convertFn(mapName, source, srcProperty, destProperty);
    }
  }

  function _convertFn(mapName, source, srcProperty, destProperty){
    var property = propertyReg.exec(srcProperty);
    srcProperty = property[1];
    var splitChar = property[3] ? property[3] : ',';
    destProperty = destProperty || srcProperty;
    property = propertyReg.exec(destProperty);
    destProperty = property[1];
    var joinChar = property[3] ? property[3] : ',';

    if (_.isArray(source)) {
      _.each(source, function (val) {
        fill(val);
      });
    } else {
      fill(source);
    }

    function fill(obj) {
      var propertyArr = obj[srcProperty].split(splitChar);
      var identity = {};

      if (propertyArr.length === 0) {
        identity.key = propertyArr[0].trim();
        var val = _.find(map[mapName], identity);
        obj[destProperty] = val ? val.value : identity.key;
      } else {
        var valueArr = [];
        _.each(propertyArr, function (val) {
          identity.key = val.trim();
          val = _.find(map[mapName], identity);
          valueArr.push(val ? val.value : identity.key);
        });
        obj[destProperty] = valueArr.join(joinChar || splitChar);
      }
    }

  }

}
