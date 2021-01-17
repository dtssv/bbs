package cn.edu.ztbu.zmx.bbs.repository;

import cn.edu.ztbu.zmx.bbs.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @program bbs.CommentRepository
 * @author: zhaomengxin
 * @date: 2020/11/14 21:05
 * @Description:
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment,Long>, JpaSpecificationExecutor<Comment> {

    @Query("select max(floorNum) from Comment where postId =:postId")
    Integer findMaxFloorByPostId(@Param("postId") Long postId);
}
