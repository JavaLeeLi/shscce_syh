window.appModule.filter("money",moneyFn);
function moneyFn(){
    return function(input){
        if(input === ''||input == null|| input == "undefined" ){
            input = '暂无数据';
        }else{
            var newStr = "";
            var count = 0;
            var str = input.toString();
            if(str.indexOf(".")==-1){
                for(var i=str.length-1;i>=0;i--){
                    if(count % 3 == 0 && count != 0){
                        newStr = str.charAt(i) + "," + newStr;
                    }else{
                        newStr = str.charAt(i) + newStr;
                    }
                    count++;
                }
                input ="¥ " + newStr + ".00" ; //自动补小数点后两位

            }else{
                input ="¥ " + input + "0" ;
            }


        }
        return input;
    }
}