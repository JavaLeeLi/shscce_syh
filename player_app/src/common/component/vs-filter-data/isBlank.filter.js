window.appModule.filter("isBlank",isBlankFn);
function isBlankFn(){
    return function(input){
        if(input === ''||input == null || input == "undefined"){
            input = "暂无";
        }
        return input;
    }
}