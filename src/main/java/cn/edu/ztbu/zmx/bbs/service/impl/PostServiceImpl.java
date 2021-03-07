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
import cn.edu.ztbu.zmx.bbs.vo.PostQueryParamVo;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
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
    public Page<Post> findByParam(PostQueryParamVo paramVo) {
        return repository.findAll((r,q,c)->{
            Path<Object> category = r.get("categoryId");
            List<Predicate> predicateList = Lists.newArrayList();
            if(!Objects.isNull(paramVo.getCategoryId())){
                Predicate predicate = c.equal(category.as(Long.class),paramVo.getCategoryId());
                predicateList.add(predicate);
            }
            Path<Object> categoryName = r.get("categoryName");
            if(!Strings.isNullOrEmpty(paramVo.getCategoryName())){
                Predicate predicate = c.like(categoryName.as(String.class),paramVo.getCategoryName());
                predicateList.add(predicate);
            }
            Path<Object> nickName = r.get("nickName");
            if(!Strings.isNullOrEmpty(paramVo.getNickName())){
                Predicate predicate = c.like(nickName.as(String.class),paramVo.getNickName());
                predicateList.add(predicate);
            }
            Path<Object> title = r.get("title");
            if(!Strings.isNullOrEmpty(paramVo.getTitle())){
                Predicate predicate = c.like(title.as(String.class),paramVo.getTitle());
                predicateList.add(predicate);
            }
            if(!Objects.isNull(paramVo.getUserId())){
                Path<Object> user = r.get("userId");
                Predicate predicate = c.equal(user.as(Long.class),paramVo.getUserId());
                predicateList.add(predicate);
            }
            Path<Object> yn = r.get("yn");
            Predicate ynPredicate = c.equal(yn.as(Boolean.class),Boolean.FALSE);
            predicateList.add(ynPredicate);
            Predicate[] pre = new Predicate[predicateList.size()];
            return q.where(predicateList.toArray(pre)).getRestriction();
        },PageRequest.of(paramVo.getPageNum(),paramVo.getPageSize()));
    }

    @Override
    public Post save(Post post) {
        User loginUser = LoginContext.getLoginUser();
        if(loginUser == null){
            throw new RuntimeException("登录用户为空");
        }
        Category category = categoryRepository.getByIdAndYn(post.getCategoryId(),Boolean.FALSE);
        if(post.getId() != null){
            Post postDb = repository.getPostByIdAndYn(post.getId(),Boolean.FALSE);
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

    @Override
    public Long todayData() {
        return repository.count((r,q,c)->{
            Path<Object> createTime = r.get("createTime");
            List<Predicate> predicateList = Lists.newArrayList();
            Predicate predicate = c.between(createTime.as(LocalDateTime.class),LocalDateTime.of(LocalDate.now(), LocalTime.of(00,00,00))
                    ,LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59)));
            predicateList.add(predicate);
            Path<Object> yn = r.get("yn");
            Predicate ynPredicate = c.equal(yn.as(Boolean.class),Boolean.FALSE);
            predicateList.add(ynPredicate);
            Predicate[] pre = new Predicate[predicateList.size()];
            return q.where(predicateList.toArray(pre)).getRestriction();
        });
    }


    @Override
    public String delete(Long id) {
        Post post = repository.getPostByIdAndYn(id,Boolean.FALSE);
        if(Objects.isNull(post) || post.getYn()){
            return "帖子不存在";
        }
        User loginUser = LoginContext.getLoginUser();
        post.setYn(Boolean.TRUE);
        post.setModifyTime(LocalDateTime.now());
        post.setModifier(loginUser.getUsername());
        repository.save(post);
        Category category = categoryRepository.getByIdAndYn(post.getCategoryId(),Boolean.FALSE);
        category.setPostNum(category.getPostNum() - CommonConstant.ONE);
        categoryRepository.save(category);
        loginUser.setPostNum(loginUser.getPostNum()  + CommonConstant.ONE);
        userRepository.save(loginUser);
        return "";
    }

    @Override
    public String cream(Long id) {
        Post post = repository.getPostByIdAndYn(id,Boolean.FALSE);
        if(Objects.isNull(post) || post.getYn()){
            return "帖子不存在";
        }
        post.setCream(Boolean.TRUE);
        post.setModifyTime(LocalDateTime.now());
        post.setModifier(LoginContext.getLoginUser().getUsername());
        repository.save(post);
        return "";
    }
}
