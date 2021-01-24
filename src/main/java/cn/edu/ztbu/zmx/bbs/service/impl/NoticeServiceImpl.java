package cn.edu.ztbu.zmx.bbs.service.impl;

import cn.edu.ztbu.zmx.bbs.domain.Notice;
import cn.edu.ztbu.zmx.bbs.repository.NoticeRepository;
import cn.edu.ztbu.zmx.bbs.service.NoticeService;
import cn.edu.ztbu.zmx.bbs.vo.NoticeQueryParamVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return repository.findAll(PageRequest.of(queryParamVo.getPageNum(),queryParamVo.getPageSize()));
    }

    @Override
    public Notice save(Notice notice) {
        return repository.save(notice);
    }
}
