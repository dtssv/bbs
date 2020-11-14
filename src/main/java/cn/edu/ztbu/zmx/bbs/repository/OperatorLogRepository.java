package cn.edu.ztbu.zmx.bbs.repository;

import cn.edu.ztbu.zmx.bbs.domain.OperatorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @program bbs.OperatorLogRepository
 * @author: zhaomengxin
 * @date: 2020/11/14 21:06
 * @Description:
 */
@Repository
public interface OperatorLogRepository extends JpaRepository<OperatorLog,Long>, JpaSpecificationExecutor<OperatorLog> {
}
