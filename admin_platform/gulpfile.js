var plgnins = require('gulp-load-plugins')();
var autoprefixer = require('autoprefixer');
var bs = require('browser-sync').create();
var proxy = require('proxy-middleware');
var urlParse = require('url').parse;
var yaml = require('js-yaml');
var gulp = require('gulp');
var util = require('util');
var fs = require('fs');

var argvMap = {
  '-m': 'min',
  '-r': 'rev',
  '-w': 'watch'
};

//处理未捕获的异常，输出错误详情
process.on('uncaughtException', function (event) {
  console.log('uncaughtException error:' + require('util').inspect(event));
});

var config = loadConfig();

//输出帮助提示
gulp.task('help',function(){
  console.log(config.help);
});

gulp.task('configSSH', function configSSH() {
    gulpSSH = new plgnins.ssh({
        ignoreErrors: false,
        sshConfig: {
            host: config.deploy.ssh_host,
            port: config.deploy.ssh_port,
            username: config.deploy.ssh_user,
            // password: config.deploy.ssh_pwd,
            privateKey:fs.readFileSync(config.deploy.privateKey)
        }
    });
});

var defaultTask = ['clean','app_css','lib_css','app_js','lib_js','template_js','html_rev','html_replace'];
if(config.watch) defaultTask.push('watch');

//默认开发环境构建任务
gulp.task('default',plgnins.sequence.apply(null,defaultTask));


//压缩目录
gulp.task('zip',function () {
  return gulp.src(config.zip)
    .pipe(plgnins.zip(config.target + '.zip'))
    .pipe(gulp.dest('.'));
});

//传输数据到远程服务器
gulp.task('scp',['zip'],function () {
  return gulp.src(config.target + '.zip')
    .pipe(gulpSSH.sftp('write',config.deploy.ssh_home + '/' + config.target + '.zip'));
});

//执行shell脚本
gulp.task('deploy',['configSSH', 'scp'],function () {
    return gulpSSH.shell(config.deploy.ssh_shell,{filePath: 'shell.log'})
        .pipe(gulp.dest('logs'));
});

//开发人员编写业务代码
gulp.task('app_js',function(){
  var globalWrap = config.prependJs + '\nwindow.vs_env = window.ENV = \n' + util.inspect(config.vs_env) + ';\n' +
    'window.appModule = angular.module("App", window.vs_env.MODULES);\n<%= contents %>\n' + config.appendJs;

  var stream = gulp.src(config.build.app_js)
    .pipe(plgnins.plumber())
    .pipe(plgnins.naturalSort())
    .pipe(plgnins.wrap('(function(angular,window,document){\n<%= contents %>\n})(angular,window,document);'))
    .pipe(plgnins.concat('app.js'))
    .pipe(plgnins.wrap(globalWrap))
    .pipe(plgnins.ngAnnotate())
    .pipe(plgnins.if(config.min,gulp.dest(config.target + '/assets/js-src')))
    .pipe(plgnins.if(config.min,plgnins.uglify()))
    .pipe(gulp.dest(config.target + '/assets/js'));

  if(config.rev){
    return rev(stream,'js');
  }

  if(bs.active){
    setTimeout(function () {
      bs.reload();
    },200);
  }
  return stream;
});

//项目依赖的第三方代码
gulp.task('lib_js',function(){
  var stream = gulp.src(config.build.lib_js)
    .pipe(plgnins.plumber())
//    .pipe(plgnins.naturalSort())
    .pipe(plgnins.concat('lib.js'))
    .pipe(plgnins.if(config.min,gulp.dest(config.target + '/assets/js-src')))
    .pipe(plgnins.if(config.min,plgnins.uglify()))
    .pipe(gulp.dest(config.target + '/assets/js'));

  if(config.rev){
    return rev(stream,'js');
  }

  if(bs.active){
    setTimeout(function () {
      bs.reload();
    },200);
  }
  return stream;
});

