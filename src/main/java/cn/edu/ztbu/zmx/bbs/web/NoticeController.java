package cn.edu.ztbu.zmx.bbs.web;

import cn.edu.ztbu.zmx.bbs.common.CommonConstant;
import cn.edu.ztbu.zmx.bbs.domain.Notice;
import cn.edu.ztbu.zmx.bbs.service.NoticeService;
import cn.edu.ztbu.zmx.bbs.util.JacksonUtil;
import cn.edu.ztbu.zmx.bbs.vo.NoticeQueryParamVo;
import cn.edu.ztbu.zmx.bbs.vo.NoticeVo;
import cn.edu.ztbu.zmx.bbs.vo.PostVo;
import cn.edu.ztbu.zmx.bbs.vo.ResultVo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * @program bbs.NoticeController
 * @author: zhaomengxin
 * @date: 2020/11/15 20:49
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @RequestMapping(value = "pageNotice")
    public ResultVo<Page<NoticeVo>> pageNotice(NoticeQueryParamVo queryParamVo){
        log.info("公告查询参数：{}", JacksonUtil.toJsonString(queryParamVo));
        if(Objects.isNull(queryParamVo.getPageNum())){
            queryParamVo.setPageNum(CommonConstant.ZERO);
        }
        if(Objects.isNull(queryParamVo.getPageSize())){
            queryParamVo.setPageSize(CommonConstant.DEFAULT_PAGE_SIZE);
        }
        Page<Notice> page = noticeService.pageAll(queryParamVo);
        List<NoticeVo> voList = Lists.transform(page.getContent(),s->{
            NoticeVo vo = new NoticeVo();
            BeanUtils.copyProperties(s,vo);
            return vo;
        });
        Page<NoticeVo> voPage = new PageImpl<>(voList,page.getPageable(),page.getTotalElements());
        return ResultVo.success(voPage);
    }
}
