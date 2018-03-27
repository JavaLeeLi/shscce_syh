
window.appModule.controller('HomeController',controllerFn);

function controllerFn(){
    var vm = this;

    $('#chart').highcharts({
        chart:{
            type:'line'
        },
        title:{
            text:'飞行轨迹施药量'
        },
        xAxis:{
            title:{
                text:'时间(秒)'
            },
            categories:[1,2,3,4,5,6]
        },
        yAxis:{
            title:{
                text:'施药量(ml)'
            }
        },
        plotOptions:{
            line:{
                dataLabels:{
                    enabled:true
                },
                enableMouseTracking:false
            }
        },
        series:[{
            name:'施药量',
            data:[11,22,33,44,55]
        }]
    })

}
