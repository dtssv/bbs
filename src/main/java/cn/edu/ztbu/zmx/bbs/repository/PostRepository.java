package cn.edu.ztbu.zmx.bbs.repository;

import cn.edu.ztbu.zmx.bbs.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @program bbs.PostRepository
 * @author: zhaomengxin
 * @date: 2020/11/14 21:06
 * @Description:
 */
@Repository
public interface PostRepository extends JpaRepository<Post,Long>, JpaSpecificationExecutor<Post> {


}
