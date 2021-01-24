var detailId;
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

    userDetail();
    function userDetail() {
        var userId = getUrlParam('userId');
        userId = parseInt(userId);
        detailId = userId;
        $.ajax({
            url:'/user/detail?id=' + detailId,
            type:'GET',
            dataType:'json',
            success:function (res) {
                userPosts();
                if(res.code === 1 && res.data){
                    var data = res.data;
                    var headUrl;
                    $(document).attr('title',data.nickName + '的主页');
                    if(data.headUrl){
                        headUrl=data.headUrl;
                    }else{
                        if(data.sex && data.sex == 1){
                            headUrl = "../../res/images/head/head_boy.png";
                            $("#userSexMan").show();
                            $("#userSexWoman").hide();
                        }else if(data.sex && data.sex == 2){
                            headUrl = "../../res/images/head/head_girl.png";
                            $("#userSexMan").hide();
                            $("#userSexWoman").show();
                        }else{
                            headUrl = "../../res/images/head/head_default.png";
                            $("#userSexMan").hide();
                            $("#userSexWoman").hide();
                        }
                    }
                    $("#userHeadUrl").attr("src",headUrl);
                    $("#userHeadUrl").attr("alt",data.nickName);
                    $("#userNickName").empty().html(data.nickName);
                    if(data.email){
                        $("#userMail").attr("href","mailto:" + data.email).show();
                    }
                    if(data.admin === 1){
                        $("#userAdmin").show();
                    }else{
                        $("#userAdmin").hide();
                    }
                    $("#userCreateDate").empty().html(data.createTime);
                    if(data.city){
                        $("#userCity").empty().html('来自' + data.city);
                    }
                    $("#userSign").empty().html(data.sign);
                }else{
                    window.location.href='/index'
                }
            }
        })
    }

    function userPosts() {
        var url = '/post/pagePost?userId=' + detailId ;
        $.ajax({
            url:url,
            type:'GET',
            dataType:'json',
            success:function (res) {
                var html = "";
                if(res.code === 1 && res.data && !res.data.empty){
                    var data = res.data.content;
                    for(var i in data){
                        var item = data[i];
                        var creamHtml = "";
                        if(item.cream){
                            creamHtml = '<span class="fly-jing">精</span>';
                        }
                        html += '<li>' +
                                creamHtml +
                                '<a href="/post/detail?postId=' + item.id + '" class="post-title">' + item.title + '</a>' +
                                '<i>' + item.modifyTime + '</i>' +
                                '<em class="layui-hide-xs">' + item.readNum + '阅/' + item.commentNum + '回复</em>' +
                            '</li>';
                    }
                }else{
                    html += '<div class="fly-none" style="min-height: 50px; padding:30px 0; height:auto;"><i style="font-size:14px;">没有发表任何求解</i></div>';
                }
                $("#userPosts").empty().html(html);
            }
        })
    }
    pageComment =function(pageNum,pageSize){
        postComment(pageNum,pageSize);
    }
    commentEdit = function(commentId){
        $.ajax({
            url:'/comment/detail?id=' + commentId,
            type:'POST',
            data:commentData,
            success:function (res) {
                if(res.code === 0){
                    layer.msg(res.msg,{shift: 6});
                }else {
                    if(res.data){
                        $("#L_content").val('').val(res.data.commentBody);
                        commentData.id = res.data.id;
                    }
                    window.location.href = '#comment';
                }
            }
        });
        return false;
    }
    commentDelete = function(commentId){
        $.ajax({
            url:'/comment/delete?id=' + commentId,
            type:'POST',
            data:commentData,
            success:function (res) {
                if(res.code != undefined){
                    if(res.code === 0){
                        layer.msg(res.msg,{shift: 6});
                    }else {
                        postComment();
                    }
                }else{
                    window.location.href = '/login';
                }
            }
        });
        return false;
    }
    form.on('submit(reply)',function (data) {
        var field = data.field;
        if(!field.commentBody){
            layer.msg("回复内容不可为空",{shift: 6});
            return false;
        }
        commentData.commentBody = field.commentBody;
        $.ajax({
            url:'/comment/reply',
            type:'POST',
            data:commentData,
            success:function (res) {
                if(res.code != undefined){
                    if(res.code === 0){
                        layer.msg(res.msg,{shift: 6});
                    }else {
                        postComment(detailId);
                        $("#L_content").val('');
                    }
                }else{
                    window.location.href='/login'
                }
            }
        });
        return false;
    });
    function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]); return null; //返回参数值
    }

})