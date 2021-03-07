package cn.edu.ztbu.zmx.bbs.service.impl;

import cn.edu.ztbu.zmx.bbs.common.CommonConstant;
import cn.edu.ztbu.zmx.bbs.domain.Category;
import cn.edu.ztbu.zmx.bbs.domain.Moderator;
import cn.edu.ztbu.zmx.bbs.domain.User;
import cn.edu.ztbu.zmx.bbs.repository.*;
import cn.edu.ztbu.zmx.bbs.service.UserService;
import cn.edu.ztbu.zmx.bbs.util.LoginContext;
import cn.edu.ztbu.zmx.bbs.vo.ResultVo;
import cn.edu.ztbu.zmx.bbs.vo.UserQueryParamVo;
import cn.edu.ztbu.zmx.bbs.vo.UserRegisterVo;
import cn.edu.ztbu.zmx.bbs.vo.UserVo;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @program bbs.UserServiceImpl
 * @author: zhaomengxin
 * @date: 2021/1/2 20:55
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModeratorRepository moderatorRepository;

    @Override
    public ResultVo register(UserRegisterVo userRegisterVo) {
        User user = userRepository.findUserByUsername(userRegisterVo.getUsername());
        if(user != null){
            return ResultVo.fail("用户名已存在");
        }
        user = new User();
        BeanUtils.copyProperties(userRegisterVo,user);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setCreator(userRegisterVo.getUsername());
        user.setModifier(user.getCreator());
        user.setRegisterTime(LocalDateTime.now());
        user.setCreateTime(LocalDateTime.now());
        user.setModifyTime(user.getCreateTime());
        user.setAdmin(CommonConstant.NO);
        user.setStatus(CommonConstant.USER_STATUS_OK);
        user.setCommentNum(CommonConstant.ZERO);
        user.setPostNum(CommonConstant.ZERO);
        user.setSex(CommonConstant.SEX_DEFAULT);
        user.setHeadPhoto(CommonConstant.SexEnum.fromCode(user.getSex()).getHeadPhoto());
        user.setCity("");
        user.setSign("");
        userRepository.save(user);
        return ResultVo.success("");
    }

    @Override
    public User getById(Long id) {
        Optional<User> user = userRepository.findOne((r, q, c)->c.and(c.equal(r.get("id"),id),
                c.equal(r.get("yn"),0)));
        return user.isPresent() ? user.get() : null;
    }

    @Override
    public List<User> selectByIds(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)){
            return Lists.newArrayList();
        }
        return userRepository.findAll((r, q, c)->{
            List<Predicate> predicateList = Lists.newArrayList();
            Expression<Long> inId = r.<Long>get("id");
            predicateList.add(inId.in(ids));
            Path<Object> yn = r.get("yn");
            Predicate ynPredicate = c.equal(yn.as(Boolean.class),Boolean.FALSE);
            predicateList.add(ynPredicate);
            Predicate[] pre = new Predicate[predicateList.size()];
            return q.where(predicateList.toArray(pre)).getRestriction();
        });
    }

    @Override
    public User saveBasic(UserVo userVo) {
        User user = this.getById(userVo.getId());
        user.setEmail(userVo.getEmail());
        user.setNickName(userVo.getNickName());
        user.setSex(userVo.getSex());
        user.setCity(userVo.getCity());
        user.setSign(userVo.getSign());
        user.setModifyTime(LocalDateTime.now());
        user.setModifier(userVo.getUsername());
        postRepository.updateByUserId(user.getId(),user.getNickName());
        commentRepository.updateNickNameByUserId(user.getId(),user.getNickName());
        commentRepository.updateCommentNickNameByUserId(user.getId(),user.getNickName());
        return userRepository.save(user);
    }

    @Override
    public ResultVo<User> changePassword(String nowPass, String newPass, Long id, String userName) {
        User user = this.getById(id);
        if(nowPass.equals(user.getPassword()) || new BCryptPasswordEncoder().encode(nowPass).equals(user.getPassword())){
            if(new BCryptPasswordEncoder().encode(newPass).equals(user.getPassword())){
                return ResultVo.fail("新密码不能和当前密码相同");
            }
            user.setPassword(new BCryptPasswordEncoder().encode(newPass));
            user.setModifier(userName);
            user.setModifyTime(LocalDateTime.now());
            return ResultVo.success(userRepository.save(user));
        }else {
            return ResultVo.fail("密码错误");
        }
    }

    @Override
    public User changeHeadImage(String headImage, Long id, String userName) {
        User user = this.getById(id);
        user.setHeadPhoto(headImage);
        user.setModifyTime(LocalDateTime.now());
        user.setModifier(userName);
        return userRepository.save(user);
    }

    @Override
    public Long todayData() {
        return userRepository.count((r,q,c)->{
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
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Page<User> findByParam(UserQueryParamVo queryParamVo) {
        return userRepository.findAll((r,q,c)->{
            List<Predicate> predicateList = Lists.newArrayList();
            Path<Object> nickName = r.get("nickName");
            if(!Strings.isNullOrEmpty(queryParamVo.getNickName())){
                Predicate predicate = c.like(nickName.as(String.class),queryParamVo.getNickName());
                predicateList.add(predicate);
            }
            Path<Object> yn = r.get("yn");
            Predicate ynPredicate = c.equal(yn.as(Boolean.class),Boolean.FALSE);
            predicateList.add(ynPredicate);
            Predicate[] pre = new Predicate[predicateList.size()];
            return q.where(predicateList.toArray(pre)).getRestriction();
        }, PageRequest.of(queryParamVo.getPageNum(),queryParamVo.getPageSize()));
    }

    @Override
    public User changeStatus(Long id, Integer status) {
        User user = this.getById(id);
        if(user.getAdmin().equals(1)){
            return null;
        }
        user.setModifyTime(LocalDateTime.now());
        user.setModifier(LoginContext.getLoginUser().getUsername());
        user.setStatus(status);
        return userRepository.save(user);
    }

    @Override
    public Integer setModerator(Long id, Long categoryId) {
        Moderator moderator = moderatorRepository.getByUserIdAndYn(id,Boolean.FALSE);
        User loginUser = LoginContext.getLoginUser();
        if(Objects.isNull(categoryId) || categoryId.equals(0L)){
            if(Objects.nonNull(moderator)){
                moderator.setYn(CommonConstant.YnEnum.Y.getFlag());
                moderator.setModifier(loginUser.getUsername());
                moderator.setModifyTime(LocalDateTime.now());
                moderatorRepository.save(moderator);
                return 1;
            }
            return 0;
        }
        Category category = categoryRepository.getByIdAndYn(categoryId,Boolean.FALSE);
        if(Objects.isNull(category)){
            return CommonConstant.ZERO;
        }
        User user = this.getById(id);
        if(Objects.isNull(user)){
            return CommonConstant.ZERO;
        }
        if(Objects.isNull(moderator)){
            moderator = new Moderator();
            moderator.setCategoryId(id);
            moderator.setCategoryName(category.getCategoryName());
            moderator.setCreator(loginUser.getUsername());
            moderator.setCreateTime(LocalDateTime.now());
            moderator.setYn(CommonConstant.YnEnum.N.getFlag());
            moderator.setNickName(user.getNickName());
            moderator.setStatus(CommonConstant.YES);
        }else{
            moderator.setCategoryId(categoryId);
            moderator.setCategoryName(category.getCategoryName());
        }
        moderator.setModifier(loginUser.getUsername());
        moderator.setModifyTime(LocalDateTime.now());
        moderatorRepository.save(moderator);
        return 1;
    }
}
