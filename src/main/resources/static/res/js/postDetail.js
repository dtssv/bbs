var commentData = {};
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

    postDetail();

    function postDetail() {
        var postId = getUrlParam('postId');
        postId = parseInt(postId);
        detailId = postId;
        commentData.postId = detailId;
        $.ajax({
            url:'/post/detail/0/' + postId,
            type:'GET',
            dataType:'json',
            success:function (res) {
                postComment();
                if(res.code === 1 && res.data){
                    var data = res.data;
                    $(".user-home-index").attr("href","/user/home?userId=" + data.userId);
                    $("#postCategory").empty().html(data.categoryName);
                    $("#postTitle").empty().html(data.title);
                    $("#postComment").empty().html(data.commentNum);
                    $("#postRead").empty().html(data.readNum);
                    $("#postHeadPhoto").attr("alt",data.nickName);
                    $("#postCreateDate").html(data.modifyTime);
                    if(data.headPhoto){
                        $("#postHeadPhoto").attr("src",data.headPhoto);
                    }else{
                        if(data.sex && data.sex == 1){
                            $("#postHeadPhoto").attr("src","../../res/images/head/head_boy.png");
                        }else if(data.sex && data.sex == 2){
                            $("#postHeadPhoto").attr("src","../../res/images/head/head_girl.png");
                        }else{
                            $("#postHeadPhoto").attr("src","../../res/images/head/head_default.png");
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

    replyClick =  function(commentId,nickName){
        if(commentId){
            commentData.replyId = commentId;
        }
        if(nickName) {
            $("#L_content").val('').val('@' + nickName +  '：');
        }
        window.location.href='#comment';
    }
    function postComment(pageNum,pageSize) {
        var url = '/comment/pageComment?postId=' + detailId ;
        if(pageNum){
            url += '&pageNum=' + pageNum;
        }
        if(pageSize){
            url += '&pageSize=' + pageSize;
        }
        $.ajax({
            url:url,
            type:'GET',
            dataType:'json',
            success:function (res) {
                var html = "";
                var pageHtml = "";
                if(res.code === 1 && res.data && !res.data.empty){
                    var data = res.data.content;
                    for(var i in data){
                        var item = data[i];
                        var authorType = "";
                        var editType = "";
                        var headPhoto = "";
                        if(item.author){
                            authorType = " <span>(楼主)</span>";
                        }
                        if(item.canEdit){
                            editType =
                                '                            <span type="edit"><a href="javascript:void(0)" onclick="commentEdit(' + item.id + ')">编辑</a></span>' +
                                '                            <span type="del"><a href="javascript:void(0)" onclick="commentDelete(' + item.id + ')">删除</a></span>';
                        }
                        if(item.headPhoto){
                            headPhoto=item.headPhoto;
                        }else{
                            if(item.sex && item.sex == 1){
                                headPhoto = "../../res/images/head/head_boy.png";
                            }else if(item.sex && item.sex == 2){
                                headPhoto = "../../res/images/head/head_girl.png";
                            }else{
                                headPhoto = "../../res/images/head/head_default.png";
                            }
                        }
                        html += ' <li data-id="' + item.id + '" class="post-daan">' +
                            '            <a name="item"></a>' +
                            '            <div class="detail-about detail-about-reply">' +
                            '              <a class="fly-avatar" style="top: 3px;" href="/user/home?userId=' + item.userId +
                            '">                <img src="' + headPhoto + '" alt=" ' + item.nickName + '">' +
                            '              </a>' +
                            '              <div class="fly-detail-user">' +
                            '                <a href="/user/home?userId=' + item.userId + '" class="fly-link">' +
                            '                  <cite>' + item.nickName + '</cite>' +
                            '                </a>' + authorType +
                            '              </div>' +
                            '              <div class="detail-hits">' +
                            '                <span>' + item.modifyTime + '</span>' +
                            '              </div>' +
                            '            </div>' +
                            '            <div class="detail-body post-body photos">' +
                            '              <p>' + item.commentBody + '</p>' +
                            '            </div>' +
                            '            <div class="post-reply">' +
                            '              <span type="reply">\n' +
                            '                <i class="iconfont icon-svgmoban53"></i>' +
                            '                <a href="javascript:void(0)" onclick="replyClick(' + item.id + ',\'' + item.nickName + '\')">回复</a>' +
                            '              </span>' +
                            '              <div class="post-admin">' + editType +
                            '              </div>' +
                            '            </div>' +
                            '          </li>';
                    }
                    if(res.data.first){
                        pageHtml +='<span class="laypage-curr">1</span>';
                    }else{
                        pageHtml += '<a href="javascript:void(0)" onclick="pageComment(' + (res.data.number - 1) + ',10)" class="laypage-prev">上一页</a>';
                        pageHtml += '<a href="javascript:void(0)" onclick="pageComment(0,10)" class="laypage-first" title="首页">首页</a>';
                        pageHtml +='<span class="laypage-curr">' + (res.data.number + 1) + '</span>';
                    }
                    if(!res.data.last){
                        pageHtml += '<a href="javascript:void(0)" onclick="pageComment(' + (res.data.totalPages - 1) + ',10)" class="laypage-last" title="尾页">尾页</a>';
                        pageHtml += '<a href="javascript:void(0)" onclick="pageComment(' + (res.data.number + 1) + ',10)" class="laypage-next">下一页</a>';
                    }
                }else{
                    html += '<li class="fly-none">消灭零回复</li>';
                }
                $("#post").empty().html(html);
                $("#commentPage").empty().html(pageHtml);
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