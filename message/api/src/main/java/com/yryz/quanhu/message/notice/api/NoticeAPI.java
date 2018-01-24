package com.yryz.quanhu.message.notice.api;


import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.message.notice.dto.NoticeAdminDto;
import com.yryz.quanhu.message.notice.dto.NoticeDto;
import com.yryz.quanhu.message.notice.entity.Notice;
import com.yryz.quanhu.message.notice.vo.NoticeAdminVo;
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
     *
     * @param noticeDto
     * @return
     */
    Response<NoticeDetailVo> detail(NoticeDto noticeDto);

    /**
     * 添加公告
     *
     * @param notice
     * @return
     */
    Response<Integer> add(Notice notice);

    /**
     * 分页查询（admin）
     *
     * @param noticeAdminDto
     * @return
     */
    Response<PageList<NoticeAdminVo>> listAdmin(NoticeAdminDto noticeAdminDto);

    /**
     * 详情（admin）
     *
     * @param noticeAdminDto
     * @return
     */
    Response<NoticeAdminVo> detailAdmin(NoticeAdminDto noticeAdminDto);

    /**
     * 更新
     *
     * @param notice
     * @return
     */
    Response<Integer> update(Notice notice);
}
