package cn.edu.ztbu.zmx.bbs.service.impl;

import cn.edu.ztbu.zmx.bbs.common.CommonConstant;
import cn.edu.ztbu.zmx.bbs.domain.User;
import cn.edu.ztbu.zmx.bbs.repository.UserRepository;
import cn.edu.ztbu.zmx.bbs.service.UserService;
import cn.edu.ztbu.zmx.bbs.vo.ResultVo;
import cn.edu.ztbu.zmx.bbs.vo.UserRegisterVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    @Override
    public ResultVo register(UserRegisterVo userRegisterVo) {
        User user = userRepository.findUserByUsername(userRegisterVo.getUsername());
        if(user != null){
            return ResultVo.fail("用户名已存在");
        }
        user = new User();
        BeanUtils.copyProperties(userRegisterVo,user);
        user.setCreator(userRegisterVo.getUsername());
        user.setModifier(user.getCreator());
        user.setRegisterTime(LocalDateTime.now());
        user.setCreateTime(LocalDateTime.now());
        user.setModifyTime(user.getCreateTime());
        user.setAdmin(CommonConstant.NO);
        user.setStatus(CommonConstant.USER_STATUS_OK);
        user.setCommentNum(CommonConstant.ZERO);
        user.setPostNum(CommonConstant.ZERO);
        userRepository.save(user);
        return ResultVo.success("");
    }
}
