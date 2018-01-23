package com.yryz.quanhu.message.notice.api;


import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.message.notice.dto.NoticeDto;
import com.yryz.quanhu.message.notice.entity.Notice;
import com.yryz.quanhu.message.notice.vo.NoticeVo;

/**
 * @author pengnian
 * @ClassName: NoticeAPI
 * @Description: NoticeApi接口
 * @date 2018-01-20 16:54:53
 */
public interface NoticeAPI {

    /**
     * 获取Notice明细
     *
     * @param id
     * @return
     */
    Response<Notice> get(Long id);

    /**
     * 获取Notice明细
     *
     * @param id
     * @return
     */
    Response<NoticeVo> detail(Long id);

    /**
     * 获取Notice列表
     *
     * @param noticeDto
     * @return
     */
    Response<PageList<NoticeVo>> list(NoticeDto noticeDto);

}
