package com.yryz.quanhu.message.notice.dao;

import com.yryz.common.dao.BaseDao;
import com.yryz.quanhu.message.notice.dto.NoticeDto;
import com.yryz.quanhu.message.notice.entity.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author pengnian
 * @ClassName: NoticeDao
 * @Description: Notice数据访问接口
 * @date 2018-01-20 16:54:53
 */
@Mapper
public interface NoticeDao extends BaseDao {

    List<Notice> selectList(NoticeDto noticeDto);

}