package cn.edu.ztbu.zmx.bbs.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @program bbs.UserController
 * @author zhaomengxin
 * @date 2020/10/17 15:27
 * @Description
 * @since 1.0
 */
@RestController
public class UserController {
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "test")
    public String test(){
        redisTemplate.opsForValue().set("test","hello");
        return redisTemplate.opsForValue().get("test").toString();
    }
}
