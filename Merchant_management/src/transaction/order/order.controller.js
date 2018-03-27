
window.appModule.controller('OrderController',controllerFn);

function controllerFn($scope, $log){
    var vm = this;

    vm.switch = switchFn;
    function switchFn(tabName) {
        vm.tab = tabName;
    }

    /* 下拉框数据 */
    vm.sites = [
        {site : "邮票", url : ""},
        {site : "纪念币", url : ""},
        {site : "古币", url : ""}
    ];
    vm.selectedSite = vm.sites[0];


    /* 表格数据 */
        var u = [
            { "id": 1, "pay_method": "在线支付", "gender": "999", "code": "YP19023478", "dt":"2017-10-01", "status":"等待审核", "order":"待发货", "num":"1", "state":"已拒绝" },
            { "id": 2, "pay_method": "在线支付", "gender": "女", "code": "YP19023478", "dt":"2017-10-01", "status":"未通过", "order":"待付款", "num":"2", "state":"待处理" },
            { "id": 3, "pay_method": "在线支付", "gender": "男", "code": "YP19023478", "dt":"2017-10-01", "status":"等待审核", "order":"待收货", "num":"3", "state":"已撤销" },
            { "id": 4, "pay_method": "在线支付", "gender": "女", "code": "YP19023478", "dt":"2017-10-01", "status":"未通过", "order":"待发货", "num":"4", "state":"待仲裁" },
            { "id": 5, "pay_method": "在线支付", "gender": "男", "code": "YP19023478", "dt":"2017-10-01", "status":"等待审核", "order":"待发货", "num":"5", "state":"已拒绝" },
            { "id": 6, "pay_method": "在线支付", "gender": "女", "code": "YP19023478", "dt":"2017-10-01", "status":"等待审核", "order":"待发货", "num":"1", "state":"已拒绝" },
            { "id": 7, "pay_method": "在线支付", "gender": "男", "code": "YP19023478", "dt":"2017-10-01", "status":"未通过", "order":"待发货", "num":"1", "state":"已拒绝" },
            { "id": 8, "pay_method": "在线支付", "gender": "女", "code": "YP19023478", "dt":"2017-10-01", "status":"等待审核", "order":"待发货", "num":"1", "state":"已拒绝" },
            { "id": 9, "pay_method": "在线支付", "gender": "男", "code": "YP19023478", "dt":"2017-10-01", "status":"未通过", "order":"待发货", "num":"1", "state":"已拒绝" },
            { "id": 10, "pay_method": "在线支付", "gender": "女", "code": "YP19023478", "dt":"2017-10-01", "status":"等待审核", "order":"待发货", "num":"1", "state":"已拒绝" },
            { "id": 11, "pay_method": "在线支付", "gender": "男", "code": "YP19023478", "dt":"2017-10-01", "status":"未通过", "order":"待发货", "num":"1", "state":"已拒绝" },
            { "id": 12, "pay_method": "在线支付", "gender": "女", "code": "YP19023478", "dt":"2017-10-01", "status":"等待审核", "order":"待发货", "num":"1", "state":"已拒绝" }
        ];
        //vm.users = u;
    var data = u;

    page(data);

    /* 分页 */
    function page(data){
        //$scope.totalItems = 12;   //总共有多少条数据
        $scope.currentPage = 1;   //当前第几页
        $scope.allitem=[];        //存放所有页
        $scope.itemsPage = 5;    //每页显示的数量.设置值小于1表示显示所有项  默认为10

        //alert( $scope.addr);
        //var data = u;
        var num= data.length;
        //alert(num);

        $scope.totalItems =num;  //共有多少条数据

        //此方法可以将一个数组分成多个数组并且放在了一个大数组里面，按每个数组10条数据【因为组件默认为10条数据一页】存放，假如41条数据的话我们将分成5页
        for(var i=0;i<num;i+=5){
            $scope.allitem.push(data.slice(i,i+5))
        }
        //alert($scope.allitem.length);
    }


    //定义一个单击删除按钮时触发的事件，用于删除选中行
    vm.delete = function ($index) {

        //alert($index);

        if($index>=0){
            if(confirm("是否删除商品"+data[$index].id) ){
                data.splice($index,1);

                page(data);
            }
        }
    };

}


