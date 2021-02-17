package cn.edu.ztbu.zmx.bbs.service;

import cn.edu.ztbu.zmx.bbs.domain.Post;
import cn.edu.ztbu.zmx.bbs.vo.PostQueryParamVo;
import org.springframework.data.domain.Page;

/**
 * @program bbs.PostService
 * @author: zhaomengxin
 * @date: 2020/11/15 20:51
 * @Description:
 */
public interface PostService {
    /**
     *
     * @param title
     * @return
     */
    Page<Post> findByTitleOrAuthor(String title,Integer pageNum,Integer pageSize);

    /**
     *
     * @param paramVo
     * @return
     */
    Page<Post> findByParam(PostQueryParamVo paramVo);

    /**
     *
     * @param post
     * @return
     */
    Post save(Post post);

    /**
     *
     * @param id
     * @return
     */
    Post findById(Integer type,Long id);

    /**
     *
     * @return
     */
    Long todayData();

}
