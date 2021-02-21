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

    loadPage(0,10);

    function loadPage(pageNum,pageSize) {
        $.ajax({
            url:'/admin/pageUser',
            type:'post',
            dataType:'json',
            data:{
                pageNum:pageNum,
                pageSize:pageSize,
                categoryName:$('#categoryName').val(),
                nickName:$('#nickName').val(),
                title:$('#postTitle').val()
            },
            success:function (res) {
                var html = "";
                var pageHtml = "";
                if(res.code === 1 && !res.data.empty) {
                    var data = res.data.content;
                    for(var i in data){
                        var item = data[i];
                        var sex;
                        if(item.sex == 1){
                            sex = '男';
                        }else if(item.sex == 2){
                            sex = '女';
                        }else{
                            sex == '未知';
                        }
                        if(item.cream == 1){
                            operatorHtml = '<a class="layui-btn layui-btn-xs" onclick="cream(' + item.id + ')">加精</a>';
                        }
                        html += '      <tr>' +
                            '           <td><a href="/user/home?userId=' + item.id + '">' + item.nickName + '</a></td>' +
                            '           <td>' + item.username + '</td>' +
                            '           <td>' + item.email + '</td>' +
                            '           <td>' + item.city + '</td>' +
                            '           <td>' + sex + '</td>' +
                            '           <td>' + item.registerTime + '</td>' +
                            '           <td>' + item.postNum + '</td>' +
                            '           <td>' + item.commentNum + '</td>' +
                            '           <td>' +
                            '               <a class="layui-btn layui-btn-danger layui-btn-xs" onclick="deleteItem(' + item.id + ')">删除</a>' +
                            '           </td>' +
                            '          </tr>';
                    }
                    if(res.data.first){
                        pageHtml +='<span class="laypage-curr">1</span>';
                    }else{
                        pageHtml += '<a href="javascript:void(0)" onclick="page(' + (res.data.number - 1) + ',10)" class="laypage-prev">上一页</a>';
                        pageHtml += '<a href="javascript:void(0)" onclick="page(0,10)" class="laypage-first" title="首页">首页</a>';
                        pageHtml +='<span class="laypage-curr">' + (res.data.number + 1) + '</span>';
                    }
                    if(!res.data.last){
                        pageHtml += '<a href="javascript:void(0)" onclick="page(' + (res.data.totalPages - 1) + ',10)" class="laypage-last" title="尾页">尾页</a>';
                        pageHtml += '<a href="javascript:void(0)" onclick="page(' + (res.data.number + 1) + ',10)" class="laypage-next">下一页</a>';
                    }
                }else{
                    html = '      <tr>' +
                        '           <td colspan="7" style="text-align: center">暂无数据</td>' +
                        '        </tr>';
                }
                $('#dataList').empty().html(html);
                $("#dataPage").empty().html(pageHtml);
            }
        })
    }

    page =function (pageNum,pageSize) {
        loadPage(pageNum,pageSize)
    }
    
    deleteItem = function (id) {
        layer.confirm('确定要删除么', function(index){
            $.ajax({
                url:'/admin/deletePost',
                type:'POST',
                data:{id:id},
                success:function (res) {
                    if(res.code == 1){
                        layer.msg('删除成功',function () {
                            loadPage(0,10);
                        });
                    }else{
                        layer.msg(res.msg,{shift:6})
                    }
                }
            });
            layer.close(index);
        });
    }

    cream = function (id) {
        layer.confirm('确定要加精么', function(index){
            $.ajax({
                url:'/admin/creamPost',
                type:'POST',
                data:{id:id},
                success:function (res) {
                    if(res.code == 1){
                        layer.msg('操作成功',function () {
                            loadPage(0,10);
                        });
                    }else{
                        layer.msg(res.msg,{shift:6})
                    }
                }
            });
            layer.close(index);
        });
    }
})