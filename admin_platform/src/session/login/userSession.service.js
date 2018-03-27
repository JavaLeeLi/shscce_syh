/**
 * Created by lily-pc on 2016/6/3.
 */
window.appModule.service('userSession',userSessionFn);
function userSessionFn () {
    this.create  = function(user_id,login_name, roles,privileges) {
        this.user_id   = user_id;
        this.login_name   = login_name;
        this.roles = roles;
        this.privileges = privileges;
    };
    this.destroy = function() {
        this.user_id   = null;
        this.login_name   = null;
        this.roles = null;
        this.privileges = null;
    };
    return this;
}