package  com.yryz.quanhu.order.grow.service;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.yryz.quanhu.order.grow.entity.GrowStatus;

/**
 * Created by lsn on 2017/8/28.
 */
public interface GrowStatusService {
	
	Long save(GrowStatus ss);

    int update(GrowStatus ss);

    GrowStatus getById(@Param("userId") String userId , @Param("appId") String appId , @Param("id") Long id);

    GrowStatus getByCode(@Param("userId") String userId , @Param("createTime") Date createTime , @Param("eventCode") String eventCode);
    

}
