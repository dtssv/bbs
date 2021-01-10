package cn.edu.ztbu.zmx.bbs.web;

import cn.edu.ztbu.zmx.bbs.domain.Category;
import cn.edu.ztbu.zmx.bbs.service.CategoryService;
import cn.edu.ztbu.zmx.bbs.vo.CategoryVo;
import cn.edu.ztbu.zmx.bbs.vo.ResultVo;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program bbs.CategoryController
 * @author: zhaomengxin
 * @date: 2020/11/15 20:49
 * @Description:
 */
@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("findAll")
    public ResultVo<List<CategoryVo>> findAll(){
        List<Category> list = categoryService.findAll();
        List<CategoryVo> voList = Lists.newArrayList();
        if(!CollectionUtils.isEmpty(list)){
            voList = Lists.transform(list,s->{
                CategoryVo vo = new CategoryVo();
                BeanUtils.copyProperties(s,vo);
                return vo;
            });
        }
        return ResultVo.success(voList);
    }
}
