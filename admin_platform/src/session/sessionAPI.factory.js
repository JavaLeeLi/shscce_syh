
window.appModule.factory('sessionAPI',factoryFn);

function factoryFn($resource){

    var actions = {
        login: {
            url: '/session.json',
            method: 'post'
        },
        logout: {
            url: '/session.json',
            method: 'delete'
        },
        queryPrivileges: {
            url: '/admin/users/:id/get_user_privileges.json',
            method: 'get'
        }
    };

    return $resource('', {_ajaxWrapOption: {}}, actions);

}