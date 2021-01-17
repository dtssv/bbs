package cn.edu.ztbu.zmx.bbs.web;

import cn.edu.ztbu.zmx.bbs.common.CommonConstant;
import cn.edu.ztbu.zmx.bbs.domain.Comment;
import cn.edu.ztbu.zmx.bbs.domain.User;
import cn.edu.ztbu.zmx.bbs.service.CommentService;
import cn.edu.ztbu.zmx.bbs.service.UserService;
import cn.edu.ztbu.zmx.bbs.util.JacksonUtil;
import cn.edu.ztbu.zmx.bbs.util.LoginContext;
import cn.edu.ztbu.zmx.bbs.vo.CommentQueryParamVo;
import cn.edu.ztbu.zmx.bbs.vo.CommentVo;
import cn.edu.ztbu.zmx.bbs.vo.ResultVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @program bbs.CommentController
 * @author: zhaomengxin
 * @date: 2020/11/15 20:50
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("comment")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @RequestMapping(value = "reply")
    public ResultVo reply(CommentVo commentVo){
        log.info("新增回复请求参数:{}", JacksonUtil.toJsonString(commentVo));
        if(Objects.isNull(commentVo.getPostId())){
            return ResultVo.fail("回复的帖子不存在");
        }
        try {
            return ResultVo.success(commentService.save(commentVo));
        } catch (RuntimeException e) {
            return ResultVo.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "pageComment")
    public ResultVo<Page<CommentVo>> pageComment(CommentQueryParamVo queryParamVo){
        log.info("回复查询参数：{}",JacksonUtil.toJsonString(queryParamVo));
        if(Objects.isNull(queryParamVo.getPageNum())){
            queryParamVo.setPageNum(CommonConstant.ZERO);
        }
        if(Objects.isNull(queryParamVo.getPageSize())){
            queryParamVo.setPageSize(CommonConstant.DEFAULT_PAGE_SIZE);
        }
        if(Objects.isNull(queryParamVo.getPostId())){
            return ResultVo.fail("帖子已被删除或不存在");
        }
        Page<CommentVo> page = commentService.pageByPostId(queryParamVo.getPostId(),queryParamVo.getPageNum(),queryParamVo.getPageSize());
        return ResultVo.success(page);
    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "delete")
    public ResultVo delete(Long id){
        log.info("回复删除参数：{}",id);
        try {
            return ResultVo.success(commentService.delete(id));
        } catch (RuntimeException e) {
            return ResultVo.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "detail")
    public ResultVo<CommentVo> detail(Long id){
        log.info("回复查询参数：{}",id);
        Comment comment = commentService.findById(id);
        if(Objects.nonNull(comment)){
            CommentVo commentVo = new CommentVo();
            BeanUtils.copyProperties(comment,commentVo);
            return ResultVo.success(commentVo);
        }
        return ResultVo.fail("回复不存在或已经被删除");
    }
}
