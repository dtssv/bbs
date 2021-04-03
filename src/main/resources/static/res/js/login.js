layui.define(['layer', 'laytpl', 'form', 'element', 'upload', 'util'], function(exports){
    var $ = layui.jquery
        ,layer = layui.layer
        ,laytpl = layui.laytpl
        ,form = layui.form
        ,element = layui.element
        ,upload = layui.upload
        ,util = layui.util
        ,device = layui.device()
        ,DISABLED = 'layui-btn-disabled';

    //阻止IE7以下访问
    if(device.ie && device.ie < 8){
        layer.alert('如果您非得使用 IE 浏览器访问Fly社区，那么请使用 IE8+');
    }

    loginerror();

    function loginerror() {
        var url = new URL(window.location);
        var haserror = url.searchParams.has('error');
        if(haserror){
            $.ajax({
                url:'/loginInfo',
                type:'post',
                dataType:'json',
                success:function (res,status,xhr) {
                    var errorMsg = decodeURI(xhr.getResponseHeader('errorMsg'));
                    if(errorMsg.startsWith('Bad')){
                        errorMsg = "帐号不存在或密码错误";
                    }
                    layer.msg(decodeURI(errorMsg));
                }
            });
        }
    }
})