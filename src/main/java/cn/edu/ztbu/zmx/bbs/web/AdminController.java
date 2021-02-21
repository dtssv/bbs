package cn.edu.ztbu.zmx.bbs.web;

import cn.edu.ztbu.zmx.bbs.common.CommonConstant;
import cn.edu.ztbu.zmx.bbs.domain.Category;
import cn.edu.ztbu.zmx.bbs.domain.Post;
import cn.edu.ztbu.zmx.bbs.domain.User;
import cn.edu.ztbu.zmx.bbs.service.*;
import cn.edu.ztbu.zmx.bbs.util.JacksonUtil;
import cn.edu.ztbu.zmx.bbs.util.LoginContext;
import cn.edu.ztbu.zmx.bbs.vo.*;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @program bbs.AdminController
 * @author: zhaomengxin
 * @date: 2020/11/15 20:48
 * @Description:
 */
@Slf4j
@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("index")
    public String index(){
        return "/admin/index";
    }

    @RequestMapping("category")
    public String category(){
        return "/admin/category";
    }

    @RequestMapping("user")
    public String user(){
        return "/admin/user";
    }

    @RequestMapping("notice")
    public String notice(){
        return "/admin/notice";
    }

    @RequestMapping("post")
    public String post(){
        return "/admin/post";
    }

    @RequestMapping("comment")
    public String comment(){
        return "/admin/comment";
    }

    @ResponseBody
    @RequestMapping("todayData")
    public ResultVo todayData(){
        Long todayUser = userService.todayData();
        Long todayPost = postService.todayData();
        Map<String,Long> todayMap = Maps.newHashMap();
        todayMap.put("todayUser",todayUser);
        todayMap.put("todayPost",todayPost);
        return ResultVo.success(todayMap);
    }

    @ResponseBody
    @RequestMapping("pageCategory")
    public ResultVo<Page<CategoryVo>> pageCategory(CategoryQueryParamVo queryParamVo){
        log.info("板块查询参数：{}", JacksonUtil.toJsonString(queryParamVo));
        if(Objects.isNull(queryParamVo.getPageNum())){
            queryParamVo.setPageNum(CommonConstant.ZERO);
        }
        if(Objects.isNull(queryParamVo.getPageSize())){
            queryParamVo.setPageSize(CommonConstant.DEFAULT_PAGE_SIZE);
        }
        Page<Category> page = categoryService.pageByParam(queryParamVo);
        List<CategoryVo> voList = Lists.transform(page.getContent(),s->{
            CategoryVo vo = new CategoryVo();
            BeanUtils.copyProperties(s,vo);
            return vo;
        });
        return ResultVo.success(new PageImpl<>(voList,page.getPageable(),page.getTotalElements()));
    }

    @ResponseBody
    @RequestMapping("deleteCategory")
    public ResultVo deleteCategory(Long id){
        if(Objects.isNull(id)){
            return ResultVo.fail("请选择要删除的板块");
        }
        String result = categoryService.delete(id);
        if(Strings.isNullOrEmpty(result)){
            return ResultVo.success("");
        }
        return ResultVo.fail(result);
    }

    @ResponseBody
    @RequestMapping("saveCategory")
    public ResultVo saveCategory(CategoryVo vo){
        if(Objects.isNull(vo) || Strings.isNullOrEmpty(vo.getCategoryName()) || Objects.isNull(vo.getOrderNum())){
            return ResultVo.fail("参数非法");
        }
        Category category = categoryService.save(vo, LoginContext.getLoginUser());
        if(Objects.isNull(category)){
            return ResultVo.fail("板块已被删除或不存在");
        }
        return ResultVo.success("");
    }

    @ResponseBody
    @RequestMapping("pagePost")
    public ResultVo<Page<PostVo>> pagePost(PostQueryParamVo queryParamVo){
        log.info("帖子查询参数：{}", JacksonUtil.toJsonString(queryParamVo));
        if(Objects.isNull(queryParamVo.getPageNum())){
            queryParamVo.setPageNum(CommonConstant.ZERO);
        }
        if(Objects.isNull(queryParamVo.getPageSize())){
            queryParamVo.setPageSize(CommonConstant.DEFAULT_PAGE_SIZE);
        }
        Page<Post> page = postService.findByParam(queryParamVo);
        List<PostVo> voList = Lists.transform(page.getContent(),s->{
            PostVo vo = new PostVo();
            BeanUtils.copyProperties(s,vo);
            return vo;
        });
        return ResultVo.success(new PageImpl<>(voList,page.getPageable(),page.getTotalElements()));
    }

    @ResponseBody
    @RequestMapping("deletePost")
    public ResultVo deletePost(Long id){
        if(Objects.isNull(id)){
            return ResultVo.fail("请选择要删除的帖子");
        }
        String result = postService.delete(id);
        if(Strings.isNullOrEmpty(result)){
            return ResultVo.success("");
        }
        return ResultVo.fail(result);
    }
    @ResponseBody
    @RequestMapping("creamPost")
    public ResultVo creamPost(Long id){
        if(Objects.isNull(id)){
            return ResultVo.fail("请选择要加精的帖子");
        }
        String result = postService.cream(id);
        if(Strings.isNullOrEmpty(result)){
            return ResultVo.success("");
        }
        return ResultVo.fail(result);
    }

    @ResponseBody
    @RequestMapping("pageUser")
    public ResultVo<Page<UserVo>> pageUser(UserQueryParamVo queryParamVo){
        log.info("用户查询参数：{}", JacksonUtil.toJsonString(queryParamVo));
        if(Objects.isNull(queryParamVo.getPageNum())){
            queryParamVo.setPageNum(CommonConstant.ZERO);
        }
        if(Objects.isNull(queryParamVo.getPageSize())){
            queryParamVo.setPageSize(CommonConstant.DEFAULT_PAGE_SIZE);
        }
        Page<User> page = userService.findByParam(queryParamVo);
        List<UserVo> voList = Lists.transform(page.getContent(),s->{
            UserVo vo = new UserVo();
            BeanUtils.copyProperties(s,vo);
            return vo;
        });
        return ResultVo.success(new PageImpl<>(voList,page.getPageable(),page.getTotalElements()));
    }
}
