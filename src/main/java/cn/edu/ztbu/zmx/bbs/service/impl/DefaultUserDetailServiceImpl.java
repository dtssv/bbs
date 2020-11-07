package cn.edu.ztbu.zmx.bbs.service.impl;

import cn.edu.ztbu.zmx.bbs.domain.User;
import cn.edu.ztbu.zmx.bbs.repository.UserRepository;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class DefaultUserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.queryByUserName(s);
        if(user == null){
            return null;
        }
        org.springframework.security.core.userdetails.User userDetail =
                new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), Lists.newArrayList());
        return userDetail;
    }
}
