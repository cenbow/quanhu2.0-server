package com.yryz.quanhu.resource.release.config.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.resource.release.config.api.ReleaseConfigApi;
import com.yryz.quanhu.resource.release.config.service.ReleaseConfigService;
import com.yryz.quanhu.resource.release.config.vo.ReleaseConfigVo;

/**
* @author wangheng
* @date 2018年1月22日 下午3:06:46
*/
@Service(interfaceClass = ReleaseConfigApi.class)
public class ReleaseConfigProvider implements ReleaseConfigApi {

    private Logger logger = LoggerFactory.getLogger(ReleaseConfigProvider.class);

    @Autowired
    ReleaseConfigService releaseConfigService;

    private ReleaseConfigService getService() {
        return releaseConfigService;
    }

    @Override
    public Response<ReleaseConfigVo> getTemplate(Long classifyId) {
        try {
            return ResponseUtils.returnObjectSuccess(this.getService().getTemplate(classifyId));
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("获取分类文章发布模板异常！", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Integer> updateTemplate(ReleaseConfigVo releaseConfigVo) {
        try {
            return ResponseUtils.returnObjectSuccess(this.getService().updateTemplate(releaseConfigVo));
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("更新分类文章发布模板异常！", e);
            return ResponseUtils.returnException(e);
        }
    }

}
