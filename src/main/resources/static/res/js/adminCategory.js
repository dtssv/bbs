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
            url:'/admin/pageCategory',
            type:'post',
            dataType:'json',
            data:{pageNum:pageNum,pageSize:pageSize,categoryName:$('#searchName').val()},
            success:function (res) {
                var html = "";
                var pageHtml = "";
                if(res.code === 1 && !res.data.empty) {
                    var data = res.data.content;
                    for(var i in data){
                        var item = data[i];
                        html += '      <tr>' +
                            '           <td>' + item.categoryName + '</td>' +
                            '           <td>' + item.createTime + '</td>' +
                            '           <td>' + item.postNum + '</td>' +
                            '           <td>' + item.moderatorNum + '</td>' +
                            '           <td>' + item.orderNum + '</td>' +
                            '           <td><a class="layui-btn layui-btn-xs" onclick="editForm(' + item.id + ',\'' + item.categoryName + '\',' + item.orderNum + ')">编辑</a>' +
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
    editForm = function (id,categoryName,orderNum) {
        var title = '新增';
        if(id){
            title = '编辑';
        }
        $('#editDiv').removeClass('layui-hide');
        $('#itemId').val(id);
        $('#categoryName').val(categoryName);
        $('#orderNum').val(orderNum);
        editDom = layer.open({
            type: 1,
            content: $('#editDiv'),
            area:['390px', '260px'],
            btn:['确认','取消'],
            title:title,
            yes:function () {
                categoryName = $('#categoryName').val();
                orderNum = $('#orderNum').val();
                if(!categoryName){
                    layer.msg('板块名称不可为空',{shift:6})
                    return false;
                }
                if(!orderNum){
                    layer.msg('顺序不可为空',{shift:6})
                    return false;
                }
                $.ajax({
                    url:'/admin/saveCategory',
                    type:'POST',
                    data:{id:id,categoryName:categoryName,orderNum:orderNum},
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
                url:'/admin/deleteCategory',
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