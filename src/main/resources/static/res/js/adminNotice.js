layui.define(['layer', 'laytpl', 'form', 'element', 'upload', 'util','laydate'], function(exports){
    var $ = layui.jquery
        ,layer = layui.layer
        ,laytpl = layui.laytpl
        ,form = layui.form
        ,element = layui.element
        ,upload = layui.upload
        ,util = layui.util
        ,laydate = layui.laydate
        ,device = layui.device()
        ,DISABLED = 'layui-btn-disabled';

    //阻止IE7以下访问
    if(device.ie && device.ie < 8){
        layer.alert('如果您非得使用 IE 浏览器访问Fly社区，那么请使用 IE8+');
    }
    laydate.render({
        elem: '#startTime'
    });
    laydate.render({
        elem: '#endTime'
    });
    loadPage(0,10);

    function loadPage(pageNum,pageSize) {
        $.ajax({
            url:'/admin/pageNotice',
            type:'post',
            dataType:'json',
            data:{pageNum:pageNum,pageSize:pageSize},
            success:function (res) {
                var html = "";
                var pageHtml = "";
                if(res.code === 1 && !res.data.empty) {
                    var data = res.data.content;
                    for(var i in data){
                        var item = data[i];
                        var noticeBody = item.noticeBody;
                        if(noticeBody.length>32){
                            noticeBody = noticeBody.substring(0,16) + "...";
                        }
                        html += '      <tr>' +
                            '           <td title="' + item.noticeBody + '">' + noticeBody + '</td>' +
                            '           <td>' + item.linkUrl + '</td>' +
                            '           <td>' + item.creator + '</td>' +
                            '           <td>' + item.createTime + '</td>' +
                            '           <td>' + item.startTime + '~' + item.endTime + '</td>' +
                            '           <td><a class="layui-btn layui-btn-xs" onclick="editForm(' + item.id + ')">编辑</a>' +
                            '               <a class="layui-btn layui-btn-danger layui-btn-xs" onclick="deleteItem(' + item.id + ')">删除</a></td>' +
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
                        '           <td colspan="6" style="text-align: center">暂无数据</td>' +
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
    var editDom;
    editForm = function (id) {
        var title = '新增';
        if(id){
            title = '编辑';
            $.ajax({
                url:"/admin/pageNotice",
                type:'post',
                data:{id:id},
                success:function (res) {
                    if(res.code == 1){
                        var data = res.data.content[0];
                        $('#itemId').val(id);
                        $('#noticeBody').val(data.noticeBody);
                        $('#linkUrl').val(data.linkUrl);
                        $('#startTime').val(data.startTime);
                        $('#endTime').val(data.endTime);
                    }
                }
            })
        }else{
            $('#itemId').val("");
            $('#noticeBody').val("");
            $('#linkUrl').val("");
            $('#startTime').val("");
            $('#endTime').val("");
        }
        $('#editDiv').removeClass('layui-hide');
        editDom = layer.open({
            type: 1,
            content: $('#editDiv'),
            area:['690px', '360px'],
            btn:['确认','取消'],
            title:title,
            yes:function () {
                var noticeBody = $('#noticeBody').val();
                if(!noticeBody){
                    layer.msg('公告内容不可为空',{shift:6})
                    return false;
                }
                $.ajax({
                    url:'/admin/saveNotice',
                    type:'POST',
                    data:{id:id,noticeBody:noticeBody,linkUrl:$('#linkUrl').val(),startTime:$('#startTime').val(),endTime:$('#endTime').val()},
                    success:function (res) {
                        if(res.code == 1){
                            layer.msg('成功',function () {
                                layer.closeAll();
                                $('#editDiv').addClass('layui-hide');
                                loadPage(0,10);
                            });
                        }else{
                            layer.msg(res.msg,{shift:6})
                        }
                    }
                });
            },
            btn2:function () {
                layer.closeAll();
                $('#editDiv').addClass('layui-hide');
            },
            cancel:function () {
                layer.closeAll();
                $('#editDiv').addClass('layui-hide');
            }
        });
    }

    deleteItem = function (id) {
        layer.confirm('确定要删除么', function(index){
            $.ajax({
                url:'/admin/deleteNotice',
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
})