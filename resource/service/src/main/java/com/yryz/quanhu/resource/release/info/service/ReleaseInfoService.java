package com.yryz.quanhu.resource.release.info.service;

import java.util.List;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.resource.release.config.vo.ReleaseConfigVo;
import com.yryz.quanhu.resource.release.info.dto.ReleaseInfoDto;
import com.yryz.quanhu.resource.release.info.entity.ReleaseInfo;
import com.yryz.quanhu.resource.release.info.vo.ReleaseInfoVo;

/**
* @author wangheng
* @date 2018年1月22日 下午8:16:28
*/
public interface ReleaseInfoService {

    /**  
    * @Description: 插入记录
    * @author wangheng
    * @param @param record
    * @param @return
    * @return int
    * @throws  
    */
    public int insertSelective(ReleaseInfo record);

    /**  
    * @Description: 唯一ID 查询
    * @author wangheng
    * @param @param kid
    * @param @return
    * @return ReleaseInfoVo
    * @throws  
    */
    public ReleaseInfoVo selectByKid(Long kid);

    /**  
    * @Description: 条件分页查询列表
    * @author wangheng
    * @param @param dto
    * @param @return
    * @return List<ReleaseInfoVo>
    * @throws  
    */
    public List<ReleaseInfoVo> selectByCondition(ReleaseInfoDto dto);

    /**  
    * @Description: 条件查询记录总数
    * @author wangheng
    * @param @param dto
    * @param @return
    * @return int
    * @throws  
    */
    public long countByCondition(ReleaseInfoDto dto);

    /**  
    * @Description: 条件分页查询Page 对象
    * @author wangheng
    * @param  dto
    * @param haveCount 是否有 总数
    * @param @return
    * @return PageList<ReleaseInfoVo>
    * @throws  
    */
    public PageList<ReleaseInfoVo> pageByCondition(ReleaseInfoDto dto, boolean haveCount);

    /**  
    * @Description: 唯一ID 选择性更新
    * @author wangheng
    * @param @param record
    * @param @return
    * @return int
    * @throws  
    */
    public int updateByUkSelective(ReleaseInfo record);

    /**  
    * @Description: 提交批量更新
    * @author wangheng
    * @param @param record
    * @param @param dto
    * @param @return
    * @return int
    * @throws  
    */
    public int updateByCondition(ReleaseInfo record, ReleaseInfoDto dto);

    /**  
    * @Description: 发布内容 校验
    * @author wangheng
    * @param @param record
    * @param @param cfgVo
    * @param @return
    * @return boolean
    * @throws  
    */
    public boolean releaseInfoCheck(ReleaseInfo record, ReleaseConfigVo cfgVo);
    
    /**  
    * @Description: 资源属性 置为：空
    * @author wangheng
    * @param @param record
    * @return void
    * @throws  
    */
    public void resourcePropertiesEmpty(ReleaseInfo record);
}
