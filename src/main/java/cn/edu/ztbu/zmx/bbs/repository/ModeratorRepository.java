package cn.edu.ztbu.zmx.bbs.repository;

import cn.edu.ztbu.zmx.bbs.domain.Moderator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @program bbs.ModeratorRepository
 * @author: zhaomengxin
 * @date: 2020/11/14 21:05
 * @Description:
 */
@Repository
public interface ModeratorRepository extends JpaRepository<Moderator,Long>, JpaSpecificationExecutor<Moderator> {

    Moderator getByUserIdAndYn(Long userId,Boolean yn);
}
