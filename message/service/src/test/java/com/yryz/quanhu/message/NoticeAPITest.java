package com.yryz.quanhu.message;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.message.notice.api.NoticeAPI;
import com.yryz.quanhu.message.notice.dto.NoticeAdminDto;
import com.yryz.quanhu.message.notice.entity.Notice;
import com.yryz.quanhu.message.notice.vo.NoticeAdminVo;
import com.yryz.quanhu.message.push.api.PushAPI;
import com.yryz.quanhu.message.push.entity.PushReqVo;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/1/19
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NoticeAPITest {

    @Autowired
    NoticeAPI noticeAPI;

    // /**
    //     * 添加公告
    //     *
    //     * @param notice
    //     * @return
    //     */
    //    Response<Integer> add(Notice notice);
    @Test
    public void add() {
        Notice notice = new Notice();
        notice.setKid(4L);
        notice.setContentPath("/");
        notice.setContent("xxx");
        notice.setNoticeStatus(0);
        notice.setAppId("123");
        notice.setAppName("app");
        notice.setNoticeDesc("xxx");
        notice.setNoticeType(0);
        notice.setPublishDate(new Date());
        notice.setSmallPic("/");
        notice.setTitle("gogogo");
        Response<Integer> response = noticeAPI.add(notice);
    }

    @Test
    public void update() {
        Notice notice = new Notice();
        notice.setKid(181607L);
        notice.setContentPath("ggg");
        notice.setContent("ggg");
        notice.setNoticeStatus(1);
        notice.setAppId("123");
        notice.setAppName("app");
        notice.setNoticeDesc("xxx");
        notice.setNoticeType(0);
        notice.setPublishDate(new Date());
        notice.setSmallPic("/");
        notice.setTitle("gogogo");
        Response<Integer> response = noticeAPI.update(notice);
    }

    @Test
    public void listAdmin() {
        NoticeAdminDto noticeAdminDto = new NoticeAdminDto();
        Response<PageList<NoticeAdminVo>> pageListResponse = noticeAPI.listAdmin(noticeAdminDto);
    }


    @Test
    public void detailAdmin() {
        NoticeAdminDto noticeAdminDto = new NoticeAdminDto();
        noticeAdminDto.setKid(181607L);
        Response<NoticeAdminVo> response = noticeAPI.detailAdmin(noticeAdminDto);
    }
}
