window.appModule.filter("isZero",isZeroFn);
function isZeroFn(){
    return function(input){
        if(input === ''||input == null|| input == "undefined" ){
            input = 0;
        }
        return input;
    }
}