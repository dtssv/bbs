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

    postDetail();

    function postDetail() {
        var postId = getUrlParam('postId');
        postId = parseInt(postId);
        $.ajax({
            url:'/post/detail/' + postId,
            type:'GET',
            dataType:'json',
            success:function (res) {
                if(res.code === 1){
                    var data = res.data;
                    $("#postCategory").empty().html(data.categoryName);
                    $("#postTitle").empty().html(data.title);
                    $("#postComment").empty().html(data.commentNum);
                    $("#postRead").empty().html(data.readNum);
                    $("#postHeadUrl").attr("alt",data.nickName);
                    if(data.headUrl){
                        $("#postHeadUrl").attr("src",data.headUrl);
                    }else{
                        if(data.sex && data.sex == 1){
                            $("#postHeadUrl").attr("src","../../res/images/head/head_boy.png");
                        }else if(data.sex && data.sex == 2){
                            $("#postHeadUrl").attr("src","../../res/images/head/head_girl.png");
                        }else{
                            $("#postHeadUrl").attr("src","../../res/images/head/head_default.png");
                        }
                    }
                    if(data.cream){
                        $("#postCream").show();
                    }else{
                        $("#postCream").hide();
                    }
                    $("#postEdit").attr("href","/my/add?postId" + data.id);
                    if(data.canEdit){
                        $("#postCanEdit").show();
                    }else{
                        $("#postCanEdit").hide();
                    }
                    $("#postAuthor").empty().html(data.nickName);
                    $("#postBody").empty().html(data.postBody);
                }else{
                    window.location.href = '/';
                }
            }
        })
    }

    function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]); return null; //返回参数值
    }
})