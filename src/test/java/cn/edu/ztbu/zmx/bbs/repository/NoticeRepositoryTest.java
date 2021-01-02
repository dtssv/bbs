package cn.edu.ztbu.zmx.bbs.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @program bbs.NoticeRepositoryTest
 * @author: zhaomengxin
 * @date: 2020/11/28 21:24
 * @Description:
 */
@SpringBootTest
public class NoticeRepositoryTest {
    @Test
    void contextLoads() {
    }

    @Autowired
    private NoticeRepository repository;

    @Test
    public void testPage(){
        repository.findAllByStartTimeBeforeAndEndTimeAfter(new Date(),new Date());
    }
}
