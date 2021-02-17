package cn.edu.ztbu.zmx.bbs.web;

import cn.edu.ztbu.zmx.bbs.common.CommonConstant;
import cn.edu.ztbu.zmx.bbs.domain.Post;
import cn.edu.ztbu.zmx.bbs.domain.User;
import cn.edu.ztbu.zmx.bbs.service.PostService;
import cn.edu.ztbu.zmx.bbs.service.UserService;
import cn.edu.ztbu.zmx.bbs.util.JacksonUtil;
import cn.edu.ztbu.zmx.bbs.util.LoginContext;
import cn.edu.ztbu.zmx.bbs.vo.PostQueryParamVo;
import cn.edu.ztbu.zmx.bbs.vo.PostVo;
import cn.edu.ztbu.zmx.bbs.vo.ResultVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @program bbs.PostController
 * @author: zhaomengxin
 * @date: 2020/11/15 20:50
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("post")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;


    @RequestMapping(value = "save",method = RequestMethod.POST)
    public ResultVo save(PostVo postVo){
        log.info("保存帖子param:{}", JacksonUtil.toJsonString(postVo));
        if(Objects.isNull(postVo)){
            return ResultVo.fail("参数为空");
        }
        Post post = new Post();
        BeanUtils.copyProperties(postVo,post);
        try {
            return ResultVo.success(postService.save(post));
        } catch (RuntimeException e) {
            return ResultVo.fail(e.getMessage());
        }
    }


    @RequestMapping(value = "pagePost")
    public ResultVo<Page<PostVo>> pagePost(PostQueryParamVo queryParamVo){
        log.info("帖子查询参数：{}",JacksonUtil.toJsonString(queryParamVo));
        if(Objects.isNull(queryParamVo.getPageNum())){
            queryParamVo.setPageNum(CommonConstant.ZERO);
        }
        if(Objects.isNull(queryParamVo.getPageSize())){
            queryParamVo.setPageSize(CommonConstant.DEFAULT_PAGE_SIZE);
        }
        Page<Post> page = postService.findByParam(queryParamVo);
        List<User> authors = userService.selectByIds(Lists.transform(page.getContent(),Post::getUserId));
        Map<Long,User> authorMap = Maps.uniqueIndex(authors,User::getId);
        List<PostVo> voList = Lists.transform(page.getContent(),s->{
            PostVo vo = new PostVo();
            BeanUtils.copyProperties(s,vo);
            User author = authorMap.get(s.getUserId());
            vo.setHeadPhoto(author.getHeadPhoto());
            vo.setSex(author.getSex());
            return vo;
        });
        Page<PostVo> voPage = new PageImpl<>(voList,page.getPageable(),page.getTotalElements());
        return ResultVo.success(voPage);
    }

    @RequestMapping(value = "detail/{type}/{id}")
    public ResultVo<PostVo> postDetail(@PathVariable Integer type,@PathVariable Long id){
        log.info("帖子详情查询参数：{}",id);
        if(Objects.isNull(id)){
            return ResultVo.fail("查询失败");
        }
        Post post = postService.findById(type,id);
        if(post == null){
            return ResultVo.fail("帖子被删除或不存在");
        }
        PostVo postVo = new PostVo();
        BeanUtils.copyProperties(post,postVo);
        User user = LoginContext.getLoginUser();
        if(Objects.nonNull(user) && user.getId().equals(post.getUserId())){
            postVo.setCanEdit(Boolean.TRUE);
            postVo.setHeadPhoto(user.getHeadPhoto());
            postVo.setSex(user.getSex());
        }else{
            postVo.setCanEdit(Boolean.FALSE);
            User author = userService.getById(post.getUserId());
            postVo.setHeadPhoto(author.getHeadPhoto());
            postVo.setSex(author.getSex());
        }
        return ResultVo.success(postVo);
    }
}
