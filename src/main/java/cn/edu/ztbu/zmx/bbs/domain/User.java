package cn.edu.ztbu.zmx.bbs.domain;

import cn.edu.ztbu.zmx.bbs.common.CommonConstant;
import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * @author zhaomengxin
 * @version 1.0
 * @program bbs.User
 * @date 2020/10/17 15:29
 * @Description
 * @since 1.0
 */
@Data
@Entity
@Table(name = "user")
public class User implements UserDetails {

    /**
     * id
     */
    @Id
    private Long id;

    /**
     * userName
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * password
     */
    private String password;

    /**
     * phone
     */
    @Column(name = "phone")
    private String phone;

    /**
     * email
     */
    @Column(name = "email")
    private String email;

    /**
     * userNameCh
     */
    private String nick_name;

    /**
     * registerTime
     */
    private LocalDateTime registerTime;

    /**
     * modifyTime
     */
    private LocalDateTime modifyTime;
    /**
     * admin
     */
    private Integer admin;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = Lists.newArrayList();
        if(admin != null && admin == CommonConstant.YES){
            roles.add(new SimpleGrantedAuthority(CommonConstant.ROLE_ADMIN));
        }
        return roles;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isAccountNonLocked() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isEnabled() {
        return Boolean.TRUE;
    }
}
