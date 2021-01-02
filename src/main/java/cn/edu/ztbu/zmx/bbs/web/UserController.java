package cn.edu.ztbu.zmx.bbs.web;

import cn.edu.ztbu.zmx.bbs.common.CommonConstant;
import cn.edu.ztbu.zmx.bbs.service.UserService;
import cn.edu.ztbu.zmx.bbs.vo.ResultVo;
import cn.edu.ztbu.zmx.bbs.vo.UserRegisterVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @version 1.0
 * @program bbs.UserController
 * @author zhaomengxin
 * @date 2020/10/17 15:27
 * @Description
 * @since 1.0
 */
@Slf4j
@RequestMapping(value = "user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    protected AuthenticationManager authenticationManager;

    @RequestMapping(value = "register")
    public ResultVo register(UserRegisterVo registerVo, HttpServletRequest request){
        ResultVo resultVo =  userService.register(registerVo);
        if(resultVo.isSuccess()){
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(registerVo.getUsername(), registerVo.getPassword());
            try{
                token.setDetails(new WebAuthenticationDetails(request));
                Authentication authenticatedUser = authenticationManager.authenticate(token);
                SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
                request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
            } catch( AuthenticationException e ){
                log.error("用户注册后登陆失败，e:{}",e);
                resultVo.setData(CommonConstant.NO);
            }
        }
        return resultVo;
    }
}
