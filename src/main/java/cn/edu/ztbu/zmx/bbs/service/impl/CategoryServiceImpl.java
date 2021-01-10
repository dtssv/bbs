package cn.edu.ztbu.zmx.bbs.service.impl;

import cn.edu.ztbu.zmx.bbs.domain.Category;
import cn.edu.ztbu.zmx.bbs.repository.CategoryRepository;
import cn.edu.ztbu.zmx.bbs.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program bbs.CategoryServiceImpl
 * @author: zhaomengxin
 * @date: 2021/1/10 20:13
 * @Description:
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Override
    public List<Category> findAll() {
        return repository.findAll(Sort.by("orderNum"));
    }
}
