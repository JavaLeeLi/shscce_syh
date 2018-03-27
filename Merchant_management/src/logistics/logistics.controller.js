
window.appModule.controller('LogisticsController',controllerFn);

function controllerFn($scope, $log){
    var vm = this;

    vm.sites = [
        {site : "aa", url : ""},
        {site : "bb", url : ""},
        {site : "cc", url : ""}
    ];

    /* 表格数据 */
        var u = [
            { "id": 1, "code": "YP19023478", "dt":"按件数", "status":"是" },
            { "id": 2, "code": "YP19023478", "dt":"按件数", "status":"否" },
            { "id": 3, "code": "YP19023478", "dt":"按件数", "status":"是" },
            { "id": 4, "code": "YP19023478", "dt":"按件数", "status":"否" },
            { "id": 5, "code": "YP19023478", "dt":"按件数", "status":"否" },
            { "id": 6, "code": "YP19023478", "dt":"按件数", "status":"是" },
            { "id": 7, "code": "YP19023478", "dt":"按件数", "status":"否" },
        ];
        //vm.users = u;
    vm.data = u;

    var u2 = [
        { "id": 1, "code": "YP19023478", "dt":"按件数", "status":"是" },
        { "id": 2, "code": "YP19023478", "dt":"按件数", "status":"否" }
    ];
    vm.data2 = u2;


    //定义一个单击删除按钮时触发的事件，用于删除选中行
    vm.delete = function ($index) {

        //alert($index);

        if($index>=0){
            if(confirm("是否删除商品"+data[$index].id) ){
                data.splice($index,1);

                //page(data);
            }
        }
    };

}


