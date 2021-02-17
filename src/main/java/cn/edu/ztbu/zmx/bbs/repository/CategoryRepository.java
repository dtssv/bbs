package cn.edu.ztbu.zmx.bbs.repository;

import cn.edu.ztbu.zmx.bbs.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @program bbs.CategoryRepository
 * @author: zhaomengxin
 * @date: 2020/11/14 21:05
 * @Description:
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category,Long>, JpaSpecificationExecutor<Category> {
    /**
     *
     */
    Category getByIdAndYn(Long id,Boolean yn);
}