//开发人员编写根据页面相关的css
gulp.task('app_css',function(){
  var stream = gulp.src(config.build.app_css)
    .pipe(plgnins.plumber())
    .pipe(plgnins.naturalSort())
    .pipe(plgnins.sassBulkImport())
    .pipe(plgnins.sass({outputStyle: 'expanded'}).on('error', plgnins.sass.logError))
    .pipe(plgnins.postcss([autoprefixer({browsers: ['last 5 version','ie >= 9']})]))
    .pipe(plgnins.concat('app.css'))
    .pipe(plgnins.if(config.min,gulp.dest(config.target + '/assets/css-src')))
    .pipe(plgnins.if(config.min,plgnins.minifyCss({})))
    .pipe(gulp.dest(config.target + '/assets/css'))
    .pipe(plgnins.if(bs.active,bs.stream()));

  if(config.rev){
    return rev(stream,'css');
  }

  return stream;
});

//项目依赖的第三方样式库
gulp.task('lib_css',function(){
  var stream = gulp.src(config.build.lib_css)
    .pipe(plgnins.plumber())
//    .pipe(plgnins.naturalSort())
    .pipe(plgnins.sassBulkImport())
    .pipe(plgnins.sass({outputStyle: 'expanded'}).on('error', plgnins.sass.logError))
    .pipe(plgnins.postcss([autoprefixer({browsers: ['last 5 version','ie >= 9']})]))
    .pipe(plgnins.concat('lib.css'))
    .pipe(plgnins.if(config.min,gulp.dest(config.target + '/assets/css-src')))
    .pipe(plgnins.if(config.min,plgnins.minifyCss({})))
    .pipe(gulp.dest(config.target + '/assets/css'))
    .pipe(plgnins.if(bs.active,bs.stream()));

  if(config.rev){
    return rev(stream,'css');
  }

  return stream;
});

//包含所有的html代码
gulp.task('template_js',function(){
  var stream = gulp.src(config.build.template_js)
    .pipe(plgnins.plumber())
    .pipe(plgnins.naturalSort())
    .pipe(plgnins.if(config.min,gulp.dest(config.target + '/assets/js-src/template')))
    .pipe(plgnins.if(config.min,plgnins.htmlmin({collapseWhitespace: true})))
    .pipe(plgnins.angularTemplatecache('template.js',{
      standalone: true,
      transformUrl: function(url) {
        return url.replace(/\.html$/, '').replace(/(^\/|^\\)/,'');
      }
    }))
    .pipe(plgnins.concat('template.js'))
    .pipe(gulp.dest(config.target + '/assets/js'));

  if(config.rev){
    return rev(stream,'js');
  }

  if(bs.active){
    setTimeout(function () {
      bs.reload();
    },200);
  }
  return stream;
});

//清除之前构建生成的文件以及文件夹
gulp.task('clean',function(){
  return gulp.src(config.clean)
    .pipe(plgnins.plumber())
    .pipe(plgnins.clean({force: true}));
});

//解析html中的表达式
gulp.task('html_replace', function () {
  return gulp.src(config.target + '/**/*.thtml')
    .pipe(plgnins.plumber())
    .pipe(plgnins.template({ENV: config.vs_env}))
    .pipe(plgnins.rename({extname: '.htm'}))
    .pipe(gulp.dest(config.target));
});

gulp.task('app_lint', function () {
  return gulp.src(config.build.app_js)
    .pipe(plgnins.plumber())
    .pipe(plgnins.eslint())
    .pipe(plgnins.eslint.format())
    .pipe(plgnins.eslint.failAfterError());
});

//监听文件变化，并及时运行构建任务
gulp.task('watch',function(){

  gulp.watch(config.build.app_css_watch,['app_css']);
  gulp.watch(config.build.lib_css_watch,['lib_css']);
  gulp.watch(config.build.app_js,['app_js']);
  gulp.watch(config.build.lib_js,['lib_js']);
  gulp.watch(config.build.template_js,['template_js']);

  gulp.watch(config.source + '/index.thtml',['html_rev','html_replace']);
  gulp.watch(config.target + '/**/*.thtml',['html_replace']);

  //代码变化后，浏览器自动刷新页面
  var proxyUrlConfig = urlParse(config.browserSyncProxy.url || '');
  merge(config.browserSyncProxy,proxyUrlConfig);
  config.vs_env.APIURL && (config.browserSyncProxy.route = config.vs_env.APIURL);
  config.browserSync.server.middleware = [proxy(config.browserSyncProxy)];
  bs.init(config.browserSync);
});

