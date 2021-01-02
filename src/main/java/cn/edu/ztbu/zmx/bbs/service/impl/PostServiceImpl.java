package cn.edu.ztbu.zmx.bbs.service.impl;

import cn.edu.ztbu.zmx.bbs.domain.Post;
import cn.edu.ztbu.zmx.bbs.repository.PostRepository;
import cn.edu.ztbu.zmx.bbs.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @program bbs.PostServiceImpl
 * @author: zhaomengxin
 * @date: 2020/11/28 20:57
 * @Description:
 */
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository repository;

    @Override
    public Page<Post> findByTitleOrAuthor(String title, Integer pageNum, Integer pageSize) {
        return repository.findAllByTitleOrNickName(title,title, PageRequest.of(pageNum,pageSize));
    }

    @Override
    public Page<Post> findByCategory(Long categoryId, Integer pageNum, Integer pageSize) {
        return repository.findAllByCategoryId(categoryId,PageRequest.of(pageNum,pageSize));
    }

    @Override
    public Post save(Post post) {
        return repository.save(post);
    }

}
