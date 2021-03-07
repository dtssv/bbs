package cn.edu.ztbu.zmx.bbs.service.impl;


import cn.edu.ztbu.zmx.bbs.common.CommonConstant;
import cn.edu.ztbu.zmx.bbs.domain.User;
import cn.edu.ztbu.zmx.bbs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;

/**
 * @program bbs.UserDetailService
 * @author: zhaomengxin
 * @date: 2020/11/7 20:14
 * @Description:
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = repository.findOne((r,q,c)->c.or(c.equal(r.get("username"),username),
                c.equal(r.get("phone"),username),c.equal(r.get("email"),username)));
        User user = optionalUser.isPresent() ? optionalUser.get() : null;
        if(Objects.isNull(user)){
            throw new BadCredentialsException("帐号不存在或密码错误");
        }
        if(user.getStatus().equals(CommonConstant.USER_STATUS_CANNOT_LOGIN)){
            throw new BadCredentialsException("帐号禁止登录，请联系管理员");
        }
        if(user.getStatus().equals(CommonConstant.USER_STATUS_DISABLE)){
            throw new BadCredentialsException("帐号已被禁用，请联系管理员");
        }
        return optionalUser.isPresent() ? optionalUser.get() : null;
    }
}
