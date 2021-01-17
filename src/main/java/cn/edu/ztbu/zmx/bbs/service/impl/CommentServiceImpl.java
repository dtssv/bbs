package cn.edu.ztbu.zmx.bbs.service.impl;

import cn.edu.ztbu.zmx.bbs.common.CommonConstant;
import cn.edu.ztbu.zmx.bbs.domain.Comment;
import cn.edu.ztbu.zmx.bbs.domain.Post;
import cn.edu.ztbu.zmx.bbs.domain.User;
import cn.edu.ztbu.zmx.bbs.repository.CommentRepository;
import cn.edu.ztbu.zmx.bbs.repository.PostRepository;
import cn.edu.ztbu.zmx.bbs.repository.UserRepository;
import cn.edu.ztbu.zmx.bbs.service.CommentService;
import cn.edu.ztbu.zmx.bbs.util.LoginContext;
import cn.edu.ztbu.zmx.bbs.vo.CommentVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @program bbs.CommentServiceImpl
 * @author: zhaomengxin
 * @date: 2021/1/17 15:18
 * @Description:
 */
@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository repository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Comment save(CommentVo vo) {
        User user = LoginContext.getLoginUser();
        Comment comment = new Comment();
        if (Objects.isNull(vo.getId())) {
            Post post = postRepository.getOne(vo.getPostId());
            if (Objects.isNull(post)) {
                throw new RuntimeException("回复的帖子不存在");
            }
            Integer commentCount = repository.findMaxFloorByPostId(vo.getPostId());
            if (Objects.isNull(commentCount)) {
                commentCount = CommonConstant.ZERO;
            }
            commentCount++;
            vo.setFloorNum(commentCount);
            BeanUtils.copyProperties(vo,comment);
            comment.setUserId(user.getId());
            comment.setNickName(user.getNickName());
            comment.setCreator(user.getNickName());
            comment.setCreateTime(LocalDateTime.now());
            comment.setModifyTime(comment.getCreateTime());
            comment.setModifier(comment.getCreator());
            comment.setYn(CommonConstant.YnEnum.N.getFlag());
            if(Objects.nonNull(comment.getCommentUserId())){
                User commentUser = userRepository.getOne(comment.getCommentUserId());
                if(Objects.nonNull(commentUser)){
                    comment.setCommentBody(commentUser.getNickName());
                }else{
                    comment.setCommentUserId(null);
                }
            }
            if(Objects.nonNull(vo.getReplyId())) {
                Comment reply = repository.getOne(vo.getReplyId());
                if(Objects.nonNull(reply)){
                    comment.setCommentUserId(reply.getUserId());
                    comment.setCommentNickName(reply.getNickName());
                }
            }
            post.setCommentNum(post.getCommentNum() + CommonConstant.ONE);
            post.setLastCommentTime(LocalDateTime.now());
            postRepository.save(post);
            user.setCommentNum(user.getCommentNum() + CommonConstant.ONE);
            userRepository.save(user);
        }else{
            comment = repository.getOne(vo.getId());
            if(!user.getId().equals(comment.getUserId())){
                throw new RuntimeException("只能编辑自己的回帖");
            }
            comment.setCommentBody(vo.getCommentBody());
            comment.setModifier(user.getNickName());
            comment.setModifyTime(LocalDateTime.now());
        }
        return repository.save(comment);
    }

    @Override
    public Page<CommentVo> pageByPostId(Long postId,Integer pageNum,Integer pageSize) {
        Page<Comment> page =  repository.findAll((r,q,c)->{
            Path<Object> post = r.get("postId");
            List<Predicate> predicateList = Lists.newArrayList();
            if(!Objects.isNull(postId)){
                Predicate predicate = c.equal(post.as(Long.class),postId);
                predicateList.add(predicate);
            }
            Path<Object> yn = r.get("yn");
            Predicate ynPredicate = c.equal(yn.as(Boolean.class),Boolean.FALSE);
            predicateList.add(ynPredicate);
            Predicate[] pre = new Predicate[predicateList.size()];
            return q.where(predicateList.toArray(pre)).getRestriction();
        }, PageRequest.of(pageNum,pageSize));
        User user = LoginContext.getLoginUser();
        List<User> authors = userRepository.findAllById(Lists.transform(page.getContent(),Comment::getUserId));
        Map<Long,User> authorMap = Maps.uniqueIndex(authors,User::getId);
        Post post = postRepository.getOne(postId);
        List<CommentVo> voList = Lists.transform(page.getContent(),s->{
            CommentVo vo = new CommentVo();
            BeanUtils.copyProperties(s,vo);
            User author = authorMap.get(s.getUserId());
            vo.setHeadUrl(author.getHeadPhoto());
            vo.setSex(author.getSex());
            if(Objects.nonNull(user) && user.getId().equals(vo.getUserId())){
                vo.setCanEdit(Boolean.TRUE);
            }else{
                vo.setCanEdit(Boolean.FALSE);
            }
            if(post.getUserId().equals(vo.getUserId())){
                vo.setAuthor(Boolean.TRUE);
            }
            return vo;
        });
        Page<CommentVo> voPage = new PageImpl<>(voList,page.getPageable(),page.getTotalElements());
        return voPage;
    }

    @Override
    public Integer delete(Long id) {
        Comment comment = repository.getOne(id);
        if(Objects.isNull(comment)){
            throw new RuntimeException("删除的回帖不存在");
        }
        User loginUser = LoginContext.getLoginUser();
        if(!loginUser.getId().equals(comment.getUserId())){
            throw new RuntimeException("只能删除自己的回帖");
        }
        comment.setYn(CommonConstant.YnEnum.Y.getFlag());
        repository.save(comment);
        return CommonConstant.ONE;
    }

    @Override
    public Comment findById(Long id) {
        return repository.getOne(id);
    }
}
