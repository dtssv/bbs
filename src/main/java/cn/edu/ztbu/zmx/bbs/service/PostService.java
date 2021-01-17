package cn.edu.ztbu.zmx.bbs.service;

import cn.edu.ztbu.zmx.bbs.domain.Post;
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
     * @param categoryId
     * @return
     */
    Page<Post> findByCategory(Long categoryId,Integer pageNum,Integer pageSize);

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
    Post findById(Long id);

}
