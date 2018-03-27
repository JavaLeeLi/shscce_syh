
window.appModule.controller('GoodsClassController',controllerFn);

function controllerFn() {
    var vm = this;
    vm.openAll = openAllFn;
    vm.closeAll = closeAllFn;
    vm.switch = switchFn;
    vm.switchSub = switchSubFn;

    function openAllFn() {
        angular.forEach(vm.nodes,function(node) {
            node.isOpen = true;
        })
    }

    function closeAllFn() {
        angular.forEach(vm.nodes,function(node) {
            node.isOpen = false;
        })
    }
    
    function switchFn(node) {
        var isFree = node.isFree ;
        if(isFree){
            angular.forEach(node.sub_nodes,function(sub) {
                sub.isFree = true;
            })
        }
    }
    
    function switchSubFn(node,sub) {
        var isFree = sub.isFree;
        var all = true;
        angular.forEach(node.sub_nodes,function(item) {
            if(item.isFree != isFree){
                all = false;
            }
        });
        if(all){
            node.isFree = isFree;
        }else{
            node.isFree = false;
        }
    }
    
    vm.nodes = [
        {
            "id": 1,
            "title": "node1",
            'sort':1,
            'isFree':true,
            "sub_nodes": [
                {
                    "id": 11,
                    "title": "node1.1",
                    'isFree':true,
                    "sort" : 1
                },
                {
                    "id": 12,
                    "title": "node1.2",
                    'isFree':true,
                    "sort" : 2
                }
            ]
        },
        {
            "id": 2,
            "title": "node2",
            'sort':2,
            'isFree':true,
            "sub_nodes": [
                {
                    "id": 21,
                    "title": "node2.1",
                    'isFree':true,
                    "sort" : 1
                },
                {
                    "id": 22,
                    "title": "node2.2",
                    'isFree':true,
                    "sort" : 2
                }
            ]
        },
        {
            "id": 3,
            "title": "node3",
            'sort':3,
            'isFree':true,
            "sub_nodes": [
                {
                    "id": 31,
                    "title": "node3.1",
                    'isFree':true,
                    "sort" : 1
                }
            ]
        }
    ]

}