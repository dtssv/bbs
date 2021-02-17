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

    useInit();

    function useInit() {
        $.ajax({
            url:'/my/detail',
            type:'get',
            dataType:'json',
            success:function (res) {
                if(res.code === 1 && res.data) {
                    var data = res.data;
                    $('#L_email').val(data.email);
                    $('#L_nickName').val(data.nickName);
                    $('#L_city').val(data.city);
                    $('#L_sign').val(data.sign);
                    var headPhoto;
                    if(data.headPhoto){
                        headPhoto=data.headPhoto;
                        if(data.sex && data.sex == 1){
                            $('#L_sexMan').prop("checked", "checked");
                        }else if(data.sex && data.sex == 2){
                            $('#L_sexWoman').prop("checked", "checked");
                        }else{
                        }
                    }else{
                        if(data.sex && data.sex == 1){
                            headPhoto = "../../res/images/head/head_boy.png";
                            $('#L_sexMan').prop("checked", "checked");
                        }else if(data.sex && data.sex == 2){
                            headPhoto = "../../res/images/head/head_girl.png";
                            $('#L_sexWoman').prop("checked", "checked");
                        }else{
                            headPhoto = "../../res/images/head/head_default.png";
                        }
                    }
                    $("#L_headPhoto").attr("src",headPhoto);
                    $('#L_nowpass').val(data.password);
                    if(data.admin == 1){
                        $('.adminIndex').show();
                    }else{
                        $('.adminIndex').hide();
                    }
                    form.render();
                }else{
                    window.location.href = '/';
                }
            }
        })
    }

    form.on('submit(userBasic)', function(data){
        var field = data.field;
        if(!field.email){
            layer.msg("邮箱不可为空",{shift: 6});
            return false;
        }
        if(!field.nickName){
            layer.msg("昵称不可为空",{shift: 6});
            return false;
        }
        if(!field.sex){
            layer.msg("请选择您的性别",{shift: 6});
            return false;
        }
        $.ajax({
          url:'/my/updateBasicInfo',
          data:field,
          type:'POST',
          dataType:'json',
          success:function (res) {
              if(res.code === 1){
                  layer.msg('修改成功');
                  location.reload();
              }else{
                  layer.msg(res.msg,{shift:6})
              }
          }  
        })
        return false;
    });

    form.on('submit(changePass)', function(data){
        var field = data.field;
        if(!field.nowpass){
            layer.msg("当前密码不可为空",{shift: 6});
            return false;
        }
        if(!field.password){
            layer.msg("新密码不可为空",{shift: 6});
            return false;
        }
        if(!field.repassword){
            layer.msg("确认密码不可为空",{shift: 6});
            return false;
        }
        if(field.password!=field.repassword){
            layer.msg("确认密码与密码输入不一致",{shift: 6});
            return false;
        }
        $.ajax({
            url:'/my/changePassword',
            data:{nowPass:field.nowpass,newPass:field.password},
            type:'POST',
            dataType:'json',
            success:function (res) {
                if(res.code === 1){
                    layer.msg('修改成功');
                    location.reload();
                }else{
                    layer.msg(res.msg,{shift:6})
                }
            }
        })
        return false;
    });

    //上传图片
    if($('.upload-img')[0]){
        layui.use('upload', function(upload){
            var avatarAdd = $('.avatar-add');

            upload.render({
                elem: '.upload-img'
                ,url: '/my/changeHeadImage/'
                ,size: 50
                ,accept:'images'
                ,exts:'jpg|png|gif|bmp|jpeg'
                ,before: function(){
                    avatarAdd.find('.loading').show();
                }
                ,done: function(res){
                    if(res.code == 1){
                       $('#L_headPhoto').attr('src',res.data);
                       location.reload();
                    } else {
                        layer.msg(res.msg, {icon: 5});
                    }
                    avatarAdd.find('.loading').hide();
                }
                ,error: function(){
                    avatarAdd.find('.loading').hide();
                }
            });
        });
    }
})