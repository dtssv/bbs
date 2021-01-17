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

    postCategory();
    postInit();

    function postCategory() {
        $.ajax({
            url:'/category/findAll',
            method:'get',
            dataType:'json',
            success:function (res) {
                if(res.code === 1){
                    var data = res.data;
                    var html = "";
                    for(var i in data){
                        var item = data[i];
                        html += "<option value='" + item.id + "'>" + item.categoryName + "</option>";
                    }
                    $("#addCategory").empty().html(html);
                    form.render("select");
                }
            }
        })
    }

    function postInit() {
        var postId = getUrlParam("postId");
        if(postId){
            postId = parseInt(postId);
            $.ajax({
                url:'/post/detail/' + postId,
                type:'GET',
                dataType:'json',
                success:function (res) {
                    if(res.code === 1){
                        var data = res.data;
                        $("#addCategory").val(data.categoryId);
                        $("#L_id").val(data.id);
                        $("#L_title").val(data.title);
                        $("#L_postBody").val(data.postBody);
                        form.render("select");
                    }else{
                        window.location.href = '/';
                    }
                }
            })
        }
    }
    
    function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]); return null; //返回参数值
    }
})