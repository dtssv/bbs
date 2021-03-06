package cn.edu.ztbu.zmx.bbs.domain;

import cn.edu.ztbu.zmx.bbs.common.CommonConstant;
import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**csdn
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * userName
     */
    @Column(name = "user_name")
    private String username;

    /**
     * password
     */
    @Column(name = "password")
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
    @Column(name = "nick_name")
    private String nickName;

    /**
     * registerTime
     */
    @Column(name = "register_time")
    private LocalDateTime registerTime;
    /**
     *
     */
    @Column(name = "post_num")
    private Integer postNum;
    /**
     *
     */
    @Column(name = "comment_num")
    private Integer commentNum;
    /**
     *
     */
    @Column(name = "status")
    private Integer status;
    /**
     *
     */
    @Column(name = "yn")
    private Boolean yn = Boolean.FALSE;
    /**
     *
     */
    @Column(name = "creator")
    private String creator;
    /**
     *
     */
    @Column(name = "create_time")
    private LocalDateTime createTime;
    /**
     * modifyTime
     */
    @Column(name = "modify_time")
    private LocalDateTime modifyTime;
    /**
     *
     */
    @Column(name = "modifier")
    private String modifier;
    /**
     * admin
     */
    private Integer admin;

    @Column(name = "head_photo")
    private String headPhoto;

    private Integer sex;

    private String city;

    private String sign;

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
        return this.username;
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
        return status != null && !status.equals(CommonConstant.USER_STATUS_CANNOT_LOGIN);
    }

    @Override
    public String getPassword(){
        return this.password;
    }
}
