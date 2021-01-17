/**

 @Name: 求解板块

 */
 
layui.define('fly', function(exports){

  var $ = layui.jquery;
  var layer = layui.layer;
  var util = layui.util;
  var laytpl = layui.laytpl;
  var form = layui.form;
  var fly = layui.fly;
  
  var gather = {}, dom = {
    post: $('#post')
    ,content: $('#L_content')
    ,postCount: $('#postCount')
  };



  form.on('submit(addPost)', function(data){
    var field = data.field;
    if(!field.categoryId){
      layer.msg("请算着所属板块",{shift: 6});
      return false;
    }
    if(!field.title){
      layer.msg("标题不可为空",{shift: 6});
      return false;
    }
    if(!field.postBody){
      layer.msg("内容不可为空",{shift: 6});
      return false;
    }
    fly.json('/post/save',field,function (res) {
      if(res.code === '0'){
        layer.msg(res.msg,{shift: 6});
      }else {
        window.location.href = '/'
      }
    })
    return false;
  });
  //监听专栏选择
  form.on('select(column)', function(obj){
    var value = obj.value
    ,elemQuiz = $('#LAY_quiz')
    ,tips = {
      tips: 1
      ,maxWidth: 250
      ,time: 10000
    };
    elemQuiz.addClass('layui-hide');
    if(value === '0'){
      layer.tips('下面的信息将便于您获得更好的答案', obj.othis, tips);
      elemQuiz.removeClass('layui-hide');
    } else if(value === '99'){
      layer.tips('系统会对【分享】类型的帖子予以飞吻奖励，但我们需要审核，通过后方可展示', obj.othis, tips);
    }
  });

  //提交回答
  fly.form['/post/reply/'] = function(data, required){
    var tpl = '<li>\
      <div class="detail-about detail-about-reply">\
        <a class="fly-avatar" href="/u/{{ layui.cache.user.uid }}" target="_blank">\
          <img src="{{= d.user.avatar}}" alt="{{= d.user.username}}">\
        </a>\
        <div class="fly-detail-user">\
          <a href="/u/{{ layui.cache.user.uid }}" target="_blank" class="fly-link">\
            <cite>{{d.user.username}}</cite>\
          </a>\
        </div>\
        <div class="detail-hits">\
          <span>刚刚</span>\
        </div>\
      </div>\
      <div class="detail-body post-body photos">\
        {{ d.content}}\
      </div>\
    </li>'
    data.content = fly.content(data.content);
    laytpl(tpl).render($.extend(data, {
      user: layui.cache.user
    }), function(html){
      required[0].value = '';
      dom.post.find('.fly-none').remove();
      dom.post.append(html);
      
      var count = dom.postCount.text()|0;
      dom.postCount.html(++count);
    });
  };

  //求解管理
  gather.postAdmin = {
    //删求解
    del: function(div){
      layer.confirm('确认删除该求解么？', function(index){
        layer.close(index);
        fly.json('/api/post-delete/', {
          id: div.data('id')
        }, function(res){
          if(res.status === 0){
            location.href = '/post/';
          } else {
            layer.msg(res.msg);
          }
        });
      });
    }
    
    //设置置顶、状态
    ,set: function(div){
      var othis = $(this);
      fly.json('/api/post-set/', {
        id: div.data('id')
        ,rank: othis.attr('rank')
        ,field: othis.attr('field')
      }, function(res){
        if(res.status === 0){
          location.reload();
        }
      });
    }

    //收藏
    ,collect: function(div){
      var othis = $(this), type = othis.data('type');
      fly.json('/collection/'+ type +'/', {
        cid: div.data('id')
      }, function(res){
        if(type === 'add'){
          othis.data('type', 'remove').html('取消收藏').addClass('layui-btn-danger');
        } else if(type === 'remove'){
          othis.data('type', 'add').html('收藏').removeClass('layui-btn-danger');
        }
      });
    }
  };

  $('body').on('click', '.post-admin', function(){
    var othis = $(this), type = othis.attr('type');
    gather.postAdmin[type] && gather.postAdmin[type].call(this, othis.parent());
  });

  //异步渲染
  var asyncRender = function(){
    var div = $('.fly-admin-box'), postAdmin = $('#LAY_postAdmin');
    //查询帖子是否收藏
    if(postAdmin[0] && layui.cache.user.uid != -1){
      fly.json('/collection/find/', {
        cid: div.data('id')
      }, function(res){
        postAdmin.append('<span class="layui-btn layui-btn-xs post-admin '+ (res.data.collection ? 'layui-btn-danger' : '') +'" type="collect" data-type="'+ (res.data.collection ? 'remove' : 'add') +'">'+ (res.data.collection ? '取消收藏' : '收藏') +'</span>');
      });
    }
  }();

  //解答操作
  gather.postActive = {
    zan: function(li){ //赞
      var othis = $(this), ok = othis.hasClass('zanok');
      fly.json('/api/post-zan/', {
        ok: ok
        ,id: li.data('id')
      }, function(res){
        if(res.status === 0){
          var zans = othis.find('em').html()|0;
          othis[ok ? 'removeClass' : 'addClass']('zanok');
          othis.find('em').html(ok ? (--zans) : (++zans));
        } else {
          layer.msg(res.msg);
        }
      });
    }
    ,reply: function(li){ //回复
      var val = dom.content.val();
      var aite = '@'+ li.find('.fly-detail-user cite').text().replace(/\s/g, '');
      dom.content.focus()
      if(val.indexOf(aite) !== -1) return;
      dom.content.val(aite +' ' + val);
    }
    ,accept: function(li){ //采纳
      var othis = $(this);
      layer.confirm('是否采纳该回答为最佳答案？', function(index){
        layer.close(index);
        fly.json('/api/post-accept/', {
          id: li.data('id')
        }, function(res){
          if(res.status === 0){
            $('.post-accept').remove();
            li.addClass('post-daan');
            li.find('.detail-about').append('<i class="iconfont icon-caina" title="最佳答案"></i>');
          } else {
            layer.msg(res.msg);
          }
        });
      });
    }
    ,edit: function(li){ //编辑
      fly.json('/post/getDa/', {
        id: li.data('id')
      }, function(res){
        var data = res.rows;
        layer.prompt({
          formType: 2
          ,value: data.content
          ,maxlength: 100000
          ,title: '编辑回帖'
          ,area: ['728px', '300px']
          ,success: function(layero){
            fly.layEditor({
              elem: layero.find('textarea')
            });
          }
        }, function(value, index){
          fly.json('/post/updateDa/', {
            id: li.data('id')
            ,content: value
          }, function(res){
            layer.close(index);
            li.find('.detail-body').html(fly.content(value));
          });
        });
      });
    }
    ,del: function(li){ //删除
      layer.confirm('确认删除该回答么？', function(index){
        layer.close(index);
        fly.json('/api/post-delete/', {
          id: li.data('id')
        }, function(res){
          if(res.status === 0){
            var count = dom.postCount.text()|0;
            dom.postCount.html(--count);
            li.remove();
            //如果删除了最佳答案
            if(li.hasClass('post-daan')){
              $('.post-status').removeClass('post-status-ok').text('求解中');
            }
          } else {
            layer.msg(res.msg);
          }
        });
      });    
    }
  };

  $('.post-reply span').on('click', function(){
    var othis = $(this), type = othis.attr('type');
    gather.postActive[type].call(this, othis.parents('li'));
  });


  //定位分页
  if(/\/page\//.test(location.href) && !location.hash){
    var replyTop = $('#flyReply').offset().top - 80;
    $('html,body').scrollTop(replyTop);
  }

  exports('post', null);
});