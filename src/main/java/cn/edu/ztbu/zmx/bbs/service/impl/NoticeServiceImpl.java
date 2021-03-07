package cn.edu.ztbu.zmx.bbs.service.impl;

import cn.edu.ztbu.zmx.bbs.common.CommonConstant;
import cn.edu.ztbu.zmx.bbs.domain.Notice;
import cn.edu.ztbu.zmx.bbs.domain.User;
import cn.edu.ztbu.zmx.bbs.repository.NoticeRepository;
import cn.edu.ztbu.zmx.bbs.service.NoticeService;
import cn.edu.ztbu.zmx.bbs.util.DateUtil;
import cn.edu.ztbu.zmx.bbs.util.LoginContext;
import cn.edu.ztbu.zmx.bbs.vo.NoticeQueryParamVo;
import cn.edu.ztbu.zmx.bbs.vo.NoticeVo;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @program bbs.NoticeServiceImpl
 * @author: zhaomengxin
 * @date: 2020/11/28 21:19
 * @Description:
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeRepository repository;
    @Override
    public List<Notice> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Notice> pageAll(NoticeQueryParamVo queryParamVo) {
        return repository.findAll((r,q,c)->{
            List<Predicate> predicateList = Lists.newArrayList();
            if(!Objects.isNull(queryParamVo.getNow())){
                Path<Object> startTime = r.get("startTime");
                Predicate predicate = c.lessThan(startTime.as(Date.class),Date.from(queryParamVo.getNow().atZone(ZoneId.systemDefault()).toInstant()));
                predicateList.add(predicate);
                Path<Object> endTime = r.get("endTime");
                Predicate endPredicate = c.greaterThan(endTime.as(Date.class),Date.from(queryParamVo.getNow().atZone(ZoneId.systemDefault()).toInstant()));
                predicateList.add(endPredicate);
            }
            if(Objects.nonNull(queryParamVo.getId())){
                Path<Object> id = r.get("id");
                Predicate predicate = c.equal(id.as(Long.class),queryParamVo.getId());
                predicateList.add(predicate);
            }
            Path<Object> yn = r.get("yn");
            Predicate ynPredicate = c.equal(yn.as(Boolean.class),Boolean.FALSE);
            predicateList.add(ynPredicate);
            Predicate[] pre = new Predicate[predicateList.size()];
            return q.where(predicateList.toArray(pre)).getRestriction();
        }, PageRequest.of(queryParamVo.getPageNum(),queryParamVo.getPageSize()));
    }

    @Override
    public Notice save(NoticeVo vo) {
        User user = LoginContext.getLoginUser();
        Notice notice = new Notice();
        if(Objects.nonNull(vo.getId())){
            notice = repository.getOne(vo.getId());
            if(Objects.isNull(notice)){
                return null;
            }
        }else{
            notice.setCreateTime(LocalDateTime.now());
            notice.setCreator(user.getUsername());
            notice.setYn(CommonConstant.YnEnum.N.getFlag());
            notice.setStatus(CommonConstant.ZERO);
        }
        notice.setNoticeBody(vo.getNoticeBody());
        notice.setLinkUrl(vo.getLinkUrl());
        notice.setStartTime(LocalDateTime.ofInstant(DateUtil.parse(vo.getStartTime()).toInstant(),ZoneId.systemDefault()));
        notice.setEndTime(LocalDateTime.ofInstant(DateUtil.parse(vo.getEndTime()).toInstant(),ZoneId.systemDefault()));
        notice.setModifier(user.getUsername());
        notice.setModifyTime(LocalDateTime.now());
        return repository.save(notice);
    }

    @Override
    public Integer delete(Long id) {
        Notice notice = repository.getOne(id);
        if(Objects.isNull(notice)){
            throw new RuntimeException("删除的公告不存在");
        }
        notice.setModifier(LoginContext.getLoginUser().getUsername());
        notice.setModifyTime(LocalDateTime.now());
        notice.setYn(CommonConstant.YnEnum.Y.getFlag());
        repository.save(notice);
        return CommonConstant.ONE;
    }
}
