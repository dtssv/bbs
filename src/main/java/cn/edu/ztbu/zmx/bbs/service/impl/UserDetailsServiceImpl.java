package cn.edu.ztbu.zmx.bbs.service.impl;


import cn.edu.ztbu.zmx.bbs.domain.User;
import cn.edu.ztbu.zmx.bbs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
        Optional<User> user = repository.findOne((r,q,c)->c.or(c.equal(r.get("username"),username),
                c.equal(r.get("phone"),username),c.equal(r.get("email"),username)));
        return user.isPresent() ? user.get() : null;
    }
}
