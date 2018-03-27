/**
 * Created by liuss on 16/3/14.
 */
window.appModule.factory('textAngular',factoryFn);

function factoryFn(taRegisterTool,taOptions,Upload,taToolFunctions){
  return {
    init: initFn
  }

  function initFn(){
    taRegisterTool('HList',{
      display: '<div uib-dropdown>'+
      '<i uib-dropdown-toggle class="fa fa-header"></i>'+
      '<ul uib-dropdown-menu>'+
      '<li ng-click="h1()">H1</li>'+
      '<li ng-click="h2()">H2</li>'+
      '<li ng-click="h3()">H3</li>'+
      '<li ng-click="h4()">H4</li>'+
      '<li ng-click="h5()">H5</li>'+
      '<li ng-click="h6()">H6</li>'+
      '</ul>'+
      '</div>',
      action: function () {
        var self = this;
        if(self.h1) return;

        ['h1','h2','h3','h4','h5','h6'].forEach(function (key) {
          self[key] = (function (tag) {
            return function () {
              self.$editor().wrapSelection("formatBlock", "<" + tag.toUpperCase() +">");
            }
          }(key));
        });
      }
    });

    taRegisterTool('uploadImage',{
      display: '<div ngf-select="uploadFile($file)" ng-model="file" ngf-accept="\'image/*\'" ngf-max-size="20MB" ><i class="fa fa-image"></i></div>',
      action: function (deferred,restoreSelection) {
        var self = this;
        self.uploadImageCallBack = function (url) {
          restoreSelection();
          self.$editor().wrapSelection('insertImage', url, true);
        }
      },
      onElementSelect: {
        element: 'img',
        action: taToolFunctions.imgOnSelectAction
      },
      uploadFile: function (file) {
        if(!file) return;

        var self = this;

        Upload.upload({
          url: window.vs_env.APIURL + '/upload',
          method: 'POST',
          file: file
        }).progress(function (evt) {
          // set upload percentage
          file.progress = parseInt(100.0 * evt.loaded / evt.total);
        }).success(function (data, status, headers, config) {
          // file is uploaded successfully
          file.complete = true;
          self.uploadImageCallBack(data[0].domain + data[0]['img']);
        }).error(function (data, status, headers, config) {
          // file failed to upload
          file.error = true;
          console.log(data);
          console.log(status);
        });
      }
    });

    taRegisterTool('uploadVideo',{
      display: '<div ngf-select="uploadFile($file)" ng-model="file" ngf-max-size="20MB" ngf-accept="\'image/*\'"><i class="fa fa-youtube-play"></i></div>',
      action: function (deferred,restoreSelection) {
        var self = this;
        self.uploadVideoCallBack = function (url) {
          var embed = '<img class="ta-insert-video" src="assets/img/gift.png" ta-insert-video="' + url + '" contenteditable="false" allowfullscreen="true" frameborder="0" />';
          restoreSelection();
          self.$editor().wrapSelection('insertHTML', embed, true);
        }
      },
      onElementSelect: {
        element: 'img',
        action: taToolFunctions.imgOnSelectAction
      },
      uploadFile: function (file) {
        if(!file) return;

        var self = this;

        Upload.upload({
          url: window.vs_env.APIURL + '/upload',
          method: 'POST',
          file: file
        }).progress(function (evt) {
          // set upload percentage
          file.progress = parseInt(100.0 * evt.loaded / evt.total);
        }).success(function (data, status, headers, config) {
          // file is uploaded successfully
          file.complete = true;
          self.uploadVideoCallBack(data[0].domain + data[0].filePath);
        }).error(function (data, status, headers, config) {
          // file failed to upload
          file.error = true;
          console.log(data);
          console.log(status);
        });
      }
    });

    taOptions.toolbar = [
      ['HList','bold', 'italics', 'underline', 'ul', 'ol', 'justifyLeft','justifyCenter','justifyRight', 'justifyFull','quote','insertLink','uploadImage']
    ];
  }
}
