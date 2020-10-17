package cn.edu.ztbu.zmx.bbs.repository;

import cn.edu.ztbu.zmx.bbs.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhaomengxin
 * @version 1.0
 * @program bbs.UserRepository
 * @date 2020/10/17 15:29
 * @Description
 * @since 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<Long, User> {

    User queryByUsername(String username);
}
