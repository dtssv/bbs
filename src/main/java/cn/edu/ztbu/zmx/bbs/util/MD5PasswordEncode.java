package cn.edu.ztbu.zmx.bbs.util;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @program bbs.MD5PasswordEncode
 * @author: zhaomengxin
 * @date: 2020/11/7 20:17
 * @Description:
 */
public class MD5PasswordEncode implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return Boolean.TRUE;
    }
}
