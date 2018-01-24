package com.yryz.quanhu.message.notice.dao;

import com.yryz.common.dao.BaseDao;
import com.yryz.quanhu.message.notice.dto.NoticeAdminDto;
import com.yryz.quanhu.message.notice.dto.NoticeDto;
import com.yryz.quanhu.message.notice.entity.Notice;
import com.yryz.quanhu.message.notice.vo.NoticeAdminVo;
import com.yryz.quanhu.message.notice.vo.NoticeDetailVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author pengnian
 * @ClassName: NoticeDao
 * @Description: Notice数据访问接口
 * @date 2018-01-20 16:54:53
 */
@Mapper
public interface NoticeDao extends BaseDao {

    /**
     * 上线的公告
     *
     * @param noticeDto
     * @return
     */
    List<Notice> selectOnlineList(NoticeDto noticeDto);

    /**
     * 下线公告
     *
     * @param noticeDto
     * @return
     */
    List<Long> selectOfflineList(NoticeDto noticeDto);

    /**
     * 公告详情
     *
     * @param noticeDto
     * @return
     */
    NoticeDetailVo detail(NoticeDto noticeDto);

    /**
     * 添加
     *
     * @param notice
     * @return
     */
    Integer add(Notice notice);

    /**
     * 列表（admin）
     *
     * @param noticeAdminDto
     * @return
     */
    List<NoticeAdminVo> listAdmin(NoticeAdminDto noticeAdminDto);

    /**
     * 详情（admin）
     *
     * @param noticeAdminDto
     * @return
     */
    NoticeAdminVo detailAdmin(NoticeAdminDto noticeAdminDto);
}