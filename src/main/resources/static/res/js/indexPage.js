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

    indexPost();

    function indexPost(data){
        $.ajax({
            url:'/post/pagePost',
            type:'post',
            dataType:'json',
            data:data,
            success:function (res) {
                var html = "";
                if(res.code === 1 && !res.data.empty){
                    var data = res.data.content;
                    for(var i in data){
                        var item = data[i];
                        var headUrl = item.headUrl;
                        if(!headUrl){
                            if(item.sex && item.sex == 1){
                                headUrl= "../../res/images/head/head_boy.png";
                            }else if(item.sex && item.sex == 2){
                                headUrl= "../../res/images/head/head_girl.png";
                            }else{
                                headUrl= "../../res/images/head/head_default.png";
                            }
                        }
                        html += '        <li>' +
                            '            <a href="/user/home?userId=' + item.userId + '" class="fly-avatar">' +
                            '              <img src="' + headUrl + '" alt="' + item.nickName + '">' +
                            '            </a>' +
                            '            <h2>' +
                            '              <a class="layui-badge">' + item.categoryName + '</a>' +
                            '              <a href="/post/detail?postId=' + item.id + '">' + item.title + '</a>' +
                            '            </h2>' +
                            '            <div class="fly-list-info">' +
                            '              <a href="/user/home?userId=' + item.userId + '"link>' +
                            '                <cite>' + item.nickName + '</cite>' +
                            '              </a>' +
                            '              <span>' + item.modifyTime + '</span>' +
                            '              <span class="fly-list-nums"> ' +
                            '                <i class="iconfont icon-pinglun1" title="回复"></i>' + item.commentNum +
                            '              </span>\n' +
                            '            </div>\n' +
                            '            <div class="fly-list-badge">';
                        if(item.cream){
                            html += '<span class="layui-badge layui-bg-red">精帖</span>';
                        }
                        html +=
                            '            </div>' +
                            '          </li>';
                    }
                    $("#findMore").show();
                }else{
                    $("#findMore").hide();
                    html = '<div class="fly-none">没有相关数据</div>';
                }
                $('#index-post').empty().html(html);
            }
        });
    }

    function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]); return null; //返回参数值
    }
})