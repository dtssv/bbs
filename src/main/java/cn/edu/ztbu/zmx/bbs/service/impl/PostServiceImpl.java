package cn.edu.ztbu.zmx.bbs.service.impl;

import cn.edu.ztbu.zmx.bbs.common.CommonConstant;
import cn.edu.ztbu.zmx.bbs.domain.Category;
import cn.edu.ztbu.zmx.bbs.domain.Post;
import cn.edu.ztbu.zmx.bbs.domain.User;
import cn.edu.ztbu.zmx.bbs.repository.CategoryRepository;
import cn.edu.ztbu.zmx.bbs.repository.PostRepository;
import cn.edu.ztbu.zmx.bbs.repository.UserRepository;
import cn.edu.ztbu.zmx.bbs.service.PostService;
import cn.edu.ztbu.zmx.bbs.util.LoginContext;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<Post> findByTitleOrAuthor(String title, Integer pageNum, Integer pageSize) {
        return repository.findAllByTitleOrNickName(title,title, PageRequest.of(pageNum,pageSize));
    }

    @Override
    public Page<Post> findByCategory(Long categoryId, Integer pageNum, Integer pageSize) {

        return repository.findAll((r,q,c)->{
            Path<Object> category = r.get("categoryId");
            List<Predicate> predicateList = Lists.newArrayList();
            if(!Objects.isNull(categoryId)){
                Predicate predicate = c.equal(category.as(Long.class),categoryId);
                predicateList.add(predicate);
            }
            Path<Object> yn = r.get("yn");
            Predicate ynPredicate = c.equal(yn.as(Boolean.class),Boolean.FALSE);
            predicateList.add(ynPredicate);
            Predicate[] pre = new Predicate[predicateList.size()];
            return q.where(predicateList.toArray(pre)).getRestriction();
        },PageRequest.of(pageNum,pageSize));
    }

    @Override
    public Post save(Post post) {
        User loginUser = LoginContext.getLoginUser();
        if(loginUser == null){
            throw new RuntimeException("登录用户为空");
        }
        Category category = categoryRepository.getOne(post.getCategoryId());
        if(post.getId() != null){
            Post postDb = repository.getOne(post.getId());
            if(!postDb.getUserId().equals(loginUser.getId())){
                throw new RuntimeException("只能修改自己的帖子");
            }
            postDb.setTitle(post.getTitle());
            postDb.setPostBody(post.getPostBody());
            postDb.setModifyTime(LocalDateTime.now());
            postDb.setCategoryName(category.getCategoryName());
            post = postDb;
        }else{
            post.setCategoryName(category.getCategoryName());
            post.setCommentNum(CommonConstant.ZERO);
            post.setReadNum(CommonConstant.ZERO);
            post.setNickName(loginUser.getNickName());
            post.setUserId(loginUser.getId());
            post.setStatus(0);
            post.setCream(Boolean.FALSE);
            post.setCreateTime(LocalDateTime.now());
            post.setCreator(loginUser.getNickName());
            post.setModifyTime(post.getCreateTime());
            post.setModifier(post.getCreator());
            post.setYn(Boolean.FALSE);
            category.setLastPostTime(LocalDateTime.now());
            category.setPostNum(category.getPostNum()  + CommonConstant.ONE);
            categoryRepository.save(category);
            loginUser.setPostNum(loginUser.getPostNum()  + CommonConstant.ONE);
            userRepository.save(loginUser);
        }
        return repository.save(post);
    }

    @Override
    public Post findById(Integer type,Long id) {
        User loginUser = LoginContext.getLoginUser();
        Post post = repository.getPostByIdAndYn(id,Boolean.FALSE);
        if(post!=null && !CommonConstant.EDIT_TYPE.equals(type)){
            post.setReadNum(post.getReadNum() + CommonConstant.ONE);
            repository.save(post);
        }else if(post!=null){
            if(CommonConstant.EDIT_TYPE.equals(type) && (Objects.isNull(loginUser) || !loginUser.getId().equals(post.getUserId()))){
                return null;
            }
        }
        return post;
    }
}
