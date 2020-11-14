package cn.edu.ztbu.zmx.bbs.repository;

import cn.edu.ztbu.zmx.bbs.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @program bbs.MoticeRepository
 * @author: zhaomengxin
 * @date: 2020/11/14 21:05
 * @Description:
 */
@Repository
public interface NoticeRepository extends JpaRepository<Notice,Long>, JpaSpecificationExecutor<Notice> {
}
