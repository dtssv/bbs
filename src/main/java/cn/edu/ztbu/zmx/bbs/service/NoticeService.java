package cn.edu.ztbu.zmx.bbs.service;

import cn.edu.ztbu.zmx.bbs.domain.Notice;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @program bbs.NoticeService
 * @author: zhaomengxin
 * @date: 2020/11/15 20:52
 * @Description:
 */
public interface NoticeService {
    /**
     *
     * @return
     */
    List<Notice> findAll();

    /**
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<Notice> pageAll(Integer pageNum,Integer pageSize);

    /**
     *
     * @param notice
     * @return
     */
    Notice save(Notice notice);
}
