package com.yryz.quanhu.user.provider;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.user.contants.Constants.ImgAuditStatus;
import com.yryz.quanhu.user.dto.UserImgAuditDTO;
import com.yryz.quanhu.user.entity.UserImgAudit;
import com.yryz.quanhu.user.service.UserImgAuditApi;
import com.yryz.quanhu.user.service.UserImgAuditService;
import com.yryz.quanhu.user.vo.UserImgAuditVO;

@Service(interfaceClass=UserImgAuditApi.class)
public class UserImgAuditProvider implements UserImgAuditApi {
	private static final Logger logger = LoggerFactory.getLogger(UserImgAuditProvider.class);
	
	@Autowired
	private UserImgAuditService imgService;
	
	@Override
	public Response<Boolean> auditImg(UserImgAuditDTO record, Integer auditActionStatus) {
		try {
			checkDTO(record,auditActionStatus);
			UserImgAudit audit = GsonUtils.parseObj(record, UserImgAudit.class);
			imgService.auditImg(audit, auditActionStatus);
			return ResponseUtils.returnObjectSuccess(true);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("头像审核未知异常", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<Boolean> batchAuditImg(List<UserImgAuditDTO> records, Integer auditActionStatus) {
		try {
			if(CollectionUtils.isEmpty(records)){
				throw QuanhuException.busiError("传参不合法");
			}
			if(auditActionStatus == null || auditActionStatus < ImgAuditStatus.NO_AUDIT.getStatus() || auditActionStatus > ImgAuditStatus.FAIL.getStatus()){
				throw QuanhuException.busiError("操作类型不合法为空");
			}
			for(int i = 0 ; i < records.size();i++){
				checkDTO(records.get(i),auditActionStatus);
			}
			
			List<UserImgAudit> audit = GsonUtils.parseList(records, UserImgAudit.class);
			imgService.batchAuditImg(audit, auditActionStatus);
			return ResponseUtils.returnObjectSuccess(true);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("头像审核未知异常", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<PageList<UserImgAuditVO>> listByCustId(Integer pageNo, Integer pageSize, Long userId, Integer auditStatus) {
		if(pageNo == null || pageNo < 0){
			pageNo = 1;
		}
		if(pageSize == null || pageSize < 0 || pageSize > 100){
			pageSize = 10;
		}
		try {
			PageList<UserImgAuditVO> pageList = new PageList<>(pageNo, pageSize, null);
			Page<UserImgAudit> page = imgService.listByUserId(pageNo, pageSize, userId, auditStatus);
			List<UserImgAuditVO> auditVOs = GsonUtils.parseList(page.getResult(), UserImgAuditVO.class);
			pageList.setCount(page.getTotal());
			pageList.setEntities(auditVOs);
			return ResponseUtils.returnObjectSuccess(pageList);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("头像审核查询未知异常", e);
			return ResponseUtils.returnException(e);
		}
	}
	private void checkDTO(UserImgAuditDTO auditDTO,Integer auditActionStatus){
		if (auditDTO == null){
			throw QuanhuException.busiError("传参不合法");
		}
		if(StringUtils.isBlank(auditDTO.getUserId())){
			throw QuanhuException.busiError("用户id为空");
		}
		if(StringUtils.isBlank(auditDTO.getUserImg())){
			throw QuanhuException.busiError("用户头像为空");
		}
		if(auditActionStatus == null || auditActionStatus < ImgAuditStatus.NO_AUDIT.getStatus() || auditActionStatus > ImgAuditStatus.FAIL.getStatus()){
			throw QuanhuException.busiError("操作类型不合法为空");
		}
	}
	
}
