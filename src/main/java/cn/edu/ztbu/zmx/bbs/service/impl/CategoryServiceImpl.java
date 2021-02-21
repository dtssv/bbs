package cn.edu.ztbu.zmx.bbs.service.impl;

import cn.edu.ztbu.zmx.bbs.common.CommonConstant;
import cn.edu.ztbu.zmx.bbs.domain.Category;
import cn.edu.ztbu.zmx.bbs.domain.User;
import cn.edu.ztbu.zmx.bbs.repository.CategoryRepository;
import cn.edu.ztbu.zmx.bbs.service.CategoryService;
import cn.edu.ztbu.zmx.bbs.util.LoginContext;
import cn.edu.ztbu.zmx.bbs.vo.CategoryQueryParamVo;
import cn.edu.ztbu.zmx.bbs.vo.CategoryVo;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
        return repository.findAll((r,q,c)->{
            List<Predicate> predicateList = Lists.newArrayList();
            Path<Object> yn = r.get("yn");
            Predicate ynPredicate = c.equal(yn.as(Boolean.class),Boolean.FALSE);
            predicateList.add(ynPredicate);
            Predicate[] pre = new Predicate[predicateList.size()];
            return q.where(predicateList.toArray(pre)).getRestriction();
        },Sort.by("orderNum"));
    }

    @Override
    public Page<Category> pageByParam(CategoryQueryParamVo vo) {
        return repository.findAll((r,q,c)->{
            List<Predicate> predicateList = Lists.newArrayList();
            if(!Strings.isNullOrEmpty(vo.getCategoryName())){
                Path<Object> categoryName = r.get("categoryName");
                Predicate categoryNamePredicate = c.like(categoryName.as(String.class),vo.getCategoryName());
                predicateList.add(categoryNamePredicate);
            }
            Path<Object> yn = r.get("yn");
            Predicate ynPredicate = c.equal(yn.as(Boolean.class),Boolean.FALSE);
            predicateList.add(ynPredicate);
            Predicate[] pre = new Predicate[predicateList.size()];
            return q.where(predicateList.toArray(pre)).getRestriction();
        }, PageRequest.of(vo.getPageNum(),vo.getPageSize(),Sort.by("orderNum")));
    }

    @Override
    public String delete(Long id) {
        Category category = repository.getByIdAndYn(id,Boolean.FALSE);
        if(Objects.isNull(category) || category.getYn()){
            return "板块不存在";
        }
        if(category.getPostNum() > 0){
            return  "当前板块已存在帖子";
        }
        category.setYn(Boolean.TRUE);
        category.setModifyTime(LocalDateTime.now());
        category.setModifier(LoginContext.getLoginUser().getUsername());
        repository.save(category);
        return "";
    }

    @Override
    public Category save(CategoryVo vo, User user) {
        Category category = new Category();
        if(Objects.nonNull(vo.getId())){
            category = repository.getByIdAndYn(vo.getId(),Boolean.FALSE);
            if(Objects.isNull(category)){
                return null;
            }
        }else{
            category.setYn(Boolean.FALSE);
            category.setCreateTime(LocalDateTime.now());
            category.setCreator(user.getUsername());
            category.setPostNum(CommonConstant.ZERO);
            category.setModeratorNum(CommonConstant.ZERO);
        }
        category.setModifier(user.getUsername());
        category.setModifyTime(LocalDateTime.now());
        category.setOrderNum(vo.getOrderNum());
        category.setCategoryName(vo.getCategoryName());
        return repository.save(category);
    }
}