//拷贝index.html，并替换css/js文件
gulp.task('html_rev', function () {
  var revSrc = [config.target + '/rev-manifest.json',config.source + '/index.thtml'];
  if(!config.rev) revSrc = [revSrc[1]];
  return gulp.src(revSrc)
    .pipe(plgnins.plumber())
    .pipe(plgnins.if(config.rev,plgnins.revCollector()))
    .pipe(gulp.dest(config.target));
});

/****************************************************************************************/


function rev(stream,type){
  return stream.pipe(plgnins.rev())
    .pipe(gulp.dest(config.target + '/assets/' + type))
    .pipe(plgnins.rev.manifest(config.target + '/rev-manifest.json',{merge: true}))
    .pipe(gulp.dest(''));
}

function loadConfig(){
  var env = 'default',
    config,
    configStr = fs.readFileSync('./app.yaml','utf-8'),
    configLocal,
    configLocalStr;

  if(process.argv.indexOf('-e') != -1){
    env = process.argv[process.argv.indexOf('-e') + 1].trim();
    console.warn('env: ' + env);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////
  config = yaml.safeLoad(configStr);

  if(process.argv.indexOf('-local--false') != -1){
    console.warn('force not to read app-*.yaml');
  }else{
    var localName = 'local';
    if(process.argv.indexOf('-local') != -1){
      localName = process.argv[process.argv.indexOf('-local') + 1].trim();
    }

    try{
      configLocalStr = fs.readFileSync('./app-' + localName + '.yaml','utf-8');
      configLocal = yaml.safeLoad(configLocalStr);
      console.warn('find app-' + localName + '.yaml');
    }catch(e){
      console.warn('read app-' + localName + ' fail');
    }
  }

  if(configLocal && configLocal[env]){
    merge(config[env],configLocal[env]);
  }
  ////////////////////////////////////////////////////////////////////////////////////////////////////



  ////////////////////////////////////////////////////////////////////////////////////////////////////
  configStr = configStr.replace(/\{source\}/img,config[env].source)
    .replace(/\{target\}/img,config[env].target);
  config = yaml.safeLoad(configStr);

  if(configLocal && configLocal[env]){
    configLocalStr = configLocalStr.replace(/\{source\}/img,config[env].source)
      .replace(/\{target\}/img,config[env].target);
    configLocal = yaml.safeLoad(configLocalStr);
  }

  if(configLocal && configLocal[env]){
    merge(config[env],configLocal[env]);
  }
  ////////////////////////////////////////////////////////////////////////////////////////////////////

  //相当于配置文件中的rev变量
  processArgv('-r');

  //相当于配置文件中的min变量
  processArgv('-m');

  //相当于配置文件中的watch变量
  processArgv('-w');

  if(process.argv.indexOf('--vs_env') != -1){
    var vs_env = process.argv[process.argv.indexOf('--vs_env') + 1].trim().split('&');
    vs_env.forEach(function(item){
      var key = item.split('=')[0];
      var value = item.split('=')[1];
      config[env].vs_env[key] = value;
      console.warn('vs_env.'+ key +': ' + value);
    })
  }

  return config[env];

  function processArgv(argv){
    if(!argvMap[argv]) return;

    if(process.argv.indexOf(argv) != -1){
      config[env][argvMap[argv]] = true;
    }else if(process.argv.indexOf(argv + '--false') != -1){
      config[env][argvMap[argv]] = false;
    }
  }
}


function merge(source,custom){
  for(var key in custom){
    if(custom.hasOwnProperty(key)){
      if(util.isArray(custom[key]) || util.isString(custom[key]) || util.isDate(custom[key])
        || util.isNumber(custom[key]) || util.isBoolean(custom[key]) || util.isRegExp(custom[key])){
        source[key] = custom[key];
      }else if(util.isObject(custom[key]) && util.isObject(source[key])){
        merge(source[key],custom[key]);
      }
    }
  }
}
