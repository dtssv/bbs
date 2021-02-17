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

    indexInit();

    function indexInit() {
        $.ajax({
            url:'/admin/todayData',
            type:'get',
            dataType:'json',
            success:function (res) {
                if(res.code === 1 && res.data) {
                    var data = res.data;
                    $('#todayPost').empty().html(data.todayPost);
                    $('#todayUser').empty().html(data.todayUser);
                }else{
                    window.location.href = '/';
                }
            }
        })
    }
})