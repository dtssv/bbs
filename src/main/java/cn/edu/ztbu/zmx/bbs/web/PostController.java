package cn.edu.ztbu.zmx.bbs.web;

import cn.edu.ztbu.zmx.bbs.common.CommonConstant;
import cn.edu.ztbu.zmx.bbs.domain.Post;
import cn.edu.ztbu.zmx.bbs.domain.User;
import cn.edu.ztbu.zmx.bbs.service.PostService;
import cn.edu.ztbu.zmx.bbs.util.JacksonUtil;
import cn.edu.ztbu.zmx.bbs.vo.PostQueryParamVo;
import cn.edu.ztbu.zmx.bbs.vo.PostVo;
import cn.edu.ztbu.zmx.bbs.vo.ResultVo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Request;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
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


    @RequestMapping(value = "save",method = RequestMethod.POST)
    public ResultVo save(PostVo postVo){
        log.info("保存帖子param:{}", JacksonUtil.toJsonString(postVo));
        if(Objects.isNull(postVo)){
            return ResultVo.fail("参数为空");
        }
        Post post = new Post();
        BeanUtils.copyProperties(postVo,post);
        return ResultVo.success(postService.save(post));
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
        Page<Post> page = postService.findByCategory(queryParamVo.getCategoryId(),queryParamVo.getPageNum(),queryParamVo.getPageSize());
        List<PostVo> voList = Lists.transform(page.getContent(),s->{
            PostVo vo = new PostVo();
            BeanUtils.copyProperties(s,vo);
            return vo;
        });
        Page<PostVo> voPage = new PageImpl<>(voList,page.getPageable(),page.getTotalElements());
        return ResultVo.success(voPage);
    }
}
