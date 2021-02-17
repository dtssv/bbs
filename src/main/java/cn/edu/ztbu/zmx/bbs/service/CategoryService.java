package cn.edu.ztbu.zmx.bbs.service;

import cn.edu.ztbu.zmx.bbs.domain.Category;
import cn.edu.ztbu.zmx.bbs.domain.User;
import cn.edu.ztbu.zmx.bbs.vo.CategoryQueryParamVo;
import cn.edu.ztbu.zmx.bbs.vo.CategoryVo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @program bbs.CategoryService
 * @author: zhaomengxin
 * @date: 2020/11/15 20:51
 * @Description:
 */
public interface CategoryService {
    /**
     *
     * @return
     */
    List<Category> findAll();

    /**
     *
     * @param vo
     * @return
     */
    Page<Category> pageByParam(CategoryQueryParamVo vo);
    /**
     *
     */
    String delete(Long id);
    /**
     *
     */
    Category save(CategoryVo vo, User user);
}
