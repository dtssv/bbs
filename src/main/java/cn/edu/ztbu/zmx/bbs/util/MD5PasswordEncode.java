package cn.edu.ztbu.zmx.bbs.util;

import com.google.common.base.Strings;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

/**
 * @program bbs.MD5PasswordEncode
 * @author: zhaomengxin
 * @date: 2020/11/7 20:17
 * @Description:
 */
public class MD5PasswordEncode implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return !Strings.isNullOrEmpty(rawPassword.toString()) && rawPassword.equals(encodedPassword);
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return true;
    }
}
