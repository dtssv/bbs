package cn.edu.ztbu.zmx.bbs.service;

import cn.edu.ztbu.zmx.bbs.domain.Comment;
import cn.edu.ztbu.zmx.bbs.vo.CommentVo;
import org.springframework.data.domain.Page;

/**
 * @program bbs.CommentService
 * @author: zhaomengxin
 * @date: 2020/11/15 20:52
 * @Description:
 */
public interface CommentService {
    /**
     *
     * @param vo
     * @return
     */
    Comment save(CommentVo vo);

    /**
     *
     * @param postId
     * @return
     */
    Page<CommentVo> pageByPostId(Long postId,Integer pageNum,Integer pageSize);

    /**
     *
     * @param id
     * @return
     */
    Integer delete(Long id);
    /**
     *
     */
    Comment findById(Long id);
}
