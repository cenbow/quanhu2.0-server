package com.yryz.quanhu.message.notice.api;


import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.message.notice.dto.NoticeDto;
import com.yryz.quanhu.message.notice.entity.Notice;
import com.yryz.quanhu.message.notice.vo.NoticeDetailVo;
import com.yryz.quanhu.message.notice.vo.NoticeVo;

/**
 * @author pengnian
 * @ClassName: NoticeAPI
 * @Description: NoticeApi接口
 * @date 2018-01-20 16:54:53
 */
public interface NoticeAPI {

    /**
     * 获取Notice列表
     *
     * @param noticeDto
     * @return
     */
    Response<NoticeVo> list(NoticeDto noticeDto);

    /**
     * 获取Notice详情
     * @param noticeDto
     * @return
     */
    Response<NoticeDetailVo> detail(NoticeDto noticeDto);
}
