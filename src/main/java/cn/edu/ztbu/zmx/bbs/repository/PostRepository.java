package cn.edu.ztbu.zmx.bbs.repository;

import cn.edu.ztbu.zmx.bbs.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program bbs.PostRepository
 * @author: zhaomengxin
 * @date: 2020/11/14 21:06
 * @Description:
 */
@Repository
public interface PostRepository extends JpaRepository<Post,Long>, JpaSpecificationExecutor<Post> {
    /**
     *
     * @param title
     * @param nickName
     * @return
     */
    Page<Post> findAllByTitleOrNickName(String title,String nickName, Pageable pageable);

    /**
     *
     * @param id
     * @param yn
     * @return
     */
    Post getPostByIdAndYn(Long id,Boolean yn);

    /**
     *
     * @param id
     * @param nickName
     */
    @Transactional
    @Modifying
    @Query("update Post set nickName=:nickName where userId=:id")
    void updateByUserId(@Param(value = "id")Long id,@Param(value = "nickName")String nickName);

}
