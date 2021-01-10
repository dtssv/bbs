package cn.edu.ztbu.zmx.bbs.service;

import cn.edu.ztbu.zmx.bbs.domain.Category;

import java.util.List;

/**
 * @program bbs.CategoryService
 * @author: zhaomengxin
 * @date: 2020/11/15 20:51
 * @Description:
 */
public interface CategoryService {

    List<Category> findAll();
}
