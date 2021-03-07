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
                        var operatorHtml = '';
                        if(item.status == 1){
                            operatorHtml += '<a class="layui-btn layui-btn-xs" onclick="changeStatus(' + item.id + ',-1)">禁用</a>';
                            operatorHtml += '<a class="layui-btn layui-btn-xs" onclick="changeStatus(' + item.id + ',0)">禁止登录</a>';
                        }else  if(item.status == -1){
                            operatorHtml += '<a class="layui-btn layui-btn-xs" onclick="changeStatus(' + item.id + ',1)">启用</a>';
                            operatorHtml += '<a class="layui-btn layui-btn-xs" onclick="changeStatus(' + item.id + ',0)">禁止登录</a>';
                        }else{
                            operatorHtml += '<a class="layui-btn layui-btn-xs" onclick="changeStatus(' + item.id + ',1)">允许登录</a>';
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
                            '           <td>' + operatorHtml +
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
                        '           <td colspan="9" style="text-align: center">暂无数据</td>' +
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

    setModerator = function (id) {
        $.ajax({
            url:'/category/findAll',
            type:'POST',
            success:function (res) {
                if(res.code === 1){
                    var data = res.data;
                    var html = '<option value="0">请选择</option>';
                    for(var i in data){
                        var item = data[i];
                        html += "<option value='" + item.id + "'>" + item.categoryName + "</option>";
                    }
                    $("#category").empty().html(html);
                    form.render("select");
                    $('#operatorDiv').removeClass('layui-hide');
                    layer.open({
                        type: 1,
                        content: $('#operatorDiv'),
                        area:['390px', '360px'],
                        btn:['确认','取消'],
                        title:'设为版主',
                        yes:function () {
                            var categoryId = $('#category').val();
                            if(!categoryId){
                                layer.msg('请选择板块',{shift:6})
                                return false;
                            }
                            $.ajax({
                                url:'/admin/setModerator',
                                type:'POST',
                                data:{id:id,categoryId:categoryId},
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
                            $('#operatorDiv').addClass('layui-hide');
                        },
                        cancel:function () {
                            layer.closeAll();
                            $('#operatorDiv').addClass('layui-hide');
                        }
                    });
                }
            }
        })
    }

    changeStatus = function (id,status) {
        layer.confirm('确定要这样做么', function(index){
            $.ajax({
                url:'/admin/changeStatus',
                type:'POST',
                data:{id:id,status:status},
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